package traction.backend.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

class RecentWeekTasksDTO(
    @JsonProperty("beforeDate")
    var beforeDate: Date
) {

}