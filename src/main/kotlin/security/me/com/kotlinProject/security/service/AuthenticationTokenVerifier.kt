package security.me.com.kotlinProject.security.service

import security.me.com.kotlinProject.security.model.VerifyResult

interface AuthenticationTokenVerifier {

    fun verify(token: String): VerifyResult?
}