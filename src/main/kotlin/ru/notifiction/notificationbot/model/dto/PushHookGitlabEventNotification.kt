package ru.notifiction.notificationbot.model.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ru.notifiction.notificationbot.handler.GitlabEventNotification

@JsonIgnoreProperties(ignoreUnknown = true)
data class PushHookGitlabEventNotification(
    val commits: List<Commit>,
    val project: Project,
    @JsonProperty("user_name")
    val userName: String,
    @JsonProperty("total_commits_count")
    val totalCommitsCount: String
) : GitlabEventNotification

@JsonIgnoreProperties(ignoreUnknown = true)
data class Commit(
    val author: Author,
    val title: String,
    val url: String
)

class PushOptions

@JsonIgnoreProperties(ignoreUnknown = true)
data class Repository(
    val description: Any,
    val git_http_url: String,
    val git_ssh_url: String,
    val homepage: String,
    val name: String,
    val url: String,
    val visibility_level: Int
)