package traction.backend.datasource

import org.springframework.stereotype.Repository
import traction.backend.model.UserAccount

@Repository
class UserAccountRepositoryStub {

    private val sampleUserAccountDatabase: MutableList<UserAccount> = mutableListOf(
        UserAccount(832L, "John", "Kim", "john.kim@gmail.com", "johnny43612", emptyList()),
        UserAccount(113L, "Alison", "Chen", "alison123@gmail.com", "cookiesilove82", emptyList()),
        UserAccount(2332L, "Charles", "Zhou", "charles_zhou@gmail.com", "charlesrulesall333", emptyList()),
    )

    fun getUserAccount(userId: Long): UserAccount {
        return sampleUserAccountDatabase.first { it.userId == userId }
    }

    fun getAllUserAccounts(): Collection<UserAccount> {
        return sampleUserAccountDatabase
    }

    fun editUserAccount(userAccount: UserAccount): UserAccount {
        val currentUserAccount: UserAccount? = sampleUserAccountDatabase.firstOrNull { it.userId == userAccount.userId }
        if (currentUserAccount == null) {
            throw NoSuchElementException("Could not find a User Account with userId: ${userAccount.userId}")
        }
        sampleUserAccountDatabase.remove(currentUserAccount)
        sampleUserAccountDatabase.add(userAccount)
        return userAccount
    }
}