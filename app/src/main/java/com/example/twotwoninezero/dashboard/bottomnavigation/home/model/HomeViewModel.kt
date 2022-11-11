package com.example.twotwoninezero.dashboard.bottomnavigation.home.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.repo.Fleetrepo
import com.example.twotwoninezero.dashboard.bottomnavigation.home.repo.HomeRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class HomeViewModel:BaseViewModel() {

    private val repository : HomeRepo = HomeRepo(ApiFactory.ttApi)
    var mHomeScreenListResponse : MutableLiveData<List<HomeScreenListResponse>> = MutableLiveData()
    var mDeleteHomeScreenFilingResponse : MutableLiveData<DeleteHomeScreenFilingResponse> = MutableLiveData()

    fun getFilingsByUserId(i: HomeScreenGetFilingsByUserIdRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getFilingsByUserId(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<HomeScreenListResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mHomeScreenListResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }
    fun deleteFiling(filing: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteFiling(filing)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteHomeScreenFilingResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteHomeScreenFilingResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun filterHomeScreenFiling(i: FilingFilterRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.filterHomeScreenFiling(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<HomeScreenListResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mHomeScreenListResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

}