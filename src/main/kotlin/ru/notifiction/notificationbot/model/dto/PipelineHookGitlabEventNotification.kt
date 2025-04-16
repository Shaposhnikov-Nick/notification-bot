package ru.notifiction.notificationbot.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.notifiction.notificationbot.handler.GitlabEventNotification

data class PipelineHookGitlabEventNotification(
    val builds: List<Build>,
    @JsonProperty("object_attributes")
    val objectAttributes: ObjectAttributes,
    val project: Project,
    val user: GitlabUser
) : GitlabEventNotification