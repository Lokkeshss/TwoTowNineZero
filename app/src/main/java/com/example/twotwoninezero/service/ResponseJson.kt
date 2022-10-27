package com.example.twotwoninezero.service

data class ApiErrorResponse(
    var errMsg : String? = ""
)

data class ApiResponse<T>(
    var isRedirect: Boolean = false,
    var data      : T? = null,
    var error     : Boolean = false,
    var msg       : String? = null
)

data class RegisterResponse(var code:Int,var message:String,var messageList:String,var filingId:String,
var userId:String,var token:String)

data class LoginResponse(var code:Int,var message:String,var messageList:String,var filingId:String,
var userId:String,var token:String)

data class ForgotPasswordResponse(
    val code: Int,
    val filingId: Any,
    val message: String,
    val messageList: Any,
    val token: Any,
    val userId: Any
)
