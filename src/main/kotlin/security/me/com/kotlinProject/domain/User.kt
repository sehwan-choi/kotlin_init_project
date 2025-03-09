package security.me.com.kotlinProject.domain

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    var email: String,

    var password: String,

    @Enumerated(EnumType.STRING)
    var status: UserStatus? = UserStatus.ACTIVE,

    @ElementCollection
    @CollectionTable(name = "user_authority", joinColumns = [JoinColumn(name = "user_id")])
    var roles: MutableSet<UserAuthority> = mutableSetOf(),

    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,

    var blocked: Boolean? = true,
)