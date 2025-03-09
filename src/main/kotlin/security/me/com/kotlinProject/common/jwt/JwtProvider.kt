package security.me.com.kotlinProject.common.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import security.me.com.kotlinProject.domain.Authority
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

private val log = KotlinLogging.logger {}

@Component
class JwtProvider(
    @Value("\${jwt.secret.key}") val salt: String,
    @Value("\${jwt.secret.expired}") val exp: Long,
): JwtService {

    companion object {
        private const val authorizationPrefixName = "Bearer "
    }

    val secretKey: Key by lazy {
        Keys.hmacShaKeyFor(salt.toByteArray(StandardCharsets.UTF_8))
    }

    override fun createToken(account: String, roles: Collection<Authority>): String {
        val claims = Jwts.claims().apply {
            subject = account
            this["roles"] = roles
        }

        val date = Date()
        val expiredDate = Date(date.time + exp)

        return authorizationPrefixName + Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setExpiration(expiredDate)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun getAccount(token: String): String {
        val jwtToken = getJwt(token)
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(jwtToken)
            .body
            .subject
    }

    override fun validateToken(token: String): Boolean {
        return try {
            val jwt = getJwt(token)
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
            true
        } catch (e: Exception) {
            when (e) {
                is SecurityException, is MalformedJwtException -> {
                    log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e)
                }
                is ExpiredJwtException -> {
                    log.error("Expired JWT token, 만료된 JWT token 입니다.", e)
                }
                is UnsupportedJwtException -> {
                    log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e)
                }
                is IllegalArgumentException -> {
                    log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", e)
                }
                else -> {
                    log.error("Jwt 파싱 실패", e)
                }
            }
            false
        }
    }

    private fun getJwt(token: String): String {
        if (!checkJwtPrefix(token)) {
            throw MalformedJwtException("유효하지 않은 JWT Prefix 입니다.")
        }
        return token.substring(7)
    }

    private fun checkJwtPrefix(token: String): Boolean {
        return token.startsWith(authorizationPrefixName)
    }
}