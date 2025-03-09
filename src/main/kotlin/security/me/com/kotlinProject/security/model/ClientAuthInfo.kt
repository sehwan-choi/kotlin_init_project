package security.me.com.kotlinProject.security.model

import security.me.com.kotlinProject.domain.Authority

interface ClientAuthInfo {
    val id: Long
    val name: String
    val roles: List<Authority>
}