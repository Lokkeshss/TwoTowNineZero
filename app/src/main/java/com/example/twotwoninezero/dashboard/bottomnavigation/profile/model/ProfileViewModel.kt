package com.example.twotwoninezero.dashboard.bottomnavigation.profile.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.repo.ProfileRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class ProfileViewModel: BaseViewModel() {
    private val repository : ProfileRepo = ProfileRepo(ApiFactory.ttApi)
    var mGetCountry : MutableLiveData<List<GetCountryItem>> = MutableLiveData()
    var mGetStateReponse : MutableLiveData<List<GetStateReponse>> = MutableLiveData()
    var mGetTransactionDetailsuserIDResponse : MutableLiveData<List<GetTransactionDetailsuserIDResponse>> = MutableLiveData()
    var mGetMyAccountDetailsResponse : MutableLiveData<GetUpdateMyAccountDetailsResponse> = MutableLiveData()
    var mChangePasswordResponse : MutableLiveData<ChangePasswordResponse> = MutableLiveData()
    var mChangeLoginEmailResponse : MutableLiveData<ChangeLoginEmailResponse> = MutableLiveData()
    var mGetTaxPreparerResponse : MutableLiveData<GetTaxPreparerResponse> = MutableLiveData()
    var mUploadTaxpayerResponse : MutableLiveData<UploadTaxpayerResponse> = MutableLiveData()

    fun getCountry() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getCountry()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetCountryItem>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetCountry.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun getCountryState(id: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getState(id)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetStateReponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetStateReponse.postValue(loginResp)
            }
        }

    }

    fun getMyAccountDetails() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getMyAccountDetails()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetUpdateMyAccountDetailsResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetMyAccountDetailsResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }
    fun updateUser(i: UpdateMyAccountDetailsRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateUser(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetUpdateMyAccountDetailsResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetMyAccountDetailsResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun changePassword(i: ChangePasswordRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.changePassword(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ChangePasswordResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mChangePasswordResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun changeLoginEmail(i: ChangeLoginEmailRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.changeLoginEmail(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ChangeLoginEmailResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mChangeLoginEmailResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }
    fun getTransactionDetailsuserID() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getTransactionDetailsuserID()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetTransactionDetailsuserIDResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetTransactionDetailsuserIDResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun getTaxPreparer() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getTaxPreparer()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetTaxPreparerResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetTaxPreparerResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun updateTaxpreparer(i:TaxPreparerRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateTaxpreparer(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as UploadTaxpayerResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUploadTaxpayerResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }




}