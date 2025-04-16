package ru.notifiction.notificationbot.model.dto

data class Runner(
    val active: Boolean,
    val description: String,
    val id: Int,
    val is_shared: Boolean,
    val runner_type: String,
    val tags: List<Any>
)