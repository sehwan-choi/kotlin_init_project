package security.me.com.kotlinProject.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import security.me.com.kotlinProject.common.jwt.JwtProvider
import security.me.com.kotlinProject.security.exception.BadTokenAuthorizationException
import security.me.com.kotlinProject.security.model.ClientAuthenticationToken
import security.me.com.kotlinProject.security.service.AccessAuthorizationService
import security.me.com.kotlinProject.security.service.AuthenticationTokenVerifier

private val log = KotlinLogging.logger {}

class TokenAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val verifier: AuthenticationTokenVerifier,
    private val authorizationService: AccessAuthorizationService,
): OncePerRequestFilter() {
    companion object {
        const val tokenAuthorizationHeaderName = "Authorization"
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtToken = obtainToken(request)
        jwtToken?.let { attemptAuthentication(jwtToken) }

        filterChain.doFilter(request, response)

    }

    private fun attemptAuthentication(jwtToken: String) {
        try {
            val result = verifier.verify(jwtToken) ?: throw BadTokenAuthorizationException("The token is invalid. token : \"${jwtToken}\"")
            val accessClient = authorizationService.getAccessClient(result.id)

            SecurityContextHolder.getContext().authentication = ClientAuthenticationToken(accessClient, jwtToken, true)
        } catch (e: Exception) {
            log.error("TokenAuthenticationFilter Exception::", e)
            SecurityContextHolder.clearContext()
        }
    }

    private fun obtainToken(request: HttpServletRequest): String? {
        return request.getHeader(tokenAuthorizationHeaderName)
    }
}