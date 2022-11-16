package traction.backend.middleware

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import java.net.http.HttpHeaders
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter: OncePerRequestFilter() {
    companion object {
        val AUTHORIZATION_EXCLUDE_URL_LIST: List<String> = listOf("/user/account/login", "/user/account/register",
            "/user/account/token/refresh", "/user/account/logout", "/user/account/get/account/all",
        "/user/task/get/recent/week/{userId}")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // If user is trying to login, don't do anything, let the request go through
        if (AUTHORIZATION_EXCLUDE_URL_LIST.contains(request.servletPath)) {
            filterChain.doFilter(request, response)
        } else {
            var authorizationHeader: String = request.getHeader("token")
            if (authorizationHeader != null) {
                try {
                    var token: String = authorizationHeader
                    // TODO: Secure this secret
                    var algorithm: Algorithm = Algorithm.HMAC256("mySecretKey")
                    var verifier: JWTVerifier = JWT.require(algorithm)
                        .build() // build the verifier using the same secret used to encode the token
                    var decodedJWT: DecodedJWT =
                        verifier.verify(token) // decode the token using the verifier we just built
                    var email: String = decodedJWT.subject // get the email/username from the decoded token
                    var roles: Array<String> = decodedJWT.getClaim("roles").asArray(String::class.java) // get the roles of this user
                    var authorities: MutableList<SimpleGrantedAuthority> = mutableListOf()
                    roles.forEach { role -> authorities.add(SimpleGrantedAuthority(role)) }
                    // We pass "null" for the password because we don't need it - the user has already been authenticated
                    var authenticationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(email, null, authorities)
                    /* Set this user in the SecurityContextHolder - this tells Spring Security: "here's the user, and their roles" -
                    Spring Security will know what resources this user can access and what they cannot access */
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch(e: JWTVerificationException) {
                    logger.info("Token verification failed: ${e.message}.")
                    response.setHeader("error", "expired token")
                    response.sendError(403)
                }
                catch(e: Exception) {
                    logger.info("Error logging in: ${e.message}")
                    response.setHeader("error", e.message)
                    response.sendError(403)
                }

            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}