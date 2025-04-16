package ru.notifiction.notificationbot.handler

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.stereotype.Service
import ru.notifiction.notificationbot.exception.GitlabEventException
import ru.notifiction.notificationbot.exception.JsonParseException
import ru.notifiction.notificationbot.model.GitlabEvent
import ru.notifiction.notificationbot.service.EventNotificationSender


interface GitlabWebhookHandler {
    fun sendEventNotification(body: String, event: String)
}


@Service
class GitlabWebhookHandlerImpl(
    val gitlabEventHandlers: Map<String, GitlabEventHandler<*>>,
    val sender: EventNotificationSender,
    val mapper: ObjectMapper
) : GitlabWebhookHandler {
    override fun sendEventNotification(body: String, event: String) {
        val gitlabEvent = GitlabEvent.from(event)
            ?: throw GitlabEventException("Unknown Gitlab event: $event !")

        val eventHandler = gitlabEventHandlers.values.firstOrNull { it.getSupportedGitlabEvent() == gitlabEvent }
            ?: throw GitlabEventException("Unsupported Gitlab event: $event !")

        runCatching {
            mapper.readValue(body, eventHandler.getSupportedEventType())
        }.mapCatching {
            @Suppress("UNCHECKED_CAST")
            (eventHandler as GitlabEventHandler<GitlabEventNotification>).handle(it)
        }.onSuccess {
            sender.send(it)
        }.onFailure { throw JsonParseException("Error when parsing event: $event, JSON: $body", it) }
    }

}