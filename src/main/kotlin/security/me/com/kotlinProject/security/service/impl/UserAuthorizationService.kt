package security.me.com.kotlinProject.security.service.impl

import org.springframework.stereotype.Service
import security.me.com.kotlinProject.member.repository.UserRepository
import security.me.com.kotlinProject.security.exception.ClientNotFoundAuthenticationException
import security.me.com.kotlinProject.security.model.ClientAuthInfo
import security.me.com.kotlinProject.security.model.UserClientAuthInfo
import security.me.com.kotlinProject.security.service.AccessAuthorizationService

@Service
class UserAuthorizationService(
    private val userRepository: UserRepository
): AccessAuthorizationService {

    override fun getAccessClient(id: Long): ClientAuthInfo {
        val let = userRepository.findUserById(id)?.let { it ->
            UserClientAuthInfo(
                id = it.id!!,
                name = it.name,
                roles = it.roles.map { it.authority }
            )
        }

        return let ?: throw ClientNotFoundAuthenticationException("user Id \"${id}\" not found!");
    }
}