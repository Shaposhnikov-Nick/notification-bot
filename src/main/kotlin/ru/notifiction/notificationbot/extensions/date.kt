package ru.notifiction.notificationbot.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val targetFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun LocalDateTime.readableFormat(): String = this.format(targetFormatter)