package traction.backend.middleware

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import traction.backend.service.UserAccountService
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomAuthenticationFilter(
    @get:JvmName("getCustomAuthenticationManager") private val authenticationManager: AuthenticationManager,
    private val userAccountService: UserAccountService
): UsernamePasswordAuthenticationFilter() {

    companion object {
        val ACCESS_TOKEN_EXPIRY: Long = (10 * 60 * 1000)
        val REFRESH_TOKEN_EXPIRY: Long = (30 * 60 * 1000)
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        if (request != null) {
            var email: String = request.getParameter("email")
            var password: String = request.getParameter("password")
            logger.info("Email is: $email, Password is: $password")

            var authenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(email, password)
            return authenticationManager.authenticate(authenticationToken)
        } else {
            throw Exception("Failed to authenticate at CustomerAuthenticationFilter.attemptAuthentication()")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authentication: Authentication?
    ) {
        var user: User = authentication?.principal as User

        // TODO: secure this secret key
        var algorithm: Algorithm = Algorithm.HMAC256("mySecretKey")
        var accessToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
            .withIssuer(request.requestURL.toString())
            .withClaim("roles", user.authorities.map { role -> role.authority}.toList())
            .sign(algorithm)

        var refreshToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
            .withIssuer(request.requestURL.toString())
            .withClaim("roles", user.authorities.map { role -> role.authority}.toList())
            .sign(algorithm)

        response.setHeader("token", accessToken)
        response.setHeader("refreshToken", refreshToken)

        // Get the userId to return in the response
        var userId: Long? = userAccountService.getUserIdByEmail(user.username)
        response.setHeader("userId", userId.toString())
    }
}