package traction.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity(name = "Role")
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", updatable = false)
    @JsonProperty("roleId")
    var roleId: Long?,

    @Column(name = "role_name", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("roleName")
    var roleName: String,
) {

}