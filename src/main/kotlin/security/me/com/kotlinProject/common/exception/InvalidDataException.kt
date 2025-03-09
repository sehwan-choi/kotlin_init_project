package security.me.com.kotlinProject.common.exception

import security.me.com.kotlinProject.common.code.ServerCode

open class InvalidDataException(
    override val code: ServerCode,
    override val message: String?,
    override val cause: Throwable?,
): SourceRootException(code, message, cause) {

    constructor(code: ServerCode): this(code, code.messageCode, null)

    constructor(code: ServerCode, message: String): this(code, message, null)

}