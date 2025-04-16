package ru.notifiction.notificationbot.handler

import ru.notifiction.notificationbot.model.GitlabEvent

interface GitlabEventHandler<in T : GitlabEventNotification> {
    fun handle(body: T): String
    fun getSupportedGitlabEvent(): GitlabEvent
    fun getSupportedEventType(): Class<out GitlabEventNotification>
}

interface GitlabEventNotification