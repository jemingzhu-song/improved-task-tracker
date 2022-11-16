package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import traction.backend.model.Task

class UserAccountEditDTO(
    @JsonProperty("userId")
    val userId: Long,

    @JsonProperty("firstName")
    var firstName: String?,

    @JsonProperty("lastName")
    var lastName: String?,

    @JsonProperty("email")
    var email: String?,

    @JsonProperty("password")
    var password: String?,
) {
    init {
        if (password != null) {
            val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
            this.password = passwordEncoder.encode(password)
        }
    }
}