package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity(name = "UserAccount")
@Table(name = "user_account")
class UserAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false) // @Column for mapping this Property Name with Database Table Column Name
    @JsonProperty("userId")
    var userId: Long?,

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("firstName")
    var firstName: String,

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("lastName")
    var lastName: String,

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    @JsonProperty("email")
    var email: String,

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("password")
    var password: String,

    @JsonProperty("tasks")
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "user_id")
    var tasks: MutableList<Task>,

    @JsonProperty("roles")
    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableList<Role>

) {
    init {
        val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
        this.password = passwordEncoder.encode(password)
    }

    fun comparePasswords(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}