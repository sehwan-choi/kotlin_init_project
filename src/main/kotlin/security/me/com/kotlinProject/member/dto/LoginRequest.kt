package security.me.com.kotlinProject.member.dto

import org.jetbrains.annotations.NotNull

data class LoginRequest(
    @field:NotNull val email: String,
    @field:NotNull val password: String
)
