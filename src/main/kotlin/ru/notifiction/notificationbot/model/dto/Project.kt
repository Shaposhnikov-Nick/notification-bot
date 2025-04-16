package ru.notifiction.notificationbot.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Project(
    val name: String,
    @JsonProperty("web_url")
    val webUrl: String
)