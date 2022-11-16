package traction.backend.datasource

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import traction.backend.model.Role

@Repository
interface RoleRepository: JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role r WHERE r.role_name = ?1", nativeQuery = true)
    fun findRoleByRoleName(roleName: String): Role
}