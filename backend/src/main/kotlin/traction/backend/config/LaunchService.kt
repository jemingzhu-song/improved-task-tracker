package traction.backend.config

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import traction.backend.model.Role
import traction.backend.service.RoleService

@SpringBootApplication
class LaunchService {
    @Bean
    fun run(roleService: RoleService): CommandLineRunner {
        return CommandLineRunner {
            roleService.createRole(Role(null, "ROLE_USER"))
            roleService.createRole(Role(null, "ROLE_ADMIN"))
        }
    }

}