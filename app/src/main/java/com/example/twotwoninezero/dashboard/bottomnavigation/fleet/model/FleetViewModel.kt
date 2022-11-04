package com.example.twotwoninezero.dashboard.bottomnavigation.fleet.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.repo.Fleetrepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class FleetViewModel:BaseViewModel() {
    private val repository : Fleetrepo = Fleetrepo(ApiFactory.ttApi)

    var mGetBusinessNameList : MutableLiveData<List<GetBusinessNameResponse>> = MutableLiveData()
    var mTaxableWeightResponseList : MutableLiveData<List<TaxableWeightResponse>> = MutableLiveData()
    var mAddNewFleetResponse : MutableLiveData<AddNewFleetResponse> = MutableLiveData()
    var mGetFleetByIdResponse : MutableLiveData<GetFleetByIdResponse> = MutableLiveData()
    var mDeleteFleetResponse : MutableLiveData<DeleteFleetResponse> = MutableLiveData()
    var mGetFleetListResponse: MutableLiveData<List<GetFleetListResponse>> = MutableLiveData()

    fun getbusinessname() {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getbusinessname()
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetBusinessNameResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetBusinessNameList.postValue(loginResp)
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

    fun addNewFleetBusinessRequest(i: AddNewFleetBusinessRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.addNewFleetBusinessRequest(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as AddNewFleetResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mAddNewFleetResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun updateFleetlineItem(fleetId:String,i: AddNewFleetBusinessRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateFleetlineItem(fleetId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as AddNewFleetResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mAddNewFleetResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getFleetById(i: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getFleetById(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetFleetByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetFleetByIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getfleetlist(i: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getfleetlist(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetFleetListResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetFleetListResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun deleteFleetlineItem(fleetId: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteFleetlineItem(fleetId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteFleetResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteFleetResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
}