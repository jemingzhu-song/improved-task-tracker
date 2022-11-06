package traction.backend.controller

import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import traction.backend.model.UserAccount
import traction.backend.model.dto.LoginDTO
import traction.backend.model.dto.UserAccountEditDTO
import traction.backend.service.UserAccountService
import java.lang.IllegalArgumentException
import javax.security.auth.login.FailedLoginException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("user/account")
class UserAccountController(
    private val userAccountService: UserAccountService
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

    @PostMapping("/login")
    fun loginUserAccount(@RequestBody loginDetails: LoginDTO, httpServletResponse: HttpServletResponse): UserAccount {
        return userAccountService.loginUserAccount(loginDetails, httpServletResponse)
    }

    @PostMapping("/logout")
    fun logoutUserAccount(httpServletResponse: HttpServletResponse): ResponseEntity<Any> {
        return userAccountService.logoutUserAccount(httpServletResponse)
    }

    @GetMapping("/authenticate")
    fun authenticateUser(@CookieValue("token") token: String?): ResponseEntity<Any> {
        println("========== $token")
        return userAccountService.authenticateUser(token)
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