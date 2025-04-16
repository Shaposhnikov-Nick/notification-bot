package ru.notifiction.notificationbot.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.notifiction.notificationbot.config.BotProperty
import ru.notifiction.notificationbot.model.MainCommand
import ru.notifiction.notificationbot.model.StatusMessage

interface EventNotificationSender {
    fun send(text: String)
}

@Component
class TelegramBot(
    private val botProperty: BotProperty
) : TelegramLongPollingBot(botProperty.token), EventNotificationSender {

    private val log by lazy { LoggerFactory.getLogger(javaClass) }

    private var botStarted = false

    override fun getBotUsername(): String = botProperty.name

    override fun onUpdateReceived(update: Update) {
        handleUpdate(update)
    }

    override fun send(text: String) {
        if (!botStarted) return

        botProperty.recipients.forEach {
            sendMessage(it, text)
        }
    }

    private fun sendMessage(chatId: String, text: String) {
        val sm = SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .disableWebPagePreview(false)
            .parseMode("html")
            .build()
        execute(sm)
    }

    private fun handleUpdate(update: Update) {
        update.message?.run {
            val chatId = chatId.toString()
            if (isOwner(chatId) && isCommand) {
                text.handleCommand(chatId)
            } else {
                sendMessage(chatId, StatusMessage.NO_ACCESS.text)
            }
        }
    }

    private fun isOwner(chatId: String) = chatId == botProperty.owner

    private fun String.handleCommand(chatId: String) {
        when (this) {
            MainCommand.START.value -> {
                botStarted = true
                sendMessage(chatId, StatusMessage.BOT_STARTED.text)
                log.info(StatusMessage.BOT_STARTED.text)
            }

            MainCommand.STOP.value -> {
                botStarted = false
                sendMessage(chatId, StatusMessage.BOT_STOPPED.text)
                log.info(StatusMessage.BOT_STOPPED.text)
            }

            MainCommand.STATUS.value -> {
                val status = if (botStarted) StatusMessage.BOT_STARTED.text else StatusMessage.BOT_STOPPED.text
                sendMessage(chatId, status)
            }
        }
    }

}