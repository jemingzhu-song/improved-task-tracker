package traction.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import traction.backend.model.Role
import traction.backend.model.dto.AddRoleUserDTO
import traction.backend.service.RoleService

@RestController("role")
class RoleController(
    private val roleService: RoleService
) {
    @PostMapping("/create")
    fun saveRole(@RequestBody role: Role): ResponseEntity<Any> {
        return ResponseEntity.ok().body(roleService.createRole(role))
    }

    @PostMapping("/add/user")
    fun addRoleToUser(@RequestBody addRoleUserDTO: AddRoleUserDTO): ResponseEntity<Any> {
        return ResponseEntity.ok().body(roleService.addRoleToUserAccount(addRoleUserDTO))
    }
}