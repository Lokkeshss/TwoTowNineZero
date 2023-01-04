package com.example.twotwoninezero.service

data class RegistrationRequest(var email:String,var firstName:String,var password:String,
                               var isTaxPreparer:String,
                               var userType:String,var referedBy:String)

data class LoginRequest(var emailId:String,var password:String)

data class ForgotPasswordEmailRequest(
    val emailId: String,
    val resetType: String
)

data class ForgotPasswordByBusinessRequest(
    val emailToken: String,
    val password: String
)

data class CreateAndUpdateBusinessRequest(
    val addressLine1: String,
    val businessName: String,
    val businessType: Int,
    val city: String,
    val countryId: String,
    val daytoPhoneNumber: String,
    val ein: String,
    val emailAddress: String,
    val phoneNumber: String,
    val signingAuthorityName: String,
    val signingAuthorityTitle: String,
    val stateId: String,
    val thirdpartyName: String,
    val thirdpartyPhoneNumber: String,
    val thirdpartyStatus: Boolean,
    val zipCode: String,
    val signingPIN: String,
    val thirdpartyPIN: String,

)

data class SearchBusinessRequest(
    val businessName: String,
    val ein: String,
    val limit: Int,
    val listType: String,
    val offset: Int
)

/*data class AddNewFleetBusinessRequest(
    val bizId: String,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)*/

data class AddNewFleetBusinessRequest(
    val businessId: String,
    val businessName: String,
    val fleetid: String,
    val isAgriculture: String,
    val isLogging: String,
    val name: String,
    val vin: String,
    val weightCategory: String
)

data class SaveUpdateFilingRequest(
    val addressChange: String,
    val amendedMonth: String,
    val businessId: String,
    val filingId: String,
    val filingMonth: String,
    val filingYearId: String,
    val finalReturn: String,
    val formType: String,
    val taxYearEndMonth: String
)


data class SaveTaxableVehicleRequest(
    val filingId: String,
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)

data class SaveBulkTaxableVehicleRequest(
    val filingId: String,
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)

