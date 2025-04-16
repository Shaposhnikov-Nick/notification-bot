package ru.notifiction.notificationbot.model

enum class MainCommand(val value: String) {
    START("/start"),
    STOP("/stop"),
    STATUS("/status")
}

enum class StatusMessage(val text: String) {
    BOT_STARTED("Bot started!"),
    BOT_STOPPED("Bot stopped!"),
    NO_ACCESS("You do not have permission to access the bot!")
}
