package security.me.com.kotlinProject.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.model.ApiResultResponse

class ResourceAccessDeniedHandler(
    private val messageSource: MessageSource
): AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val message = messageSource.getMessage(ServerCode.FORBIDDEN.messageCode, null, request.locale)

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.status = HttpStatus.FORBIDDEN.value()

        response.outputStream.use {
            val jsonString = Json.encodeToString(ApiResultResponse.ofResponse(ServerCode.FORBIDDEN.code, message, null))
            it.write(jsonString.toByteArray())
        }
    }
}