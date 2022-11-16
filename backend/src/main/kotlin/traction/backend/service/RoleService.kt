package traction.backend.service

import org.springframework.stereotype.Service
import traction.backend.datasource.RoleRepository
import traction.backend.datasource.UserAccountRepository
import traction.backend.model.Role
import traction.backend.model.UserAccount
import traction.backend.model.dto.AddRoleUserDTO

@Service
class RoleService(
    private val userAccountRepository: UserAccountRepository,
    private val roleRepository: RoleRepository
) {
    fun getAllRoles(): MutableList<Role> {
        return roleRepository.findAll()
    }

    fun createRole(role: Role): Role {
        return roleRepository.save(role)
    }

    fun addRoleToUserAccount(addRoleUserDTO: AddRoleUserDTO) {
        var userAccount: UserAccount = userAccountRepository.findById(addRoleUserDTO.userId).orElseThrow {
            IllegalStateException("User with id: ${addRoleUserDTO.userId} does not exist")
        }

        var role: Role = roleRepository.findRoleByRoleName(addRoleUserDTO.roleName)

        userAccount.roles.add(role)
    }
}