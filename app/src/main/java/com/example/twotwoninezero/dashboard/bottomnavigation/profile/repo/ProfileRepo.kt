package com.example.twotwoninezero.dashboard.bottomnavigation.profile.repo

import com.example.twotwoninezero.service.*

class ProfileRepo (private val api: Api) : BaseRepository(){

    suspend fun getCountry(): ApiResponse<List<GetCountryItem>>? {
        val resp = doApiCall { api.getCountry().await() }
        return resp
    }


    suspend fun getState(i:String): ApiResponse<List<GetStateReponse>>? {
        val resp = doApiCall { api.getState(i).await() }
        return resp
    }
    suspend fun getMyAccountDetails(): ApiResponse<GetUpdateMyAccountDetailsResponse>? {
        val resp = doApiCall { api.getMyAccountDetails().await() }
        return resp
    }

    suspend fun updateUser(i:UpdateMyAccountDetailsRequest): ApiResponse<GetUpdateMyAccountDetailsResponse>? {
        val resp = doApiCall { api.updateUser(i).await() }
        return resp
    }
    suspend fun changePassword(i:ChangePasswordRequest): ApiResponse<ChangePasswordResponse>? {
        val resp = doApiCall { api.changePassword(i).await() }
        return resp
    }
    suspend fun changeLoginEmail(i:ChangeLoginEmailRequest): ApiResponse<ChangeLoginEmailResponse>? {
        val resp = doApiCall { api.changeLoginEmail(i).await() }
        return resp
    }
    suspend fun getTransactionDetailsuserID(): ApiResponse<List<GetTransactionDetailsuserIDResponse>>? {
        val resp = doApiCall { api.getTransactionDetailsuserID().await() }
        return resp
    }
    suspend fun getTaxPreparer(): ApiResponse<GetTaxPreparerResponse>? {
        val resp = doApiCall { api.getTaxPreparer().await() }
        return resp
    }
    suspend fun updateTaxpreparer(i:TaxPreparerRequest): ApiResponse<UploadTaxpayerResponse>? {
        val resp = doApiCall { api.updateTaxpreparer(i).await() }
        return resp
    }
}