package ru.notifiction.notificationbot.handler

import org.springframework.stereotype.Service
import ru.notifiction.notificationbot.extensions.bold
import ru.notifiction.notificationbot.extensions.nullable
import ru.notifiction.notificationbot.extensions.readableFormat
import ru.notifiction.notificationbot.model.GitlabEvent
import ru.notifiction.notificationbot.model.dto.PipelineHookGitlabEventNotification


@Service
class PipelineHookGitlabEventHandler : GitlabEventHandler<PipelineHookGitlabEventNotification> {
    override fun handle(body: PipelineHookGitlabEventNotification): String {
        val htmlJobs = body.builds.joinToString(separator = "\n", prefix = "\n") { build ->
            """
             |Job: ${build.name.bold()}
             |Stage: ${build.stage.bold()}
             |Status: ${build.status.bold()}
             |Started: ${build.startedAt?.readableFormat().nullable()}
             |Finished: ${build.finishedAt?.readableFormat().nullable()}
             |Duration: ${build.duration.nullable()}
             |Failure Reason: ${build.failureReason.nullable()}
             |${"-".repeat(5)}
        """.trimIndent()
        }

        val htmlText =
            """Project: ${body.project.name.bold()} 
            |${body.user.name.bold()} started pipeline
            |URL: ${body.objectAttributes.url}
            |${"-".repeat(30)}
            |Jobs: $htmlJobs
        """.trimMargin()

        return htmlText
    }

    override fun getSupportedGitlabEvent(): GitlabEvent = GitlabEvent.PIPELINE_HOOK

    override fun getSupportedEventType(): Class<out GitlabEventNotification> {
        return PipelineHookGitlabEventNotification::class.java
    }

}