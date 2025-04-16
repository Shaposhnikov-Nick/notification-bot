package ru.notifiction.notificationbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotificationBotApplication

fun main(args: Array<String>) {
	runApplication<NotificationBotApplication>(*args)
}
