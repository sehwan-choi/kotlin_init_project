package security.me.com.kotlinProject.member.exception

import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.exception.InvalidDataException

open class UserEmailDuplicateException(
    override val code: ServerCode,
    override val message: String?,
    override val cause: Throwable?,
): InvalidDataException(code, message, cause) {

    constructor(message: String): this(ServerCode.SIGNUP_DUPLICATE_EMAIL, message, null)
}