package security.me.com.kotlinProject.member.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import security.me.com.kotlinProject.common.jwt.JwtProvider
import security.me.com.kotlinProject.domain.Authority
import security.me.com.kotlinProject.domain.User
import security.me.com.kotlinProject.domain.UserAuthority
import security.me.com.kotlinProject.member.dto.LoginRequest
import security.me.com.kotlinProject.member.dto.LoginResult
import security.me.com.kotlinProject.member.dto.UserCreateRequest
import security.me.com.kotlinProject.member.exception.LoginException
import security.me.com.kotlinProject.member.exception.UserEmailDuplicateException
import security.me.com.kotlinProject.member.exception.UserNotFoundException
import security.me.com.kotlinProject.member.repository.UserRepository

@Service
class UserSignService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,

) {
    fun login(request : LoginRequest): LoginResult {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw LoginException("Passwords do not match")
        }
        return LoginResult(jwtProvider.createToken(user.id.toString(), user.roles.map { it.authority }))
    }

    fun create(userCreateRequest: UserCreateRequest): User {
        checkEmail(userCreateRequest.email)
        return userRepository.save(User(
            name = userCreateRequest.username,
            email = userCreateRequest.email,
            password = passwordEncoder.encode(userCreateRequest.password),
            roles = mutableSetOf(UserAuthority(Authority.USER))
        ))
    }

    private fun checkEmail(email: String) {
        userRepository.findByEmail(email)?.let { throw UserEmailDuplicateException("Email duplicate => [$email]") }
    }

    fun findUserId(id: Long): User? {
        return userRepository.findById(id).get()
    }
}