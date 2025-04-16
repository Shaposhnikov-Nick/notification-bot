package ru.notifiction.notificationbot.controller

import org.springframework.web.bind.annotation.*
import ru.notifiction.notificationbot.handler.GitlabWebhookHandler


@RestController
@RequestMapping("/webhook")
class GitlabWebhooksController(
    val gitlabWebhookHandler: GitlabWebhookHandler
) {

    @PostMapping
    fun handleWebhook(@RequestBody body: String, @RequestHeader("X-Gitlab-Event") event: String) {
        gitlabWebhookHandler.sendEventNotification(body, event)
    }

}