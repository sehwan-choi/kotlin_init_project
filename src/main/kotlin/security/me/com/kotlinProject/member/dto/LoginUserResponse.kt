package security.me.com.kotlinProject.member.dto

import security.me.com.kotlinProject.domain.UserAuthority
import security.me.com.kotlinProject.domain.UserStatus
import java.time.LocalDateTime

data class LoginUserResponse(
    val userId: Long,
    val userName: String,
    val email: String,
    val roles: Set<UserAuthority>,
    val status: UserStatus,
    val createdAt: LocalDateTime,
    val blocked: Boolean,
)
