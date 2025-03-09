package security.me.com.kotlinProject.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import security.me.com.kotlinProject.common.jwt.JwtProvider
import security.me.com.kotlinProject.security.filter.TokenAuthenticationFilter
import security.me.com.kotlinProject.security.service.AccessAuthorizationService
import security.me.com.kotlinProject.security.service.AuthenticationTokenVerifier

@Configuration
class SpringSecurityConfig(
    @Value("\${spring.security.debug:false}")
    private val webSecurityDebug: Boolean,
    private val messageSource: MessageSource,
    private val jwtProvider: JwtProvider,
    @Qualifier("jwtAuthenticationTokenVerifier") private val verifier: AuthenticationTokenVerifier,
    @Qualifier("userAuthorizationService") private val authorizationService: AccessAuthorizationService,
) {
    companion object {
        private const val loginProcessingUrl: String = "/api/v1/sign/login"
        private const val signUpProcessingURl: String = "/api/v1/sign/signup"
    }

    @Bean
    fun filterChain(http: HttpSecurity, introspector: HandlerMappingIntrospector): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .securityMatcher(MvcRequestMatcher(introspector, "/api/**"))
            .authorizeHttpRequests {
                it.requestMatchers(MvcRequestMatcher(introspector, loginProcessingUrl), MvcRequestMatcher(introspector, signUpProcessingURl)).permitAll()
                    .requestMatchers(MvcRequestMatcher(introspector, "/h2-console")).permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint())
                it.accessDeniedHandler(accessDeniedHandler())
            }
            .addFilterBefore(TokenAuthenticationFilter(jwtProvider, verifier, authorizationService), CsrfFilter::class.java)

        return http.build()
    }

    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return TokenAuthenticationEntryPoint(messageSource)
    }

    fun accessDeniedHandler(): AccessDeniedHandler {
        return ResourceAccessDeniedHandler(messageSource)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}