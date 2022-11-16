package traction.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import traction.backend.middleware.CustomAuthenticationFilter
import traction.backend.middleware.CustomAuthorizationFilter
import traction.backend.service.UserAccountService

//import traction.backend.middleware.CustomAuthorizationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val userAccountService: UserAccountService,
): WebSecurityConfigurerAdapter() {

    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder?) {
        if (auth != null) {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
        }
    }

    override fun configure(http: HttpSecurity?) {
        var customAuthenticationFilter: CustomAuthenticationFilter = CustomAuthenticationFilter(authenticationManagerBean(), userAccountService)
        // Customise the "login" route, otherwise, it'll default to "/login" instead
        customAuthenticationFilter.setFilterProcessesUrl("/user/account/login")
        if (http != null) {
            http.csrf().disable()
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            http.authorizeRequests().antMatchers("/user/account/get/account/all", "/user/account/login", "/user/account/register", "/user/account/logout", "/user/account/token/refresh").permitAll()
            http.authorizeRequests().anyRequest().authenticated()
            http.addFilter(customAuthenticationFilter)
            http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    @Bean
    @Override
    override fun authenticationManagerBean(): AuthenticationManager {
         return super.authenticationManager()
    }
}