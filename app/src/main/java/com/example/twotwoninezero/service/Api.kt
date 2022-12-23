package com.example.twotwoninezero.service

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
/*https://dev1.simpletrucktax.com/trucktax/customerfeedback/saveFeedback/2120*/
interface Api {
    /*signuprepo*/
    @POST("users/createUser")
    @Headers("Content-Type: application/json", "No-Authentication: true")
    fun registerRequest(
        @Body i: RegistrationRequest?
    ): Deferred<Response<RegisterResponse>>

    @POST("login")
    @Headers("Content-Type: application/json", "No-Authentication: true")
    fun loginRequest(
        @Body i: LoginRequest?
    ): Deferred<Response<LoginResponse>>

    @POST("forgotPassword/validateUserDetails")
    @Headers("Content-Type: application/json", "No-Authentication: true")
    fun forgotPasswordRequest(
        @Body i: ForgotPasswordEmailRequest?
    ): Deferred<Response<ForgotPasswordResponse>>

    /*signuprepo*/


    /*businessrepo*/
    @GET("businesses/getCountry")
    @Headers("Content-Type: application/json")
    fun getCountry(): Deferred<Response<List<GetCountryItem>>>

    @GET("businesses/getBusinessType")
    @Headers("Content-Type: application/json")
    fun getBusinessTypeRequestItem(): Deferred<Response<List<GetBusinessTypeRequestItem>>>

    @GET("businesses/getState/{countryId}")
    @Headers("Content-Type: application/json")
    fun getState(@Path("countryId") countryid: String?): Deferred<Response<List<GetStateReponse>>>


    @GET("businesses/businessList/{listType}/{limit}/{offset}")
    @Headers("Content-Type: application/json")
    fun getallbusinesslist(@Path("listType") listType: String?,
                           @Path("limit") limit: String?,
                           @Path("offset") offset: String?): Deferred<Response<List<AllAndSearchBusinessListResponse>>>

    @GET("filings/getFilingById/{filingId}")
    @Headers("Content-Type: application/json")
    fun getFilingById(@Path("filingId") listType: String?): Deferred<Response<GetFilingByIdResponse>>

    @POST("businesses/searchBusiness")
    @Headers("Content-Type: application/json")
    fun searchBusiness(@Body i:SearchBusinessRequest): Deferred<Response<List<AllAndSearchBusinessListResponse>>>

    @POST("businesses/createBusiness")
    @Headers("Content-Type: application/json")
    fun createNewBusinessRequest(
        @Body i: CreateAndUpdateBusinessRequest?
    ): Deferred<Response<UpdateAndNewBusinessResponse>>

    @PUT("businesses/updateBusiness/{businessId}")
    @Headers("Content-Type: application/json")
    fun updateBusinessRequest(
        @Path("businessId") businessId: String,
        @Body i: CreateAndUpdateBusinessRequest?
    ): Deferred<Response<UpdateAndNewBusinessResponse>>

    @GET("businesses/businessById/{businessId}")
    @Headers("Content-Type: application/json")
    fun editBusinessList(@Path("businessId") businessId: String): Deferred<Response<EditBusinessListResponse>>

    @PUT("businesses/archiveBusiness/{businessId}")
    @Headers("Content-Type: application/json")
    fun archiveBusinesss(@Path("businessId") businessId: String): Deferred<Response<ArchiveBusinessResponse>>

    @PUT("businesses/reActivateBusiness/{businessId}")
    @Headers("Content-Type: application/json")
    fun reActiveBusinesss(@Path("businessId") businessId: String): Deferred<Response<ReActivateBusinessResponse>>

    @Streaming
    @GET("businesses/download/null/null/active/all")
    fun download_file(): Deferred<Response<ResponseBody>>

    /*businessrepo*/


    /*fleetrepo*/
    @GET("businesses/getBusinessName")
    @Headers("Content-Type: application/json")
    fun getbusinessname(): Deferred<Response<List<GetBusinessNameResponse>>>

    @GET("filings/getWeightList")
    @Headers("Content-Type: application/json")
    fun gettaxableweight(): Deferred<Response<List<TaxableWeightResponse>>>

