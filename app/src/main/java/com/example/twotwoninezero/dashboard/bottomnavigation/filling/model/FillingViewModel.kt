package com.example.twotwoninezero.dashboard.bottomnavigation.filling.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.repo.FillingRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class FillingViewModel:BaseViewModel() {

    private val repository : FillingRepo = FillingRepo(ApiFactory.ttApi)
    var mGetBusinessTypeRequestItem : MutableLiveData<List<GetBusinessTypeRequestItem>> = MutableLiveData()
    var mGetFillingFormResponse : MutableLiveData<List<GetFillingFormResponse>> = MutableLiveData()
    var mGetFillingTaxYearResponse: MutableLiveData<List<GetFillingTaxYearResponse>> = MutableLiveData()
    var mgetFillingFirstUsedMonthResponse: MutableLiveData<List<getFillingFirstUsedMonthResponse>> = MutableLiveData()
    var mGetTaxableVehicleResponse: MutableLiveData<List<GetTaxableVehicleResponse>> = MutableLiveData()
    var mSaveUpdateFilingResponse: MutableLiveData<SaveUpdateFilingResponse> = MutableLiveData()
    var mSaveTaxableVehicleResponse: MutableLiveData<SaveTaxableVehicleResponse> = MutableLiveData()
    var mTaxableWeightResponseList : MutableLiveData<List<TaxableWeightResponse>> = MutableLiveData()

    fun getBusinessTypeRequestItem() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getBusinessTypeRequestItem()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetBusinessTypeRequestItem>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetBusinessTypeRequestItem.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }


    }
    fun getFormType() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getFormType()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetFillingFormResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetFillingFormResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }
    fun gettaxyear(formtype: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.gettaxyear(formtype)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetFillingTaxYearResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetFillingTaxYearResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun getFirstUsedMonth(formtype: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getFirstUsedMonth(formtype)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<getFillingFirstUsedMonthResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mgetFillingFirstUsedMonthResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun saveFiling(i: SaveUpdateFilingRequest?) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveFiling(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as SaveUpdateFilingResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveUpdateFilingResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }


    fun getTaxableVehicleByFilingId(i: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getTaxableVehicleByFilingId(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetTaxableVehicleResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetTaxableVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun gettaxableweight() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.gettaxableweight()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<TaxableWeightResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mTaxableWeightResponseList.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun saveTaxableVehicle(filling:String,i: SaveTaxableVehicleRequest?) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveTaxableVehicle(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as SaveTaxableVehicleResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveTaxableVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


}