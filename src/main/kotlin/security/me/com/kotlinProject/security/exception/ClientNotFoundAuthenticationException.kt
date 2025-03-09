package security.me.com.kotlinProject.security.exception

import org.springframework.security.core.AuthenticationException


class ClientNotFoundAuthenticationException(
    override val message: String,
    override val cause: Throwable? = null
): AuthenticationException(message, cause) {
    constructor(message: String): this(message = message, cause = null)
}