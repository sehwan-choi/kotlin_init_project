package security.me.com.kotlinProject.member.repository

import security.me.com.kotlinProject.domain.User

interface UserCustomRepository {

    fun findUserById(id: Long): User?
}