package com.example.twotwoninezero.service

import java.io.File

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

data class GetCountryItem(
    val countryName: String,
    val id: Int
)

data class GetBusinessTypeRequestItem(
    val businessType: String,
    val id: Int,
    val name: String
)

data class UpdateAndNewBusinessResponse(
    val code:Int,
    val message:String
)


data class AddNewFleetResponse(
    val code:Int,
    val message:String
)

data class ArchiveBusinessResponse(
    val code: Int,
    val message: String,
)

data class DeleteFleetResponse(
    val code: Int,
    val message: String,
)

data class ReActivateBusinessResponse(
    val code: Int,
    val message: String,
)

data class FileDownloadResponse (
    var error: Boolean,
    var msg: String?,
    var file : File?
)

data class GetStateReponse(
    val countryId: Int,
    val id: Int,
    val stateCode: String,
    val stateName: String
)

data class GetBusinessNameResponse(
    val businessName: String,
    val businessType: Int,
    val id: Int,
    val thirdpartyStatus: Boolean
)

data class TaxableWeightResponse(
    val monthRemaining: Int,
    val weight: String,
    val weightCategory: String
)

data class AllAndSearchBusinessListResponse(
    val businessName: String,
    val businessType: Int,
    val businessTypeName: String,
    val ein: String,
    val email: String,
    val id: Int,
    val phone: String,
    val thirdpartyStatus: Boolean
)

data class EditBusinessListResponse(
    val address1: String,
    val address2: String,
    val archive: String,
    val bizType: String,
    val businessName: String,
    val businessType: Int,
    val city: String,
    val countryId: String,
    val countryName: String,
    val decryptBizName: String,
    val decryptEmail: String,
    val decryptPhone: String,
    val ein: String,
    val email: String,
    val id: Int,
    val phone: String,
    val signingAuthorityName: String,
    val signingAuthorityPhone: String,
    val signingAuthorityPin: String,
    val signingAuthorityTitle: String,
    val stateId: String,
    val stateName: String,
    val thirdPartyDesigneeName: String,
    val thirdPartyDesigneePhone: String,
    val thirdPartyDesigneePin: String,
    val thirdpartyStatus: Boolean,
    val zipCode: String
)

data class GetFleetByIdResponse(
    val businessId: Int,
    val id: Int,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)

data class GetFleetListResponse(
    val businessId: Int,
    val businessName: String,
    val id: Int,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)