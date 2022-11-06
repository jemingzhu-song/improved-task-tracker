package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import traction.backend.model.enums.StatusCode

class TaskEditDTO(
    @JsonProperty("taskId")
    var taskId: Long,

    @JsonProperty("status")
    var status: StatusCode?,

    @JsonProperty("taskDescription")
    var taskDescription: String?
) {
}