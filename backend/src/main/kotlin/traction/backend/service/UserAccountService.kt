package traction.backend.service

import org.springframework.stereotype.Service
import traction.backend.datasource.UserAccountRepository
import traction.backend.model.UserAccount

@Service
class UserAccountService(
    private val userAccountRepository: UserAccountRepository
){
    fun getUserAccount(userId: Long): UserAccount {
        return userAccountRepository.getUserAccount(userId)
    }

    fun editUserAccount(userAccount: UserAccount): UserAccount {
        return userAccountRepository.editUserAccount(userAccount)
    }
}