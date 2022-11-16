package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

class AddRoleUserDTO(
    @JsonProperty("roleName")
    var roleName: String,

    @JsonProperty("userId")
    var userId: Long,

) {
}