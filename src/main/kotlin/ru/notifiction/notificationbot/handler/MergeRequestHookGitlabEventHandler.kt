package ru.notifiction.notificationbot.handler

import org.springframework.stereotype.Service
import ru.notifiction.notificationbot.extensions.bold
import ru.notifiction.notificationbot.extensions.underline
import ru.notifiction.notificationbot.extensions.url
import ru.notifiction.notificationbot.model.GitlabEvent
import ru.notifiction.notificationbot.model.dto.GitlabMergeRequestAction
import ru.notifiction.notificationbot.model.dto.MergeRequestHookGitlabEventNotification


@Service
class MergeRequestHookGitlabEventHandler : GitlabEventHandler<MergeRequestHookGitlabEventNotification> {
    override fun handle(body: MergeRequestHookGitlabEventNotification): String {
        val mrActionText = when (body.objectAttributes.action) {
            GitlabMergeRequestAction.OPEN -> "${body.user.name.bold()} created a merge request"
            GitlabMergeRequestAction.CLOSE -> "${body.user.name.bold()} closed a merge request"
            GitlabMergeRequestAction.REOPEN -> "${body.user.name.bold()} reopen a merge request"
            GitlabMergeRequestAction.UPDATE -> "${body.user.name.bold()} updated a merge request"
            GitlabMergeRequestAction.APPROVED -> "Merge request is approved"
            GitlabMergeRequestAction.UNAPPROVED -> "Merge request is unapproved"
            GitlabMergeRequestAction.APPROVAL -> "${body.user.name.bold()} approval a merge request"
            GitlabMergeRequestAction.UNAPPROVAL -> "${body.user.name.bold()} unapproval a merge request"
            GitlabMergeRequestAction.MERGE -> "All threads are resolved on the merge request"
            GitlabMergeRequestAction.UNKNOWN -> "Unknown".underline() + " action with merge request"
        }

        val htmlText =
            """Project: ${body.project.name.bold()} 
            |${mrActionText}: ${body.objectAttributes.url.url("!${body.objectAttributes.iid}")}
            |${"-".repeat(30)}
            |Title: ${body.objectAttributes.title.bold()}
            |Branches: ${body.objectAttributes.sourceBranch} to ${body.objectAttributes.targetBranch}         
            |Assignees: ${body.assignees.joinToString(separator = ",") { a -> a.name }}
            |Reviewers: ${body.assignees.joinToString(separator = ",") { a -> a.name }}
        """.trimMargin()

        return htmlText
    }

    override fun getSupportedGitlabEvent(): GitlabEvent = GitlabEvent.MERGE_REQUEST

    override fun getSupportedEventType(): Class<out GitlabEventNotification> {
        return MergeRequestHookGitlabEventNotification::class.java
    }
}
