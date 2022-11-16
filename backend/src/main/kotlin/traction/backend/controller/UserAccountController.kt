package traction.backend.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import traction.backend.model.UserAccount
import traction.backend.model.dto.LoginDTO
import traction.backend.model.dto.UserAccountEditDTO
import traction.backend.service.AuthenticationService
import traction.backend.service.UserAccountService
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.*
import javax.security.auth.login.FailedLoginException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("user/account")
class UserAccountController(
    private val userAccountService: UserAccountService,
    private val authenticationService: AuthenticationService
) {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(FailedLoginException::class)
    fun handleFailedLoginException(e: FailedLoginException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/logout")
    fun logoutUserAccount(@RequestHeader(value = "token") token: String): ResponseEntity<Any> {
        return authenticationService.logoutUserAccount(token)
    }

    @GetMapping("/token/refresh")
    fun refreshUserAccountToken(request: HttpServletRequest, response: HttpServletResponse): Unit {
        return authenticationService.refreshUserAccountToken(request, response)
    }
    @GetMapping("/get/account/id/{userId}")
    fun getUserAccountById(@PathVariable userId: Long): UserAccount {
        return userAccountService.getUserAccountById(userId)
    }

    @GetMapping("/get/account/email/{email}")
    fun getUserAccountByEmail(@PathVariable email: String): UserAccount? {
        var userAccount: UserAccount? = userAccountService.getUserAccountByEmail(email)
        return userAccount
    }

    @GetMapping("/get/id/email/{email}")
    fun getUserIdByEmail(@PathVariable email: String): Long? {
        var userId: Long? = userAccountService.getUserIdByEmail(email)
        return userId
    }

    @GetMapping("/get/account/all")
    fun getAllUserAccounts(): Collection<UserAccount> {
        return userAccountService.getAllUserAccounts()
    }

    @PostMapping("/register")
    fun createUserAccount(@RequestBody userAccount: UserAccount): UserAccount {
        return userAccountService.createUserAccount(userAccount)
    }

    @PutMapping("/edit")
    fun editUserAccount(@RequestBody userAccountEditDTO: UserAccountEditDTO): Unit {
        return userAccountService.editUserAccount(userAccountEditDTO)
    }

    @DeleteMapping("/delete/account/{email}")
    fun deleteUserAccount(@PathVariable email: String): Unit {
        return userAccountService.deleteUserAccount(email)
    }

    @DeleteMapping("/delete/all")
    fun deleteAllUserAccounts(): Unit {
        return userAccountService.deleteAllUserAccounts()
    }
}