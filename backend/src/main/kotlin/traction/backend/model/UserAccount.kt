package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.CascadeType
import javax.persistence.OneToMany

class UserAccount(
    @JsonProperty("userId")
    var userId: Long,
    @JsonProperty("firstName")
    var firstName: String,
    @JsonProperty("lastName")
    var lastName: String,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("password")
    var password: String,
    @JsonProperty("tasks")
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var tasks: Collection<Task>
) {

}