package security.me.com.kotlinProject.member.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import security.me.com.kotlinProject.common.model.ApiResultResponse
import security.me.com.kotlinProject.member.dto.LoginRequest
import security.me.com.kotlinProject.member.dto.LoginResult
import security.me.com.kotlinProject.member.dto.LoginUserResponse
import security.me.com.kotlinProject.member.dto.UserCreateRequest
import security.me.com.kotlinProject.member.service.UserSignService
import security.me.com.kotlinProject.security.model.ClientAuthenticationToken

@RestController
@RequestMapping("/api/v1/sign")
class SignController(
    private val userSignService: UserSignService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ApiResultResponse<LoginResult> {
        val result = userSignService.login(request)

        return ApiResultResponse.success(result)
    }

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: UserCreateRequest): ApiResultResponse<Unit> {
        userSignService.create(request)
        return ApiResultResponse.success()
    }

    @GetMapping("/user")
    fun loginUser(): ApiResultResponse<LoginUserResponse> {

        val token = SecurityContextHolder.getContext().authentication as ClientAuthenticationToken
        val user = userSignService.findUserId(token.authInfo.id)!!
        return ApiResultResponse.success(LoginUserResponse(
            userId = user.id!!,
            userName = user.name,
            email = user.email,
            roles = user.roles,
            status = user.status!!,
            createdAt = user.createdAt,
            blocked = user.blocked!!
        ))
    }
}