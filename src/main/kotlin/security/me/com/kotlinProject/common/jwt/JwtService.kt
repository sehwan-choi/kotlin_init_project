package security.me.com.kotlinProject.common.jwt

import security.me.com.kotlinProject.domain.Authority

interface JwtService {

    fun createToken(account: String, roles:Collection<Authority>): String

    fun getAccount(token: String): String

    fun validateToken(token: String): Boolean
}