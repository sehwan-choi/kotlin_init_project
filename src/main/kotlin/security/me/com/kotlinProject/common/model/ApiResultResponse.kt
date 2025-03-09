package security.me.com.kotlinProject.common.model

import kotlinx.serialization.Serializable
import security.me.com.kotlinProject.common.code.ServerCode

@Serializable
data class ApiResultResponse<T>(
    val code: String,
    val message: String,
    val data: T?
) {
    companion object {
        fun <T> success(): ApiResultResponse<T> {
            return ApiResultResponse(
                code = ServerCode.SUCCESS.code,
                message = "success",
                null
            )
        }

        fun <T> success(data: T): ApiResultResponse<T> {
            return ApiResultResponse(
                code = ServerCode.SUCCESS.code,
                message = "success",
                data
            )
        }

        fun <T> ofResponse(code: String, message: String, data: T?): ApiResultResponse<T> {
            return ApiResultResponse(
                code = code,
                message = message,
                data = data
            )
        }
    }
}
