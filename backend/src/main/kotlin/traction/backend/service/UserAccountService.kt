package traction.backend.service

import org.springframework.stereotype.Service
import traction.backend.datasource.UserAccountRepository
import traction.backend.datasource.UserAccountRepositoryStub
import traction.backend.model.UserAccount
import traction.backend.model.helper.UserAccountEdit
import java.lang.IllegalArgumentException
import javax.transaction.Transactional

@Service
@Transactional
class UserAccountService(
    private val userAccountRepositoryStub: UserAccountRepositoryStub,
    private val userAccountRepository: UserAccountRepository
){
    fun getUserAccountById(userId: Long): UserAccount {
        var userAccount: UserAccount = userAccountRepository.findById(userId).orElseThrow {
            IllegalStateException("User with userId: $userId does not exist")
        }
        return userAccount
    }

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
        // Only update details that have been provided and are different
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
    }

    fun deleteUserAccount(email: String): Unit {
        var userAccount: UserAccount? = userAccountRepository.findUserAccountByEmail(email).orElseThrow {
            IllegalStateException("User with email: $email does not exist")
        }
        var userId: Long? = userAccount?.userId ?: null
        if (userId == null) {
            throw IllegalStateException("User with email: $email does not have a user_id")
        }
        userAccountRepository.deleteById(userId)
    }

    fun deleteAllUserAccounts(): Unit {
        userAccountRepository.deleteAll()
    }
}