package security.me.com.kotlinProject.common.jwt

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import security.me.com.kotlinProject.security.service.TokenValidationService

@Service
class JwtTokenValidationService(
    @Qualifier("jwtProvider") private val jwtService: JwtService
): TokenValidationService {

    override fun validation(token: String): Boolean {
        return jwtService.validateToken(token)
    }

    override fun getId(token: String): Long {
        val userId = jwtService.getAccount(token)
        return userId.toLong()
    }
}