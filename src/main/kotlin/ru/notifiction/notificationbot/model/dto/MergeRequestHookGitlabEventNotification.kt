package ru.notifiction.notificationbot.model.dto

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue
import com.fasterxml.jackson.annotation.JsonProperty
import ru.notifiction.notificationbot.handler.GitlabEventNotification

class MergeRequestHookGitlabEventNotification(
    val user: GitlabUser,
    val project: Project,
    @JsonProperty("object_attributes")
    val objectAttributes: ObjectAttributes,
    val assignees: List<GitlabUser>,
    val reviewers: List<GitlabUser>
) : GitlabEventNotification

enum class GitlabMergeRequestAction {
    @JsonProperty("open")
    OPEN,

    @JsonProperty("close")
    CLOSE,

    @JsonProperty("reopen")
    REOPEN,

    @JsonProperty("update")
    UPDATE,

    @JsonProperty("approved")
    APPROVED,

    @JsonProperty("unapproved")
    UNAPPROVED,

    @JsonProperty("approval")
    APPROVAL,

    @JsonProperty("unapproval")
    UNAPPROVAL,

    @JsonProperty("merge")
    MERGE,

    @JsonEnumDefaultValue
    UNKNOWN;

//    fun from(action: String): GitlabMergeRequestAction? =
//        GitlabMergeRequestAction.entries.find { it.name.equals(action, true) }
}
