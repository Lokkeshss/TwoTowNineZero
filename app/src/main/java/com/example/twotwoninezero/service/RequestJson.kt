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

class SaveBulkTaxableVehicleRequest : ArrayList<SaveBulkTaxableVehicleRequestItem>()

data class SaveBulkTaxableVehicleRequestItem(
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