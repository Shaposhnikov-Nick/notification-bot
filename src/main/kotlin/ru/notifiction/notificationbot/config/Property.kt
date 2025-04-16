package ru.notifiction.notificationbot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "bot")
class BotProperty {
    var name: String = ""
    var token: String = ""
    var owner: String = ""
    var recipients: List<String> = listOf()
}

@Configuration
@ConfigurationProperties(prefix = "gitlab")
class GitlabProperty {
    var token: String = ""
}