package traction.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import traction.backend.model.UserAccount
import traction.backend.model.helper.UserAccountEdit
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

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
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

    @PostMapping
    fun createUserAccount(@RequestBody userAccount: UserAccount): UserAccount {
        return userAccountService.createUserAccount(userAccount)
    }

    @PutMapping
    fun editUserAccount(@RequestBody userAccountEdit: UserAccountEdit): Unit {
        return userAccountService.editUserAccount(userAccountEdit)
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