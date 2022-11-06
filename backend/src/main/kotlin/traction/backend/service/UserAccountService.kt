package traction.backend.service

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
) {

    fun loginUserAccount(loginDetails: LoginDTO, httpServletResponse: HttpServletResponse): UserAccount {
        var userAccount: UserAccount = userAccountRepository.findUserAccountByEmail(loginDetails.email).orElseThrow {
            IllegalStateException("User with email: ${loginDetails.email} does not exist")
        }
        if (userAccount.comparePasswords(loginDetails.password)) {
            var issuer: String = userAccount.userId.toString()
            val jwt: String = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date((System.currentTimeMillis() + (60 * 10 * 1000)))) // 10 Minutes
                .signWith(SignatureAlgorithm.HS256, "secretTpStoreAsEnvironmentVariable") // TODO: Store this secret as an Environment Variable
                .compact()

            val cookie = Cookie("token", jwt)
            cookie.isHttpOnly = true
            httpServletResponse.addCookie(cookie)

            return userAccount
        } else {
            throw FailedLoginException("Incorrect password. User Login Failed.")
        }
    }

    fun logoutUserAccount(httpServletResponse: HttpServletResponse): ResponseEntity<Any> {
        var cookie = Cookie("token", "")
        cookie.maxAge = 0

        httpServletResponse.addCookie(cookie)

        return ResponseEntity.ok("Logout Success")
    }

    fun authenticateUser(token: String?): ResponseEntity<Any> {
        if (token == null) {
            throw FailedLoginException("Token invalid. Please login.")
        }
        try {

            // TODO: Store this secret as an Environment Variable
            val body = Jwts.parser().setSigningKey("secretTpStoreAsEnvironmentVariable").parseClaimsJws(token).body

            return ResponseEntity.ok(userAccountRepository.findById(body.issuer.toLong()))
        } catch(e: Exception) {
            throw FailedLoginException("Token invalid. Please login.")
        }
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