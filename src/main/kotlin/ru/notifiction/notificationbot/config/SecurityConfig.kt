package ru.notifiction.notificationbot.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class SecurityConfig(
    val property: GitlabProperty
) : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        if (req.getHeader("X-Gitlab-Token") == property.token) {
            chain.doFilter(request, response)
        } else {
            val res = response as HttpServletResponse
            res.status = HttpServletResponse.SC_UNAUTHORIZED
            res.writer.write("Unauthorized")
        }
    }
}
