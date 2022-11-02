package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "Task")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", updatable = false)
    @JsonProperty("taskId")
    var taskId: Long,

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("status")
    var status: String,

    @Column(name = "task_description", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("taskDescription")
    var taskDescription: String
) {
}