package security.me.com.kotlinProject.member.dto

data class UserCreateRequest(
    val username: String,
    val password: String,
    val email: String,
)
