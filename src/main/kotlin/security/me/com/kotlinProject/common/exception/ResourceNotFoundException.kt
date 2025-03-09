package security.me.com.kotlinProject.common.exception

import security.me.com.kotlinProject.common.code.ServerCode

open class ResourceNotFoundException(
    override val code: ServerCode,
    override val message: String
): SourceRootException(code, message, null)