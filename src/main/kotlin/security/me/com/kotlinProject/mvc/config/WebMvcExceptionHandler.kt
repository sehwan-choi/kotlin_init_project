package security.me.com.kotlinProject.mvc.config

import mu.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.exception.InvalidDataException
import security.me.com.kotlinProject.common.exception.ResourceNotFoundException
import security.me.com.kotlinProject.common.model.ApiResultResponse

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class WebMvcExceptionHandler(
    private val messageSource: MessageSource
): ResponseEntityExceptionHandler() {

    @ExceptionHandler(
        InvalidDataException::class,
        Exception::class,
    )
    fun handleApplicationException(ex:Exception, request: WebRequest): ResponseEntity<Any> {
        log.error ("handleApplicationException::",ex)

        var code: ServerCode? = null
        var httpStatus: HttpStatus? = null

        when (ex) {
            is InvalidDataException -> {
                httpStatus = HttpStatus.BAD_REQUEST
                code = ex.code
            }
            is ResourceNotFoundException -> {
                httpStatus = HttpStatus.BAD_REQUEST
                code = ex.code
            }
            else -> {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                code = ServerCode.INTERNAL_SERVER_ERROR
            }
        }

        val message = messageSource.getMessage(code.messageCode, null, request.locale)
        return ResponseEntity(ApiResultResponse.ofResponse(code.code, message, null), httpStatus)
    }

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {

        log.error ("handleExceptionInternal::", ex)

        var code: ServerCode? = null
        var httpStatus: HttpStatus? = null

        when (statusCode) {
            HttpStatus.BAD_REQUEST -> {
                code = ServerCode.BAD_REQUEST
                httpStatus = HttpStatus.BAD_REQUEST
            }
            HttpStatus.NOT_FOUND -> {
                code = ServerCode.NOT_FOUND
                httpStatus = HttpStatus.NOT_FOUND
            }
            else -> {
                code = ServerCode.INTERNAL_SERVER_ERROR
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
            }
        }

        val message = messageSource.getMessage(code.messageCode, null, request.locale)
        return ResponseEntity(ApiResultResponse.ofResponse(code.code, message, null), httpStatus)
    }
}