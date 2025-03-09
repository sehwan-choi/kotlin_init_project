package security.me.com.kotlinProject.security.service

import security.me.com.kotlinProject.security.model.ClientAuthInfo

interface AccessAuthorizationService {

    fun getAccessClient(id: Long): ClientAuthInfo
}