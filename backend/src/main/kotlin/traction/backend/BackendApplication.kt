package traction.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class BackendApplication {
}

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
