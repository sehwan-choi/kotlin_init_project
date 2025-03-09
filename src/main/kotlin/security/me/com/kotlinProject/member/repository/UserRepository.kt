package security.me.com.kotlinProject.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import security.me.com.kotlinProject.domain.User

interface UserRepository: JpaRepository<User, Long>, UserCustomRepository {

    fun findByEmail(email: String): User?
}