    @GET("amendment1/getTGWIncreaseById/{id}/{filingId}")
    @Headers("Content-Type: application/json")
    fun getTGWIncreaseById(@Path("id") id: String,@Path("filingId") fleetId: String): Deferred<Response<GetTGWIncreaseByIdResponse>>

    @GET("amendment2/getExceededMileageById/{id}/{filingId}")
    @Headers("Content-Type: application/json")
    fun getExceededMileageById(@Path("id") id: String,@Path("filingId") fleetId: String): Deferred<Response<GetExceededMileageByIdResponse>>

    @GET("vinCorrection/getVinCorrectionById/{id}/{filingId}")
    @Headers("Content-Type: application/json")
    fun getVinCorrectionById(@Path("id") id: String,@Path("filingId") fleetId: String): Deferred<Response<GetVinCorrectionByIdResponse>>

    @POST("filings/saveFleets")
    @Headers("Content-Type: application/json")
    fun addNewFleetBusinessRequest(
        @Body i: AddNewFleetBusinessRequest?
    ): Deferred<Response<AddNewFleetResponse>>

    @GET("filings/fleetsById/{id}")
    @Headers("Content-Type: application/json")
    fun getFleetById(
        @Path("id") businessId: String
    ): Deferred<Response<GetFleetByIdResponse>>

    @GET("filings/fleetsByUserId/{id}")
    @Headers("Content-Type: application/json")
    fun getfleetlist(
        @Path("id") businessId: String
    ): Deferred<Response<List<GetFleetListResponse>>>

    @PUT("filings/updateFleets/{fleetId}")
    @Headers("Content-Type: application/json")
    fun updateFleetlineItem(
        @Path("fleetId") fleetId: String,
        @Body i: AddNewFleetBusinessRequest?
    ): Deferred<Response<AddNewFleetResponse>>

    @DELETE("filings/deleteFleets/{filingId}")
    @Headers("Content-Type: application/json")
    fun deleteFleetlineItem(
        @Path("filingId") fleetId: String,
    ): Deferred<Response<DeleteFleetResponse>>

    /*fleetrepo*/

    /*Form submiting summary*/

    @GET("formSummary/getSummaryDetailsByFilingId/{filingId}")
    @Headers("Content-Type: application/json")
    fun GetSummaryDetailsByFilingIdResponse(@Path("filingId") formtype: String):
            Deferred<Response<GetSummaryDetailsByFilingIdResponse>>

    @GET("irsPaymentOptions/getPaymentOptionByFilingId/{filingId}")
    @Headers("Content-Type: application/json")
    fun getPaymentOptionByFilingId(@Path("filingId") formtype: String):
            Deferred<Response<PaymentOptionMethodResponse>>

    @POST("irsPaymentOptions/savePaymentOption/{filingId}")
    @Headers("Content-Type: application/json")
    fun savePaymentOption(@Path("filingId") formtype: String,@Body i:SavePaymentOptionRequest):
            Deferred<Response<SavePaymentOptionResponse>>

    /*Form submiting summary*/


    /*Filling*/

    @GET("filings/getForm")
    @Headers("Content-Type: application/json")
    fun getFormType(): Deferred<Response<List<GetFillingFormResponse>>>

    @GET("filings/getTaxYear/{formtype}")
    @Headers("Content-Type: application/json")
    fun gettaxyear(
        @Path("formtype") formtype: String,
    ): Deferred<Response<List<GetFillingTaxYearResponse>>>

    @GET("filings/getFirstUsedMonth/{taxYear}/{formtype}")
    @Headers("Content-Type: application/json")
    fun getFirstUsedMonth(
        @Path("taxYear") taxYear: String,
        @Path("formtype") formtype: String,
    ): Deferred<Response<List<getFillingFirstUsedMonthResponse>>>

    @POST("filings/saveFiling")
    @Headers("Content-Type: application/json")
    fun saveFiling(@Body i: SaveUpdateFilingRequest?) :
            Deferred<Response<SaveUpdateFilingResponse>>
    @POST("filings/updateFiling/{filing}")
    @Headers("Content-Type: application/json")
    fun updateFiling(@Path("filing") filing: String,@Body i: SaveUpdateFilingRequest?) :
            Deferred<Response<SaveUpdateFilingResponse>>

