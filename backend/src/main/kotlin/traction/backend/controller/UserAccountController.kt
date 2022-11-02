package traction.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import traction.backend.model.UserAccount
import traction.backend.service.UserAccountService

@RestController
@RequestMapping("user/account")
class UserAccountController(
    private val userAccountService: UserAccountService
) {
    @GetMapping
    fun getUserAccount(@RequestBody userId: Long): UserAccount {
        return userAccountService.getUserAccount(userId)
    }

    @PutMapping
    fun editUserAccount(@RequestBody userAccount: UserAccount): UserAccount {
        return userAccountService.editUserAccount(userAccount)
    }
}