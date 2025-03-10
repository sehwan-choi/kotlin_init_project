package security.me.com.kotlinProject.mvc.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import security.me.com.kotlinProject.common.generator.KeyGenerator
import security.me.com.kotlinProject.mvc.domain.MDCKey
import java.io.ByteArrayInputStream

private val log = KotlinLogging.logger {}

class LoggingFilter(
    private val keyGenerator: KeyGenerator,
    private val excludePath: MutableList<AntPathRequestMatcher> = mutableListOf(),
): OncePerRequestFilter() {

    init {
        excludePath.add(AntPathRequestMatcher("/actuator*"))
        excludePath.add(AntPathRequestMatcher("/actuator/*"))
        excludePath.add(AntPathRequestMatcher("/h2-console/*"))
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return excludePath.any { it.matches(request) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)

        try {
            MDC.put(MDCKey.TRX_ID.key, keyGenerator.generate())
            val requestBody = requestWrapper.contentAsByteArray
            log.debug("URI : ${requestWrapper.requestURI}")
            log.debug("Method : ${requestWrapper.method}")
            printHeaders(requestWrapper)
            printQueryParameters(requestWrapper)
            printContentBody(requestBody, "Request Body")

            filterChain.doFilter(requestWrapper, responseWrapper)

            val responseBody = responseWrapper.contentAsByteArray
            printContentBody(responseBody, "Response Body")

            responseWrapper.copyBodyToResponse()
        } finally {
            MDC.clear()
        }
    }

    private fun printHeaders(request: HttpServletRequest) {
        request.headerNames.asSequence().forEach { headerName ->
            log.debug("[Header] $headerName : ${request.getHeader(headerName)}")
        }
    }

    private fun printQueryParameters(request: HttpServletRequest) {
        request.parameterMap.forEach { (key, value) ->
            log.debug("[QueryParam] key : ${key} : ${value.joinToString(", ")}")
        }
    }

    private fun printContentBody(contents: ByteArray, prefix: String) {
        ByteArrayInputStream(contents).bufferedReader().useLines { lines ->
            lines.forEach { log.debug("[${prefix}] ${it}") }
        }
    }
}