package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

class LoginDTO(
    @JsonProperty("email")
    var email: String,

    @JsonProperty("password")
    var password: String
) {
}