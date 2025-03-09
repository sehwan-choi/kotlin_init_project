package security.me.com.kotlinProject.security.service

interface TokenValidationService {

    fun validation(token: String): Boolean

    fun getId(token: String): Long
}