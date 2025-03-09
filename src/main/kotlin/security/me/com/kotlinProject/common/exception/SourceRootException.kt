package security.me.com.kotlinProject.common.exception

import security.me.com.kotlinProject.common.code.ServerCode

abstract class SourceRootException(
    open val code: ServerCode,
    override val message: String?,
    override val cause: Throwable?
): RuntimeException(message, cause) {

    constructor(code: ServerCode): this(code, code.messageCode, null)
}