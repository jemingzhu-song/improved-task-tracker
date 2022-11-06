package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
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

    @JsonProperty("tasks")
    var tasks: Collection<Task>?
) {
}