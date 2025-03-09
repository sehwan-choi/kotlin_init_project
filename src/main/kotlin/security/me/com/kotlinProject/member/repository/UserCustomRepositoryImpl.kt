package security.me.com.kotlinProject.member.repository

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import security.me.com.kotlinProject.domain.QUser
import security.me.com.kotlinProject.domain.QUserAuthority
import security.me.com.kotlinProject.domain.User

class UserCustomRepositoryImpl: QuerydslRepositorySupport(User::class.java), UserCustomRepository {

    override fun findUserById(id: Long): User? {
        val user = QUser.user
        val userAuthority = QUserAuthority.userAuthority

        return from(user)
            .leftJoin(user.roles, userAuthority)
            .fetchJoin()
            .fetchOne()
    }
}