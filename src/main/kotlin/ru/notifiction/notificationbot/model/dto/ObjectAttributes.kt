package ru.notifiction.notificationbot.model.dto

data class ObjectAttributes(
    val iid: Long = 0L,
    val url: String,
    val title: String = "",
    val sourceBranch: String = "",
    val targetBranch: String = "",
    val action: GitlabMergeRequestAction = GitlabMergeRequestAction.UNKNOWN
)