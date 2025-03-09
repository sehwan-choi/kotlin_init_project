package security.me.com.kotlinProject.member.exception

import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.exception.InvalidDataException

class LoginException(
    override val message: String,
): InvalidDataException(ServerCode.LOGIN_FAIL, message) {
}