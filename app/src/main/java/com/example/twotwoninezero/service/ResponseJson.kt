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
data class SaveCurrentSuspendResponse(
    val code: Int,
    val message: String,
)
data class UpdateCurrentSuspendResponse(
    val code: Int,
    val message: String,
)
data class UpdatePriorSuspendedResponse(
    val code: Int,
    val message: String,
)
data class SavePriorSuspendedResponse(
    val code: Int,
    val message: String,
)
data class DeletePriorSuspendedResponse(
    val code: Int,
    val message: String,
)

data class SaveSoldDestroyedVehicleResponse(
    val code: Int,
    val message: String,
)
data class SaveCreditOverPaymentResponse(
    val code: Int,
    val message: String,
)
data class UpdateCreditOverPaymentResponse(
    val code: Int,
    val message: String,
)

data class SaveLowMileageVehicleResponse(
    val code: Int,
    val message: String,
)
data class UpdateLowMileageVehicleResponse(
    val code: Int,
    val message: String,
)


data class updateSoldDestroyedVehicle(
    val code: Int,
    val message: String,
)


data class SaveUpdateFilingResponse(
    val code: Int,
    val message: String,
    val filingId: String,
)

data class SaveTaxableVehicleResponse(
    val code: Int,
    val message: String,
)

data class DeleteTaxableVehicleResponse(
    val code: Int,
    val message: String,
)

data class DeleteCurrentSuspendedeById(
    val code: Int,
    val message: String,
)
data class DeleteSoldDestroyedResponse(
    val code: Int,
    val message: String,
)
data class DeleteCreditOverPaymentById(
    val code: Int,
    val message: String,
)
data class DeleteLowMileageByIdResponse(
    val code: Int,
    val message: String,
)

data class DeleteHomeScreenFilingResponse(
    val code: Int,
    val message: String,
)

data class ReactivateHomeScreenFilingResponse(
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

data class GetFillingFormResponse(
    val desc: String,
    val type: String,
    val xmlTagValue: String
)

data class GetFillingTaxYearResponse(
    val displayYear: String,
    val formType: String,
    val id: Int,
    val taxComputationYear: Long,
    val taxPeriodEndDate: Long,
    val taxPeriodStartDate: Long,
    val taxYear: String,
    val xsdVersion: String
)

data class getFillingFirstUsedMonthResponse(
    val firstUsedMonth: String,
    val firstUsedMonthId: String
)

data class GetTaxableVehicleResponse(
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)

data class HomeScreenListResponse(
    val ackCount: Int,
    val ackReceived: Int,
    val businessId: Int,
    val businessName: String,
    val consentToSubmit: Int,
    val createdDate: String,
    val dataMasked: Int,
    val ein: String,
    val feedbackstatus: String,
    val filingId: Int,
    val filingMonth: String,
    val filingStatus: String,
    val filingYear: String,
    val formType: String,
    val irsApproved: Int,
    val limit: Int,
    val modifiedbyAdmin: Int,
    val offset: Int,
    val paymentStatus: String,
    val pendingCount: Int,
    val recordCount: Int,
    val sch1Received: Int,
    val scheduleCount: Int,
    val userCompleted: Int,
    val xmlSubmitted: Int
)

data class FilterCategory(var name:String,var id:String)

data class EditTaxableVehicleByIdResponse(
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weightCategory: String
)

data class GetCurrentSuspendedByFilingIdResponse(
    val filingId: Int,
    val id: Int,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)
data class EditGetCurrentSuspendedByIdResponse(
    val filingId: Int,
    val id: Int,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class EditgetSoldDestroyedByIdResponse(
    val creditAmount: String,
    val documentName: String,
    val filingId: Int,
    val firstUsedMonth: String,
    val id: Int,
    val isLogging: String,
    val lossType: String,
    val refundExplanation: String,
    val soldDestroyedDate: String,
    val vin: String,
    val weightCategory: String
)

data class GetCreditOverPaymentByIdResponse(
    val amountOfClaim: String,
    val documentName: String,
    val explanation: String,
    val filingId: Int,
    val id: Int,
    val paymentDate: String,
    val vin: String
)

data class GetPriorSuspendedByFilingIdResponse(
    val id: Int,
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldToWhom: String,
    val vin: String,
    val soldDate: String
)

data class GetPriorSuspendedByIdResponse(
    val filingId: Int,
    val id: Int,
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldDate: String,
    val soldToWhom: String,
    val vin: String
)

data class GetSoldDestroyedByFilingIdResponse(
    val creditAmount: String,
    val documentName: String,
    val filingId: Int,
    val firstUsedMonth: String,
    val id: Int,
    val isLogging: String,
    val lossType: String,
    val refundExplanation: String,
    val soldDestroyedDate: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)


data class GetCreditOverPaymentByFilingIdResponse(
    val amountOfClaim: String,
    val documentName: Any,
    val explanation: String,
    val filingId: Any,
    val id: Int,
    val paymentDate: String,
    val vin: String
)

data class GetLowMileageByFilingIdResponse(
    val creditAmount: String,
    val documentName: String,
    val firstUsedMonth: String,
    val id: Int,
    val isLogging: String,
    val refundExplanation: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)

data class GetLowMileageByIdResponse(
    val creditAmount: String,
    val documentName: String,
    val filingId: Int,
    val firstUsedMonth: String,
    val id: Int,
    val isLogging: String,
    val refundExplanation: String,
    val vin: String,
    val weightCategory: String
)