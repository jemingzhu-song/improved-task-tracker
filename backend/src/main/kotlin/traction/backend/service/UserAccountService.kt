package traction.backend.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import traction.backend.datasource.UserAccountRepository
import traction.backend.datasource.UserAccountRepositoryStub
import traction.backend.model.UserAccount
import traction.backend.model.UserAccountEdit
import java.lang.IllegalArgumentException
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
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

    fun getUserIdByEmail(email: String): Long? {
        var userId: Long? = userAccountRepository.findUserIdByEmail(email).orElseThrow {
            IllegalStateException("User with email: $email does not exist")
        }
        return userId
    }

    fun getAllUserAccounts(): Collection<UserAccount> {
        return userAccountRepository.findAll()
    }

    fun createUserAccount(userAccount: UserAccount): UserAccount {
        if (userAccountRepository.findUserIdByEmail(userAccount.email).isPresent) {
            throw IllegalArgumentException("User with email: ${userAccount.email} already exists. Cannot register user")
        }

        return userAccountRepository.save(userAccount)
    }

    fun editUserAccount(userAccountEdit: UserAccountEdit): Unit {
        var currentUserAccount: UserAccount = userAccountRepository.findById(userAccountEdit.userId).orElseThrow {
            IllegalStateException("User with userId: ${userAccountEdit.userId} does not exist")
        }
        // Only update details that are different
        if (userAccountEdit.email != null && userAccountEdit.email != currentUserAccount.email) {
            currentUserAccount.email = userAccountEdit.email!!
        }
        if (userAccountEdit.firstName != null && userAccountEdit.firstName != currentUserAccount.firstName) {
            currentUserAccount.firstName = userAccountEdit.firstName!!
        }
        if (userAccountEdit.lastName != null && userAccountEdit.lastName != currentUserAccount.lastName) {
            currentUserAccount.lastName = userAccountEdit.lastName!!
        }
        if (userAccountEdit.password != null && userAccountEdit.password != currentUserAccount.password) {
            currentUserAccount.password = userAccountEdit.password!!
        }
        return
    }

    fun deleteUserAccount(email: String): Unit {
        var userAccount: UserAccount? = userAccountRepository.findUserAccountByEmail(email).orElseThrow {
            IllegalStateException("User with email: $email does not exist")
        }
        var userId: Long? = userAccount?.userId ?: null
        if (userId == null) {
            throw IllegalStateException("User with email: $email does not have a user_id")
        }
        return userAccountRepository.deleteById(userId)
    }
}