package security.me.com.kotlinProject.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.model.ApiResultResponse

class TokenAuthenticationEntryPoint(
    private val messageSource: MessageSource
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val message = messageSource.getMessage(ServerCode.UNAUTHORIZED.messageCode, null, request.locale)

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.status = HttpStatus.UNAUTHORIZED.value()

        response.outputStream.use {
            val jsonString = Json.encodeToString(ApiResultResponse.ofResponse(ServerCode.UNAUTHORIZED.code, message, null))

            it.write(jsonString.toByteArray())
        }
    }


}