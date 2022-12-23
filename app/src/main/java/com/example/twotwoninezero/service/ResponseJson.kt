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

data class RegisterResponseList(var Error:String)

data class MainRegisterResponse(var registerResponse:RegisterResponse?,var registerResponseList:List<RegisterResponseList>?=null)

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

data class SavePaymentOptionResponse(
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
data class saveBulkTaxableVehicleResponse(
    val code: Int,
    val message: String,
)data class saveBulkReportingSuspendedResponse(
    val code: Int,
    val message: String,
)

data class SaveUpdateTGWIncreaseResponse(
    val code: Int,
    val message: String,
)
data class SaveUpdateExceededMileageResponse(
    val code: Int,
    val message: String,
)

data class SaveUpdateVinCorrectionResponse(
    val code: Int,
    val message: String,
)

data class DeleteTaxableVehicleResponse(
    val code: Int,
    val message: String,
)

data class UpdateConsentDisclosureResponse(
    val code: Int,
    val message: String,
    val messageList: List<String>,
)
data class DeleteTGWIncreaseByIdResponse(
    val code: Int,
    val message: String,
)
data class DeleteExceededMileageByIdResponse(
    val code: Int,
    val message: String,
)

data class DeleteVinCorrectionByIdResponse(
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

data class ChangePasswordResponse(
    val code: Int,
    val message: String,
)
data class ChangeLoginEmailResponse(
    val code: Int,
    val message: String,
)

data class UploadTaxpayerResponse(
    val code: Int,
    val message: String,
)

data class ValidateXMLResponse(
    val code: Int,
    val message: String,
    val messageList: List<String>?,
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

data class LoadFleetListResponse(
    val businessName: String,
    var isAgriculture: String,
    var isLogging: String,
    val vin: String,
    var weight: String,
    var weightCategory: String,
    var isCheckMe:Boolean=false
)


data class GetTGWIncreaseByFilingIdResponse(
    val changedCategory: String,
    val changedWeight: String,
    val differenceTaxAmount: String,
    val id: Int,
    val isLogging: String,
    val previousCategory: String,
    val previousWeight: String,
    val vin: String
)

data class GetExceededMileageByFilingIdResponse(
    val id: Int,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)


data class GetVinCorrectionByFilingIdResponse(
    val correctVin: String,
    val id: Int,
    val isLogging: String,
    val previousVin: String,
    val vinType: String,
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

data class GetIRSRejectionDetailsResponse(
    val irsRejectArray: List<String>
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

data class GetPriorSoldDateResponse(
    val filingId: Int,
    val maxDate: String,
    val minDate: String,
    val userId: Int
)

data class GetSoldAndDestoryDateResponse(
    val filingId: Int,
    val maxDate: String,
    val minDate: String,
    val userId: Int
)
data class GetLowMileageDateResponse(
    val filingId: Int,
    val maxDate: String,
    val minDate: String,
    val userId: Int
)
data class GetCreditOverPaymentDateResponse(
    val filingId: Int,
    val maxDate: String,
    val minDate: String,
    val userId: Int
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

data class GetTGWIncreaseByIdResponse(
    val changedCategory: String,
    val changedMonth: String,
    val changedMonthTaxAmount: String,
    val differenceTaxAmount: String,
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val previousCategory: String,
    val previousMonthTaxAmount: String,
    val vin: String
)

data class GetExceededMileageByIdResponse(
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weightCategory: String
)

data class GetVinCorrectionByIdResponse(
    val correctVin: String,
    val editMode: String,
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val previousVin: String,
    val vinCorrectionExplanation: String,
    val vinType: String,
    val weightCategory: String
)


/*Form Summary Starting*/

data class GetSummaryDetailsByFilingIdResponse(
    val amendement1: List<AmendementOne>,
    val amendement2: List<AmendementTwo>,
    val amendment1Total: String,
    val amendment2Total: String,
    val businessInfo: BusinessInfo,
    val creditOverPayment: List<CreditOverPayment>,
    val creditTotal: String,
    val currentSuspended: List<CurrentSuspended>,
    val filingInfo: FilingInfo,
    val irsPayment: IrsPayment?,
    val irsRejectArray: String,/*    val irsRejectArray: List<String>,*/
    val lowMileage: List<LowMileage>,
    val mileageTotal: String,
    val priorSuspended: List<PriorSuspended>,
    val soldTotal: String,
    val soldVehicle: List<SoldVehicle>,
    val taxableTotal: String,
    val taxableVehicle: List<TaxableVehicle>,
    val totalCreditAmt: String,
    val totalDueToIRS: String,
    val totalTaxAmt: String,
    val vinCorrection: List<VinCorrection>
)

data class AmendementOne(
    val active: Boolean,
    val changedCategory: String,
    val changedWeight: String,
    val differenceTaxAmount: String,
    val id: String,
    val isLogging: String,
    val previousCategory: String,
    val previousWeight: String,
    val vin: String
)

data class AmendementTwo(
    val active: Boolean,
    val id: String,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weight: String,
    val weightCategory: String,
)

data class BusinessInfo(
    val address1: String,
    val address2: String,
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
    val userId: Int,
    val zipCode: String
)

data class CreditOverPayment(
    var active:Boolean,
    var amountOfClaim:String,
    var explanation:String,
    var id:Int,
    var paymentDate:String,
    var vin:String,
)

data class CurrentSuspended(
    val active: Boolean,
    val filingId: Int,
    val id: Int,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class FilingInfo(
    val amendedMonth: String,
    val businessId: Int,
    val consentDisclosure: String,
    val displayTaxYr: String,
    val filingMonth: String,
    val filingYear: String,
    val finalReturn: String,
    val formType: String,
    val formTypeName: String,
    val taxYearEndMonth: String
)

data class LowMileage(
    val active: Boolean,
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

data class PriorSuspended(
    val active: Boolean,
    val id: Int,
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldDate: String,
    val soldToWhom: String,
    val vin: String
)

data class SoldVehicle(
    val active: Boolean,
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

data class TaxableVehicle(
    val active: Boolean,
    val filingId: Int,
    val id: Int,
    val isLogging: String,
    val taxAmount: String,
    val vin: String,
    val weight: String,
    val weightCategory: String
)

data class VinCorrection(
    val active: Boolean,
    val correctVin: String,
    val id: Int,
    val isLogging: String,
    val previousVin: String,
    val vinType: String,
    val weightCategory: String,
)

/*Form Summary Ending*/

/*profile*/

data class GetUpdateMyAccountDetailsResponse(
    val address: String,
    val countryId: Int,
    val decryptEmail: String,
    val decryptPhone: String,
    val decryptUserFirstName: String,
    val email: String,
    val emailLc: String,
    val emailToken: String,
    val firstName: String,
    val isLinkedinLogin: String,
    val isTaxPreparer: String,
    val isTwitterLogin: String,
    val languagePreference: String,
    val lastName: String?,
    val otherReferBy: String,
    val phone: String,
    val pinCode: String,
    val referedBy: String,
    val tokenGenerationDate: String
)

data class GetTransactionDetailsuserIDResponse(
    val amount: String,
    val bussinessName: String,
    val formType: String,
    val fromDate: Any,
    val paymentDate: String,
    val refId: Any,
    val status: String,
    val toDate: Any,
    val transactionId: String,
    val voucherNo: String
)

data class GetTaxPreparerResponse(
    val address1: String,
    val address2: String,
    val city: String,
    val countryId: String,
    val emailAddress: String,
    val firmEIN: String,
    val firmName: String,
    val foreignAddress: String,
    val foreignPhone: String,
    val phone: String,
    val preparerName: String,
    val preparerType: String,
    val ptin: String,
    val selfEmployed: String?,
    val state: String,
    val taxPreparer: String?,
    val zipCode: String,
)

data class IrsPayment(
    val paymentMode:String?,
    val retypebankAccNo:String?,
    val active:Boolean?,
    val bankName:String?,
    val acctNumber:String?,
    val createdDate:String?,
    val createdBy:Long?,
    val acctType:String?,
    val modifiedDate:String?,
    val filingId:Long?,
    val routingTransitNo:String?,
    val modifiedBy:Long?,
    val id:Long?,
    val userAcceptance:Boolean?,
)

data class PaymentOptionMethodResponse(
    val acctNumber: String,
    val acctType: String,
    val bankName: String,
    val filingId: Int,
    val id: Int,
    val paymentMode: String,
    val retypebankAccNo: String,
    val routingTransitNo: String
)

data class GetFilingByIdResponse(
    val ackCount: Int,
    val ackReceived: Int,
    val addressChange: String,
    val amendedMonth: String,
    val archive: String,
    val businessId: Int,
    val consentDisclosure: String,
    val consentToSubmit: Int,
    val couponCode: String,
    val dataMasked: Int,
    val filingMonth: String,
    val filingTaxYrId: Int,
    val filingYear: String,
    val finalReturn: String,
    val formPdfPath: String,
    val formType: String,
    val id: Int,
    val irsApproved: Int,
    val modifiedbyAdmin: Int,
    val pendingCount: Int,
    val sch1Received: Int,
    val scheduleCount: Int,
    val taxYearEndMonth: String,
    val thirdPartyDesignee: String,
    val userCompleted: Int,
    val xmlSubmitted: Int
)

data class SubmissionFeeResponse(
    val businessName: String,
    val couponCode: String,
    val couponStatus: String,
    val diffAmount: Double,
    val discountAmount: Double,
    val discountPercentage: Double,
    val ein: String,
    val feeAmount: Double,
    val formType: String,
    val paidAmount: Double,
    val serviceCharge: Double,
    val subTotal: Double,
    val vehicleCount: Int
)

data class GetPaymentDetResponse(
    val amount: Double,
    val refId: String,
    val token: String
)

data class CaptureCCPaymentResponse(
    val amount: String?,
    val bizName: String?,
    val cardType: String?,
    val filingId: String?,
    val prefilingContent: String?,
    val refId: String?,
    val resDescription: String?,
    val resMessageCode: String?,
    val status: String?,
    val submitStatus: String?,
    val transDate: String?,
    val transactionId: String?,
    val userId: Int?
)
