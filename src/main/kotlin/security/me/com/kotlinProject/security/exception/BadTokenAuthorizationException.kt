package security.me.com.kotlinProject.security.exception

import org.springframework.security.authentication.BadCredentialsException

class BadTokenAuthorizationException(
    msg: String?, cause: Throwable?
) : BadCredentialsException(msg, cause) {

    constructor(msg: String): this(msg, null)
}