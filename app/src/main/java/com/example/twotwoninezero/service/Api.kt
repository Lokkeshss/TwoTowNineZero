package com.example.twotwoninezero.service

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

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


    /*Filling*/

    @GET("filings/getForm")
    @Headers("Content-Type: application/json")
    fun getFormType(): Deferred<Response<List<GetFillingFormResponse>>>

    @GET("filings/getTaxYear/{formtype}")
    @Headers("Content-Type: application/json")
    fun gettaxyear(
        @Path("formtype") formtype: String,
    ): Deferred<Response<List<GetFillingTaxYearResponse>>>

    @GET("filings/getFirstUsedMonth/30/{formtype}")
    @Headers("Content-Type: application/json")
    fun getFirstUsedMonth(
        @Path("formtype") formtype: String,
    ): Deferred<Response<List<getFillingFirstUsedMonthResponse>>>

    @POST("filings/saveFiling")
    @Headers("Content-Type: application/json")
    fun saveFiling(@Body i: SaveUpdateFilingRequest?) :
            Deferred<Response<SaveUpdateFilingResponse>>

    @GET("form2290/getTaxableVehicleByFilingId/{filing}")
    @Headers("Content-Type: application/json")
    fun getTaxableVehicleByFilingId( @Path("filing") filing: String) :
            Deferred<Response<List<GetTaxableVehicleResponse>>>

    @POST("form2290/saveTaxableVehicle/{filing}")
    @Headers("Content-Type: application/json")
    fun saveTaxableVehicle(@Path("filing") filing: String,
                           @Body i: SaveTaxableVehicleRequest?) :
            Deferred<Response<SaveTaxableVehicleResponse>>

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
}