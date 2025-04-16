package ru.notifiction.notificationbot.handler

import org.springframework.stereotype.Service
import ru.notifiction.notificationbot.extensions.bold
import ru.notifiction.notificationbot.model.GitlabEvent
import ru.notifiction.notificationbot.model.dto.PushHookGitlabEventNotification


@Service
class PushHookGitlabEventHandler : GitlabEventHandler<PushHookGitlabEventNotification> {
    override fun handle(body: PushHookGitlabEventNotification): String {
        val htmlCommits = body.commits.joinToString(separator = "\n", prefix = "\n") { commit ->
            """
             |Title: ${commit.title.bold()}
             |Author: ${commit.author.name.bold()}
             |URL: ${commit.url}
        """.trimIndent()
        }

        val htmlText =
            """Project: ${body.project.name.bold()} 
            |${body.userName.bold()} added ${body.totalCommitsCount} commits 
            |${"-".repeat(30)}
            |Commits: $htmlCommits
        """.trimMargin()

        return htmlText
    }

    override fun getSupportedGitlabEvent(): GitlabEvent = GitlabEvent.PUSH_HOOK

    override fun getSupportedEventType(): Class<out GitlabEventNotification> {
        return PushHookGitlabEventNotification::class.java
    }

}