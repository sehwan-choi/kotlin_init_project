package security.me.com.kotlinProject.security.model

import security.me.com.kotlinProject.domain.Authority

data class UserClientAuthInfo(
    override val id: Long,
    override val name: String,
    override val roles: List<Authority>
): ClientAuthInfo {

}
