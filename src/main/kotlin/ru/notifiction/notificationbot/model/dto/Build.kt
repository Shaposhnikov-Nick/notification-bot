package ru.notifiction.notificationbot.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import ru.notifiction.notificationbot.domain.BuildDateDeserializer
import java.time.LocalDateTime

data class Build(
    val duration: Double?,
    @JsonProperty("failure_reason")
    @JsonDeserialize(using = BuildDateDeserializer::class)
    val failureReason: String?,
    @JsonProperty("finished_at")
    @JsonDeserialize(using = BuildDateDeserializer::class)
    val finishedAt: LocalDateTime?,
    val name: String,
    val stage: String,
    @JsonProperty("started_at")
    @JsonDeserialize(using = BuildDateDeserializer::class)
    val startedAt: LocalDateTime?,
    val status: String,
)