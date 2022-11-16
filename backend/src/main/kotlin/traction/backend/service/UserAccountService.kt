package traction.backend.service

import net.bytebuddy.build.Plugin.Factory.Simple
import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import traction.backend.datasource.UserAccountRepository
import traction.backend.model.UserAccount
import traction.backend.model.dto.LoginDTO
import traction.backend.model.dto.UserAccountEditDTO
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import javax.security.auth.login.FailedLoginException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@Service
@Transactional
class UserAccountService(
    private val userAccountRepository: UserAccountRepository
): UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        var userAccount: UserAccount = userAccountRepository.findUserAccountByEmail(email).orElseThrow {
            IllegalStateException("User with email: $email does not exist")
        }

        var authorities: MutableList<SimpleGrantedAuthority> = mutableListOf()
        // Loop through all the roles of the user, and create a "SimpleGrantedAuthority" role
        userAccount.roles.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }

        return User(userAccount.email, userAccount.password, authorities)
    }

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

    fun editUserAccount(userAccountEditDTO: UserAccountEditDTO): Unit {
        var currentUserAccount: UserAccount = userAccountRepository.findById(userAccountEditDTO.userId).orElseThrow {
            IllegalStateException("User with userId: ${userAccountEditDTO.userId} does not exist")
        }
        // Only update details that have been provided and are different
        if (userAccountEditDTO.email != null && userAccountEditDTO.email != currentUserAccount.email) {
            currentUserAccount.email = userAccountEditDTO.email!!
        }
        if (userAccountEditDTO.firstName != null && userAccountEditDTO.firstName != currentUserAccount.firstName) {
            currentUserAccount.firstName = userAccountEditDTO.firstName!!
        }
        if (userAccountEditDTO.lastName != null && userAccountEditDTO.lastName != currentUserAccount.lastName) {
            currentUserAccount.lastName = userAccountEditDTO.lastName!!
        }
        if (userAccountEditDTO.password != null && userAccountEditDTO.password != currentUserAccount.password) {
            currentUserAccount.password = userAccountEditDTO.password!!
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