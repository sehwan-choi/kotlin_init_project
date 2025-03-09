package security.me.com.kotlinProject.domain

import org.springframework.security.core.GrantedAuthority

enum class Authority: GrantedAuthority {
    USER,
    ADMIN;

    override fun getAuthority(): String {
        return this.name
    }
}