data class SaveBulkCurrentSuspendedRequest(
    val filingId: String,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class SaveUpdateTGWIncreaseRequest(
    val changedCategory: String,
    val isLogging: String,
    val previousCategory: String,
    val vin: String,
    val filingId: String
)

data class SaveUpdateExceededMileageRequest(
    val filingId: String,
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)

data class SaveUpdateVinCorrectionRequest(
    val correctVin: String,
    val explanation: String,
    val filingId: String,
    val isLogging: String,
    val previousVin: String,
    val vinType: String,
    val weightCategory: String
)

data class SaveAndUpdateFilingRequest(
    val addressChange: String,
    val amendedMonth: String,
    val businessId: Int,
    val filingMonth: String,
    val filingYearId: String,
    val finalReturn: String,
    val formType: String
)

data class SearchFillingRequest(
    val category: String,
    val fromDate: String,
    val isPartial: String,
    val keyword: String,
    val limit: String,
    val listType: String,
    val offset: String,
    val toDate: String
)
data class SaveFeedbackRequest(
    val appId: String,
    val customerService: String,
    val disLike: String,
    val feedbackStatus: String,
    val filingExperience: String,
    val firstTimeUsed: Int,
    val like: String,
    val otherValue: String,
    val satisfied: Int,
    val usedBecause: String,
    val usedInFuture: Int,
    val userId: Int,
    val userName: String,
    val willRecommend: Int
)

data class SaveUpdateTaxableVehicleRequest(
    val isLogging: String,
    val vin: String,
    val weightCategory: String
)

data class SaveUpdatePriorSuspendRequest(
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldDate: String,
    val soldToWhom: String,
    val vin: String
)

data class SaveUpdateCurrentSuspendRequest(
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

class SaveBulkCurrentSuspendRequest : ArrayList<SaveBulkCurrentSuspendRequestItem>()

data class SaveBulkCurrentSuspendRequestItem(
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class SaveSoldDestroyedRequest(
    val documentName: String,
    val firstUsedMonth: String,
    val isLogging: String,
    val lossType: String,
    val refundExplanation: String,
    val soldDate: String,
    val vin: String,
    val weightCategory: String
)

data class SaveCreditOverPaymentReuest(
    val amountOfClaim: String,
    val explanation: String,
    val paymentDate: String,
    val vin: String
)

data class HomeScreenGetFilingsByUserIdRequest(
    val limit: Int,
    val listType: String,
    val offset: Int
)


data class FilingFilterRequest(
    val category: String,
    val fromDate: String,
    val isPartial: String,
    val keyword: String,
    val limit: String,
    val listType: String,
    val offset: String,
    val toDate: String
)

data class SaveCurrentSuspendRequest(
    val filingId: String,
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class UpdateCurrentSuspendRequest(
    val isAgriculture: String,
    val isLogging: String,
    val vin: String
)

data class UpdatePriorSuspendedRequest(
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldDate: String,
    val soldToWhom: String,
    val vin: String
)

data class SavePriorSuspendedRequest(
    val filingId: String,
    val isExceededMileage: String,
    val isVehicleSold: String,
    val soldDate: String,
    val soldToWhom: String,
    val vin: String
)

data class SaveSoldDestroyedVehicleRequest(
    val documentName: String,
    val filingId: String,
    val firstUsedMonth: String,
    val isLogging: String,
    val lossType: String,
    val refundExplanation: String,
    val soldDate: String,
    val vin: String,
    val weightCategory: String
)


data class SaveCreditOverPaymentRequest(
    val amountOfClaim: String,
    val documentName: String,
    val explanation: String,
    val filingId: String,
    val paymentDate: String,
    val vin: String
)

data class UpdateCreditOverPaymentRequest(
    val amountOfClaim: String,
    val documentName: String,
    val explanation: String,
    val filingId: String,
    val paymentDate: String,
    val vin: String
)

data class SaveLowMileageVehicleRequest(
    val documentName: String,
    val filingId: String,
    val firstUsedMonth: String,
    val isLogging: String,
    val refundExplanation: String,
    val vin: String,
    val weightCategory: String
)

data class UpdateLowMileageVehicleRequest(
    val documentName: String,
    val firstUsedMonth: String,
    val isLogging: String,
    val refundExplanation: String,
    val vin: String,
    val weightCategory: String
)

data class UpdateSoldDestroyedVehicleRequest(
    val documentName: String,
    val firstUsedMonth: String,
    val isLogging: String,
    val lossType: String,
    val refundExplanation: String,
    val soldDate: String,
    val vin: String,
    val weightCategory: String
)


data class UpdateMyAccountDetailsRequest(
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

data class ChangePasswordRequest(
    val confirmPassword:String,
    val loginPassword:String,
    val newPassword:String
)

data class ChangeLoginEmailRequest(
    val confirmMail:String,
    val email:String,
    val newEmailId:String,
    val requesterName:String,
)

data class TaxPreparerRequest(
    val address1: String,
    val address2: String,
    val city: String,
    val countryId: String,
    val emailAddress: String,
    val firmEIN: String,
    val firmName: String,
    val foreignAddress: String,
    val foreignPhone: String,
    val optradio: String,
    val optradio1: String,
    val phone: String,
    val preparerName: String,
    val preparerType: String,
    val ptin: String,
    val selfEmployed: String,
    val state: String,
    val taxPreparer: String,
    val zipCode: String,

)

data class SavePaymentOptionRequest(
    val acctNumber: String,
    val acctType: String,
    val bankName: String,
    val checkBox: String,
    val checkBox1: String,
    val checkBox2: String,
    val filingId: String,
    val id: String,
    val paymentMode: String,
    val retypebankAccNo: String,
    val routingTransitNo: String
)

data class ApplyCouponRequest(
    val couponCode:String =""
)
data class UpdateConsentDisclosureRequest(
    val consentDisclosure: String,
    val couponCode: String = ""
)
data class SaveConsentSubmit(
    val consentDisclosure: String,
)

data class CaptureCCPaymentRequest(
    val accountNumber: String,
    val acctType: String,
    val bankAccountType: String,
    val bankName: String,
    val billingAddress: String,
    val cardCode: String,
    val cardNumber: String,
    val city: String,
    val countryCode: String,
    val diffBillAdd: Boolean,
    val expDate: String,
    val firstName: String,
    val isCreditDetails: Boolean,
    val isDebitDetails: Boolean,
    val lastName: String,
    val nameOnAccount: String,
    val payAmt: Double,
    val refId: String,
    val routingNumber: String,
    val selectedPaymentMethod: String,
    val state: String,
    val zipCode: String
)


