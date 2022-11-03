package traction.backend.model.helper

import com.fasterxml.jackson.annotation.JsonProperty
import traction.backend.model.Task

class UserAccountEdit(
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

    @JsonProperty("tasks")
    var tasks: Collection<Task>?
) {
}