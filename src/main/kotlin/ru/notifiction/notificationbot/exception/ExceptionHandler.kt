package ru.notifiction.notificationbot.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime


data class GitlabEventException(override val message: String) : RuntimeException(message)
data class JsonParseException(override val message: String, override val cause: Throwable?) : RuntimeException(message,cause)

data class ExceptionResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    val timestamp: LocalDateTime,
    val message: String,
    val status: String,
    val code: Int,
)

@ControllerAdvice
class GlobalExceptionHandler {

    private val log by lazy { LoggerFactory.getLogger(javaClass) }

    @ExceptionHandler
    fun handleAllException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            LocalDateTime.now(),
            e.message ?: "Unknown error",
            HttpStatus.INTERNAL_SERVER_ERROR.name,
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
        return ResponseEntity.internalServerError().body(response).also { log.error(response.toString(), e) }
    }

    @ExceptionHandler(value = [GitlabEventException::class])
    fun handleGitlabEventException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            LocalDateTime.now(),
            e.message ?: "Unknown error",
            HttpStatus.BAD_REQUEST.name,
            HttpStatus.BAD_REQUEST.value()
        )
        return ResponseEntity.badRequest().body(response).also { log.info(response.toString(), e) }
    }

}