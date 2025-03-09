package security.me.com.kotlinProject.security.service.impl

import org.springframework.stereotype.Service
import security.me.com.kotlinProject.security.model.JwtVerifyResult
import security.me.com.kotlinProject.security.model.VerifyResult
import security.me.com.kotlinProject.security.service.AuthenticationTokenVerifier
import security.me.com.kotlinProject.security.service.TokenValidationService

@Service
class JwtAuthenticationTokenVerifier(
    private val tokenValidationService: TokenValidationService
): AuthenticationTokenVerifier {

    override fun verify(token: String): VerifyResult? {
        return if (tokenValidationService.validation(token)) {
            val userId = tokenValidationService.getId(token)
            JwtVerifyResult(userId)
        } else {
            null
        }

    }

}