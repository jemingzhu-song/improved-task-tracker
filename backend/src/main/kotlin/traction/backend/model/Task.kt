package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import traction.backend.model.enums.StatusCode
import java.util.Date
import javax.persistence.*

@Entity(name = "Task")
@Table(name = "task")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", updatable = false)
    @JsonProperty("taskId")
    var taskId: Long,

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("status")
    var status: StatusCode,

    @Column(name = "task_description", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("taskDescription")
    var taskDescription: String,

    @Column(name = "date_created", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("dateCreated")
    var dateCreated: Date = Date(System.currentTimeMillis()),
) {
}