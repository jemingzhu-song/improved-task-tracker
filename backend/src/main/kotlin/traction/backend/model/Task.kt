package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty

class Task(
    @JsonProperty("taskId")
    var taskId: Long,
    @JsonProperty("status")
    var status: String,
    @JsonProperty("taskDescription")
    var taskDescription: String
) {
}