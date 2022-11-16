package traction.backend.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import traction.backend.middleware.CustomAuthenticationFilter
import traction.backend.model.UserAccount
import traction.backend.model.dto.LoginDTO
import java.lang.Exception
import java.lang.RuntimeException
import java.util.logging.Logger
import java.util.*
import javax.security.auth.login.FailedLoginException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class AuthenticationService(
    private val userAccountService: UserAccountService
) {
    fun refreshUserAccountToken(request: HttpServletRequest, response: HttpServletResponse): Unit {
        var authorizationHeader: String = request.getHeader("refreshToken")
        if (authorizationHeader != null) {
            try {
                var refreshToken: String = authorizationHeader
                // TODO: Secure this secret
                var algorithm: Algorithm = Algorithm.HMAC256("mySecretKey")
                var verifier: JWTVerifier = JWT.require(algorithm)
                    .build() // build the verifier using the same secret used to encode the token
                var decodedJWT: DecodedJWT =
                    verifier.verify(refreshToken) // decode the token using the verifier we just built
                var email: String = decodedJWT.subject // get the email/username from the decoded token
                var userAccount: UserAccount? = userAccountService.getUserAccountByEmail(email)
                // Send back a new valid "token"
                if (userAccount != null) {
                    var accessToken: String = JWT.create()
                        .withSubject(userAccount.email)
                        .withExpiresAt(Date(System.currentTimeMillis() + CustomAuthenticationFilter.ACCESS_TOKEN_EXPIRY))
                        .withIssuer(request.requestURL.toString())
                        .withClaim("roles", userAccount.roles.map { role -> role.roleName }.toList())
                        .sign(algorithm)
                    response.setHeader("token", accessToken)
                    response.setHeader("refreshToken", refreshToken)
                    response.setHeader("userId", userAccount.userId.toString())
                    response.status = 200
                } else {
                    throw IllegalStateException("User account not found when trying to handle refresh token")
                }
            } catch(e: Exception) {
                response.setHeader("error", e.message)
                response.sendError(403)
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }

    fun logoutUserAccount(token: String): ResponseEntity<Any> {
        return ResponseEntity.ok("Logout Success")
    }
}