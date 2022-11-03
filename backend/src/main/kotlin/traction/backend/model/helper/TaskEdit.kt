package traction.backend.model.helper

import com.fasterxml.jackson.annotation.JsonProperty
import traction.backend.model.enums.StatusCode
import javax.persistence.Column

class TaskEdit(
    @JsonProperty("taskId")
    var taskId: Long,

    @JsonProperty("status")
    var status: StatusCode?,

    @JsonProperty("taskDescription")
    var taskDescription: String?
) {
}