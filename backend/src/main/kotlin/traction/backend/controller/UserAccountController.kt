package traction.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import traction.backend.model.UserAccount
import traction.backend.service.UserAccountService
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("user/account")
class UserAccountController(
    private val userAccountService: UserAccountService
) {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/get/{email}")
    fun getUserAccountByEmail(@PathVariable email: String): UserAccount? {
        var userAccount: UserAccount? = userAccountService.getUserAccountByEmail(email)
        return userAccount
    }

    @GetMapping("/get/all")
    fun getAllUserAccounts(): Collection<UserAccount> {
        return userAccountService.getAllUserAccounts()
    }

    @PostMapping
    fun createUserAccount(@RequestBody userAccount: UserAccount): UserAccount {
        return userAccountService.createUserAccount(userAccount)
    }

    @PutMapping
    fun editUserAccount(@RequestBody userAccount: UserAccount): UserAccount {
        return userAccountService.editUserAccount(userAccount)
    }
}