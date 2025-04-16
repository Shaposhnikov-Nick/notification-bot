package ru.notifiction.notificationbot.model

enum class GitlabEvent(val event: String) {
    PUSH_HOOK("Push Hook"),
    TAG_PUSH_HOOK("Tag Push Hook"),
    ISSUE_HOOK("Issue Hook"),
    NOTE_HOOK("Note Hook"),
    MERGE_REQUEST("Merge Request Hook"),
    WIKI_PAGE_HOOK(" Wiki Page Hook"),
    PIPELINE_HOOK("Pipeline Hook"),
    JOB_HOOK("Job Hook"),
    DEPLOYMENT_HOOK("Deployment Hook"),
    MEMBER_HOOK("Member Hook"),
    SUBGROUP_HOOK("Subgroup Hook"),
    FEATURE_FLAG_HOOK("Feature Flag Hook"),
    RELEASE_HOOK("Release Hook");

    companion object {
        fun from(event: String) = GitlabEvent.entries.find { it.event == event }
    }

}