package com.example.twotwoninezero.dashboard.bottomnavigation.home.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.home.repo.HomeRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel:BaseViewModel() {

    private val repository : HomeRepo = HomeRepo(ApiFactory.ttApi)
    var mHomeScreenListResponseActive : MutableLiveData<List<HomeScreenListResponse>> = MutableLiveData()
    var mHomeScreenListResponseArchive : MutableLiveData<List<HomeScreenListResponse>> = MutableLiveData()
    var mDeleteHomeScreenFilingResponse : MutableLiveData<DeleteHomeScreenFilingResponse> = MutableLiveData()
    var mReactivateHomeScreenFilingResponse : MutableLiveData<ReactivateHomeScreenFilingResponse> = MutableLiveData()
    var mEditTaxableVehicleByIdResponse : MutableLiveData<EditTaxableVehicleByIdResponse> = MutableLiveData()
    var mFileDownloadResponse : MutableLiveData<FileDownloadResponse> = MutableLiveData()
    var mGetIRSRejectionDetailsResponse : MutableLiveData<GetIRSRejectionDetailsResponse> = MutableLiveData()

    fun getIRSRejectionDetails(i: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getIRSRejectionDetails(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetIRSRejectionDetailsResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetIRSRejectionDetailsResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun getFilingsByUserId(i: HomeScreenGetFilingsByUserIdRequest,requestType:String) {

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
                if (requestType.equals("archive")){
                    mHomeScreenListResponseArchive.postValue(loginResp)
                }else{
                    mHomeScreenListResponseActive.postValue(loginResp)
                }
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

    fun reactivateFiling(filing: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.reactivateFiling(filing)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ReactivateHomeScreenFilingResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mReactivateHomeScreenFilingResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun filterHomeScreenFiling(i: FilingFilterRequest,requestType:String) {

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

                if (requestType.equals("archive")){
                    mHomeScreenListResponseArchive.postValue(loginResp)
                }else{
                    mHomeScreenListResponseActive.postValue(loginResp)
                }

                // println("loginResploginResp "+loginResp.toString())
            }
        }
    }

    fun downloadPDF(filename: String, file: File) {

        mIsLoading.postValue(true)
        scope.launch {
            var fileResponse = repository.downloadPDF(filename, file)
            mIsLoading.postValue(false)
            if (fileResponse.error) {
                Log.d("API Error", fileResponse?.msg.toString())
                mFailureMessage.postValue(fileResponse.msg)
            } else {
                Log.d(" API SUCCESS ", fileResponse.toString())
                //mFileDownloadResponse.postValue(fileResponse)
                if (fileResponse.file != null) {
                    mFileDownloadResponse.postValue(fileResponse)
                } else {
                    // mFailureMessage.postValue("File download failed due to network connection, Please try again later")
                }
            }
        }
    }

}