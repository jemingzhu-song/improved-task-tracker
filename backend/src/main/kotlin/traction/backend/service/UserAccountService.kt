package traction.backend.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import traction.backend.datasource.UserAccountRepository
import traction.backend.datasource.UserAccountRepositoryStub
import traction.backend.model.UserAccount
import java.lang.IllegalArgumentException
import java.util.*

@Service
class UserAccountService(
    private val userAccountRepositoryStub: UserAccountRepositoryStub,
    private val userAccountRepository: UserAccountRepository
){
    fun getUserAccountByEmail(email: String): UserAccount? {
        var userAccount: UserAccount? = userAccountRepository.findUserAccountByEmail(email).orElseThrow {
           IllegalStateException("User with email: $email does not exist")
        }
        return userAccount
    }

    fun getAllUserAccounts(): Collection<UserAccount> {
        return userAccountRepository.findAll()
    }

    fun editUserAccount(userAccount: UserAccount): UserAccount {
        return userAccountRepositoryStub.editUserAccount(userAccount)
    }

    fun createUserAccount(userAccount: UserAccount): UserAccount {
        return userAccountRepository.save(userAccount)
    }
}