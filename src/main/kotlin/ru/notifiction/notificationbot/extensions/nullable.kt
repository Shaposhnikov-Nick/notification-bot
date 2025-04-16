package ru.notifiction.notificationbot.extensions

fun String?.nullable(default: String = "---"): String = this ?: default
fun Double?.nullable(default: String = "---"): String = this?.toString() ?: default
