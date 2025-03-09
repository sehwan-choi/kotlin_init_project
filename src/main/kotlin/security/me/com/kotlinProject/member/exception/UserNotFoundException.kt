package security.me.com.kotlinProject.member.exception

import security.me.com.kotlinProject.common.code.ServerCode
import security.me.com.kotlinProject.common.exception.ResourceNotFoundException

class UserNotFoundException(
    override val message: String,
): ResourceNotFoundException(ServerCode.USER_NOT_FOUND, message) {
}