    @GET("form2290/getTaxableVehicleByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getTaxableVehicleByFilingId( @Path("filing") filing: String) :
            Deferred<Response<List<GetTaxableVehicleResponse>>>

    @GET("filings/loadFleetList/{filing}")
    @Headers("Content-Type: application/json")
    fun loadFleetList( @Path("filing") filing: String) :
            Deferred<Response<List<LoadFleetListResponse>>>

    @GET("amendment1/getTGWIncreaseByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getTGWIncreaseByFilingId( @Path("filing") filing: String) :
            Deferred<Response<List<GetTGWIncreaseByFilingIdResponse>>>

    @GET("amendment2/getExceededMileageByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getExceededMileageByFilingId( @Path("filing") filing: String) :
            Deferred<Response<List<GetExceededMileageByFilingIdResponse>>>

    @GET("vinCorrection/getVinCorrectionByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getVinCorrectionByFilingId( @Path("filing") filing: String) :
            Deferred<Response<List<GetVinCorrectionByFilingIdResponse>>>

    @POST("form2290/saveTaxableVehicle/{filing}")
    @Headers("Content-Type: application/json")
    fun saveTaxableVehicle(@Path("filing") filing: String,
                           @Body i: SaveTaxableVehicleRequest?) :
            Deferred<Response<SaveTaxableVehicleResponse>>

    @POST("form2290/saveBulkTaxableVehicle/{filing}")
    @Headers("Content-Type: application/json")
    fun saveBulkTaxableVehicle(@Path("filing") filing: String,
                           @Body i: List<SaveBulkTaxableVehicleRequest>?) :
            Deferred<Response<saveBulkTaxableVehicleResponse>>
    @POST("form2290/saveBulkCurrentSuspended/{filing}")
    @Headers("Content-Type: application/json")
    fun saveBulkCurrentSuspended(@Path("filing") filing: String,
                           @Body i: List<SaveBulkCurrentSuspendedRequest>?) :
            Deferred<Response<saveBulkReportingSuspendedResponse>>

    @POST("amendment1/saveTGWIncrease/{filing}")
    @Headers("Content-Type: application/json")
    fun saveTGWIncrease(@Path("filing") filing: String,
                           @Body i: SaveUpdateTGWIncreaseRequest?) :
            Deferred<Response<SaveUpdateTGWIncreaseResponse>>

    @PUT("amendment1/updateTGWIncrease/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateTGWIncrease(@Path("id") id: String,@Path("filing") filing: String,
                           @Body i: SaveUpdateTGWIncreaseRequest?) :
            Deferred<Response<SaveUpdateTGWIncreaseResponse>>

    @POST("amendment2/saveExceededMileage/{filing}")
    @Headers("Content-Type: application/json")
    fun saveExceededMileage(@Path("filing") filing: String,
                            @Body i: SaveUpdateExceededMileageRequest?) :
            Deferred<Response<SaveUpdateExceededMileageResponse>>

    @POST("vinCorrection/saveVinCorrection/{filing}")
    @Headers("Content-Type: application/json")
    fun saveVinCorrection(@Path("filing") filing: String,
                            @Body i: SaveUpdateVinCorrectionRequest?) :
            Deferred<Response<SaveUpdateVinCorrectionResponse>>

    @PUT("amendment2/updateExceededMileage/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateExceededMileage(@Path("id") id: String,@Path("filing") filing: String,
                            @Body i: SaveUpdateExceededMileageRequest?) :
            Deferred<Response<SaveUpdateExceededMileageResponse>>

    @PUT("vinCorrection/updateVinCorrection/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateVinCorrection(@Path("id") id: String,@Path("filing") filing: String,
                            @Body i: SaveUpdateVinCorrectionRequest?) :
            Deferred<Response<SaveUpdateVinCorrectionResponse>>

    @PUT("form2290/updateTaxableVehicle/{businessId}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateTaxableVehicle(@Path("businessId") businessId: String,
                             @Path("filing") filing: String,
                           @Body i: SaveTaxableVehicleRequest?) :
            Deferred<Response<SaveTaxableVehicleResponse>>

    @DELETE("form2290/deleteTaxableVehicleById/{businessId}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteTaxableVehicleById(@Path("businessId") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteTaxableVehicleResponse>>

    @DELETE("amendment1/deleteTGWIncreaseById/{businessId}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteTGWIncreaseById(@Path("businessId") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteTGWIncreaseByIdResponse>>

    @DELETE("amendment2/deleteExceededMileageById/{businessId}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteExceededMileageById(@Path("businessId") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteExceededMileageByIdResponse>>

    @DELETE("vinCorrection/deleteVinCorrectionById/{businessId}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteVinCorrectionById(@Path("businessId") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteVinCorrectionByIdResponse>>


    @DELETE("form2290/deleteCurrentSuspendedeById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteCurrentSuspendedeById(@Path("id") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteCurrentSuspendedeById>>

    @DELETE("form2290/deleteSoldDestroyedById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteSoldDestroyedById(@Path("id") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteSoldDestroyedResponse>>

    @DELETE("form2290/deleteCreditOverPaymentById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteCreditOverPaymentById(@Path("id") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteCreditOverPaymentById>>

    @DELETE("form2290/deleteLowMileageById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteLowMileageById(@Path("id") businessId: String,
                             @Path("filing") filing: String) :
            Deferred<Response<DeleteLowMileageByIdResponse>>

    @GET("form2290/getCurrentSuspendedByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun GetCurrentSuspendedByFilingId(@Path("filing") filing: String):
            Deferred<Response<List<GetCurrentSuspendedByFilingIdResponse>>>

    @POST("form2290/saveCurrentSuspended/{filing}")
    @Headers("Content-Type: application/json")
    fun saveCurrentSuspended(@Path("filing") filing: String,@Body i:SaveCurrentSuspendRequest):
            Deferred<Response<SaveCurrentSuspendResponse>>

    @PUT("form2290/updateCurrentSuspended/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateCurrentSuspended(@Path("id") id: String,@Path("filing") filing: String,@Body i:UpdateCurrentSuspendRequest):
            Deferred<Response<UpdateCurrentSuspendResponse>>

    @PUT("form2290/updatePriorSuspended/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updatePriorSuspended(@Path("id") id: String,@Path("filing") filing: String,@Body i:UpdatePriorSuspendedRequest):
            Deferred<Response<UpdatePriorSuspendedResponse>>

    @GET("form2290/getCurrentSuspendedById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun editGetCurrentSuspendedById(@Path("id") id: String,@Path("filing") filing: String):
            Deferred<Response<EditGetCurrentSuspendedByIdResponse>>

    @GET("form2290/getSoldDestroyedById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun editgetSoldDestroyedById(@Path("id") id: String,@Path("filing") filing: String):
            Deferred<Response<EditgetSoldDestroyedByIdResponse>>

    @GET("form2290/getCreditOverPaymentById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun getCreditOverPaymentById(@Path("id") id: String,@Path("filing") filing: String):
            Deferred<Response<GetCreditOverPaymentByIdResponse>>

    @GET("form2290/getLowMileageById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun getLowMileageById(@Path("id") id: String,@Path("filing") filing: String):
            Deferred<Response<GetLowMileageByIdResponse>>

    @GET("form2290/getPriorSuspendedByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getPriorSuspendedByFilingId(@Path("filing") filing: String):
            Deferred<Response<List<GetPriorSuspendedByFilingIdResponse>>>

    @GET("form2290/getPriorSoldDate/{filing}")
    @Headers("Content-Type: application/json")
    fun getPriorSoldDate(@Path("filing") filing: String):
            Deferred<Response<GetPriorSoldDateResponse>>

    @GET("form2290/getSoldAndDestoryDate/{filing}")
    @Headers("Content-Type: application/json")
    fun getSoldAndDestoryDate(@Path("filing") filing: String):
            Deferred<Response<GetSoldAndDestoryDateResponse>>

    @GET("form2290/getLowMileageDate/{filing}")
    @Headers("Content-Type: application/json")
    fun getLowMileageDate(@Path("filing") filing: String):
            Deferred<Response<GetLowMileageDateResponse>>

    @GET("form2290/getCreditOverPaymentDate/{filing}")
    @Headers("Content-Type: application/json")
    fun getCreditOverPaymentDate(@Path("filing") filing: String):
            Deferred<Response<GetCreditOverPaymentDateResponse>>

    @POST("form2290/savePriorSuspended/{filing}")
    @Headers("Content-Type: application/json")
    fun savePriorSuspended(@Path("filing") filing: String,@Body i : SavePriorSuspendedRequest):
            Deferred<Response<SavePriorSuspendedResponse>>

    @DELETE("form2290/deletePriorSuspendedById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun deletePriorSuspendedById(@Path("id") businessId: String,@Path("filing") filing: String):
            Deferred<Response<DeletePriorSuspendedResponse>>

    @GET("form2290/getPriorSuspendedById/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun getPriorSuspendedById(@Path("id") businessId: String,@Path("filing") filing: String):
            Deferred<Response<GetPriorSuspendedByIdResponse>>





    @POST("form2290/saveCreditOverPayment/{filing}")
    @Headers("Content-Type: application/json")
    fun saveCreditOverPayment(@Path("filing") filing: String,@Body i:SaveCreditOverPaymentRequest):
            Deferred<Response<SaveCreditOverPaymentResponse>>

    @PUT("form2290/updateCreditOverPayment/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateCreditOverPayment(@Path("id") id: String,@Path("filing") filing: String,@Body i:UpdateCreditOverPaymentRequest):
            Deferred<Response<UpdateCreditOverPaymentResponse>>

    @POST("form2290/saveSoldDestroyedVehicle/{filing}")
    @Headers("Content-Type: application/json")
    fun saveSoldDestroyedVehicle(@Path("filing") filing: String,@Body i:SaveSoldDestroyedVehicleRequest):
            Deferred<Response<SaveSoldDestroyedVehicleResponse>>

    @PUT("form2290/updateSoldDestroyedVehicle/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateSoldDestroyedVehicle(@Path("id") id: String,@Path("filing") filing: String,@Body i:UpdateSoldDestroyedVehicleRequest):
            Deferred<Response<updateSoldDestroyedVehicle>>

    @GET("form2290/getSoldDestroyedByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getSoldDestroyedByFilingId(@Path("filing") filing: String):
            Deferred<Response<List<GetSoldDestroyedByFilingIdResponse>>>

    @GET("form2290/getCreditOverPaymentByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getCreditOverPaymentByFilingId(@Path("filing") filing: String):
            Deferred<Response<List<GetCreditOverPaymentByFilingIdResponse>>>


    @GET("form2290/getLowMileageByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getLowMileageByFilingId(@Path("filing") filing: String):
            Deferred<Response<List<GetLowMileageByFilingIdResponse>>>


    @POST("form2290/saveLowMileageVehicle/{filing}")
    @Headers("Content-Type: application/json")
    fun saveLowMileageVehicle(@Path("filing") filing: String,@Body i:SaveLowMileageVehicleRequest):
            Deferred<Response<SaveLowMileageVehicleResponse>>

    @PUT("form2290/updateLowMileageVehicle/{id}/{filing}")
    @Headers("Content-Type: application/json")
    fun updateLowMileageVehicle(@Path("id") id: String,@Path("filing") filing: String,@Body i:UpdateLowMileageVehicleRequest):
            Deferred<Response<UpdateLowMileageVehicleResponse>>



    /*Filling*/


    /* Home */

    @GET("formSummary/getIRSRejectionDetails/{filing}")
    @Headers("Content-Type: application/json")
    fun getIRSRejectionDetails(@Path("filing") filing: String):Deferred<Response<GetIRSRejectionDetailsResponse>>

    @POST("filings/getFilingsByUserId")
    @Headers("Content-Type: application/json")
    fun getFilingsByUserId(@Body i:HomeScreenGetFilingsByUserIdRequest):Deferred<Response<List<HomeScreenListResponse>>>

    @DELETE("filings/deleteFiling/{filing}")
    @Headers("Content-Type: application/json")
    fun deleteFiling(@Path("filing") filing: String):Deferred<Response<DeleteHomeScreenFilingResponse>>

    @POST("filings/searchFiling")
    @Headers("Content-Type: application/json")
    fun filterHomeScreenFiling(@Body i: FilingFilterRequest):Deferred<Response<List<HomeScreenListResponse>>>

    @PUT("filings/reactivateFiling/{filing}")
    @Headers("Content-Type: application/json")
    fun reactivateFiling(@Path("filing") filing: String):Deferred<Response<ReactivateHomeScreenFilingResponse>>

    @GET("form2290/getTaxableVehicleById/{businessId}/{filingId}")
    @Headers("Content-Type: application/json")
    fun getTaxableVehicleById(@Path("businessId") businessId: String,@Path("filingId") filingId: String):Deferred<Response<EditTaxableVehicleByIdResponse>>

    /* Home */


    /*Profile*/

    @GET("users/getMyAccountDetails")
    @Headers("Content-Type: application/json")
    fun getMyAccountDetails(): Deferred<Response<GetUpdateMyAccountDetailsResponse>>

    @PUT("users/updateUser")
    @Headers("Content-Type: application/json")
    fun updateUser(@Body i:UpdateMyAccountDetailsRequest): Deferred<Response<GetUpdateMyAccountDetailsResponse>>

    @POST("users/changePassword")
    @Headers("Content-Type: application/json")
    fun changePassword(@Body i:ChangePasswordRequest): Deferred<Response<ChangePasswordResponse>>

    @PUT("users/changeLoginEmail")
    @Headers("Content-Type: application/json")
    fun changeLoginEmail(@Body i:ChangeLoginEmailRequest): Deferred<Response<ChangeLoginEmailResponse>>

    @GET("getTransactionDetailsuserID")
    @Headers("Content-Type: application/json")
    fun getTransactionDetailsuserID(): Deferred<Response<List<GetTransactionDetailsuserIDResponse>>>

    @GET("taxpreparer/getTaxPreparer")
    @Headers("Content-Type: application/json")
    fun getTaxPreparer(): Deferred<Response<GetTaxPreparerResponse>>

    @PUT("taxpreparer/updateTaxpreparer")
    @Headers("Content-Type: application/json")
    fun updateTaxpreparer(@Body i:TaxPreparerRequest): Deferred<Response<UploadTaxpayerResponse>>


    /* payment and irs submission*/

    @GET("formSummary/validateXML/{filingId}")
    @Headers("Content-Type: application/json")
    fun validateXML(@Path("filingId") filingId: String): Deferred<Response<ValidateXMLResponse>>



    @POST("formSummary/updateRejErrorDesc/{filingId}")
    @Headers("Content-Type: application/json")
    fun updateRejErrorDesc(@Path("filingId") filingId: String): Deferred<Response<Int>>



    @GET("formSummary/submissionFee/{filingId}")
    @Headers("Content-Type: application/json")
    fun submissionFee(@Path("filingId") filingId: String): Deferred<Response<SubmissionFeeResponse>>

    @POST("applyCoupon/{filingId}")
    @Headers("Content-Type: application/json")
    fun applyCoupon(@Path("filingId") filingId: String,@Body i:ApplyCouponRequest): Deferred<Response<SubmissionFeeResponse>>

    @POST("formSummary/updateConsentDisclosure/{filingId}")
    @Headers("Content-Type: application/json")
    fun updateConsentDisclosure(@Path("filingId") filingId: String,@Body i:UpdateConsentDisclosureRequest): Deferred<Response<UpdateConsentDisclosureResponse>>

    /* payment and irs submission*/


    /* Submission Fee Payment Option */
    @GET("getPaymentDet/{filingId}")
    @Headers("Content-Type: application/json")
    fun getPaymentDet(@Path("filingId") filingId: String): Deferred<Response<GetPaymentDetResponse>>

    @POST("captureCCPayment/{filingId}")
    @Headers("Content-Type: application/json")
    fun captureCCPayment(@Path("filingId") filingId: String, @Body i:CaptureCCPaymentRequest): Deferred<Response<CaptureCCPaymentResponse>>


    /* Submission Fee Payment Option */

    /*Downlaad files*/

    @Streaming
    @GET("downloadPDF/{filingId}")
    @Headers("Content-Type: application/json")
    fun downloadPDF(@Path("filingId") filingId: String,): Deferred<Response<ResponseBody>>



    @POST("login")
    @Headers("Content-Type: application/json", "No-Authentication: true")
    fun loginRequests(@Body i: LoginRequest?): Call<ResponseBody?>?


}