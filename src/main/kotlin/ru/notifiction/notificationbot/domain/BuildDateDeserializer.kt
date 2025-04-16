package ru.notifiction.notificationbot.domain

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class BuildDateDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    companion object {
        private var formatter1: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
        private var formatter2: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        private val inputFormatters = listOf(formatter1, formatter2)
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
        inputFormatters.forEach {
            try {
                val zonedDateTime = ZonedDateTime.parse(p.text, it)
                return zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
            } catch (_: DateTimeParseException) {
            }
        }
        throw DateTimeParseException("Error when parse date: ${p.text}", p.text, 0)
    }

}