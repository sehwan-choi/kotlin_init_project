package security.me.com.kotlinProject.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

@Embeddable
class UserAuthority(
    @Enumerated(EnumType.STRING)
    var authority: Authority,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
)