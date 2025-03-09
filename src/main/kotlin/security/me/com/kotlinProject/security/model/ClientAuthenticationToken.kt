package security.me.com.kotlinProject.security.model

import org.springframework.security.authentication.AbstractAuthenticationToken

class ClientAuthenticationToken(
    val authInfo: ClientAuthInfo,
    val accessToken: String,
    authentication: Boolean = false,
): AbstractAuthenticationToken(authInfo.roles) {

    init {
        isAuthenticated = authentication
    }

    override fun getCredentials(): Any {
        return accessToken
    }

    override fun getPrincipal(): Any {
        return authInfo
    }
}