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

    /* Home */
}