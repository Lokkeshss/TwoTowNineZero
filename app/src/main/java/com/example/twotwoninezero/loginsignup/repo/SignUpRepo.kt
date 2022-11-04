package com.example.twotwoninezero.loginsignup.repo

import com.example.twotwoninezero.ThisApplication
import com.example.twotwoninezero.service.*

class SignUpRepo (private val api: Api) : BaseRepository() {

    suspend fun doLogin(i: LoginRequest): ApiResponse<LoginResponse>? {
        val resp = doApiCall { api.loginRequest(i).await() }
        if (resp?.error == false) {
            // store the token in application
            val loginResp = resp.data as LoginResponse
            ThisApplication.publicPrefs.jwtToken = loginResp.token
         /*   ThisApplication.publicPrefs.roleId = loginResp.role_id.toString()
            if (rememberMe!!) {
                ThisApplication.publicPrefs.rememberMe = rememberMe
            }*/
        }

        return resp
    }
    suspend fun doRegister(i: RegistrationRequest): ApiResponse<RegisterResponse>? {
        val resp = doApiCall { api.registerRequest(i).await() }
        if (resp?.error == false) {
            // store the token in application
            val registerResp = resp.data as RegisterResponse
            ThisApplication.publicPrefs.jwtToken = registerResp.token
            /*   ThisApplication.publicPrefs.roleId = loginResp.role_id.toString()
               if (rememberMe!!) {
                   ThisApplication.publicPrefs.rememberMe = rememberMe
               }*/
        }
        return resp
    }

    suspend fun doForgotPassword(i: ForgotPasswordEmailRequest): ApiResponse<ForgotPasswordResponse>? {
        val resp = doApiCall { api.forgotPasswordRequest(i).await() }
        return resp
    }
}