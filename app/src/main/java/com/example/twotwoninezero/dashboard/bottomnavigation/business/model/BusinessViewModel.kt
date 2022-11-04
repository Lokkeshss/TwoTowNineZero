package com.example.twotwoninezero.dashboard.bottomnavigation.business.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.business.repo.BusinessRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch
import java.io.File

class BusinessViewModel:BaseViewModel() {
    private val repository : BusinessRepo = BusinessRepo(ApiFactory.ttApi)
  //  var mAddnewBusinessResponse : MutableLiveData<AddnewBusinessResponse> = MutableLiveData()

    var mUpdateAndAddnewBusinessResponse : MutableLiveData<UpdateAndNewBusinessResponse> = MutableLiveData()
    var mReActivateBusinessResponse : MutableLiveData<ReActivateBusinessResponse> = MutableLiveData()
    var mEditBusinessListResponse : MutableLiveData<EditBusinessListResponse> = MutableLiveData()
    var mArchiveBusinessResponse: MutableLiveData<ArchiveBusinessResponse> = MutableLiveData()
    var mFileDownloadResponse : MutableLiveData<FileDownloadResponse> = MutableLiveData()


    var mGetCountry : MutableLiveData<List<GetCountryItem>> = MutableLiveData()
    var mGetStateReponse : MutableLiveData<List<GetStateReponse>> = MutableLiveData()
    var mAllBusinessListResponse : MutableLiveData<List<AllAndSearchBusinessListResponse>> = MutableLiveData()
    var mdeletedBusinessListResponse : MutableLiveData<List<AllAndSearchBusinessListResponse>> = MutableLiveData()
    var mGetBusinessTypeRequestItem : MutableLiveData<List<GetBusinessTypeRequestItem>> = MutableLiveData()
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

    fun addNewBusiness(i: CreateAndUpdateBusinessRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.createNewBusinessRequest(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as UpdateAndNewBusinessResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateAndAddnewBusinessResponse.postValue(loginResp)
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

    fun getallbusinesslist( listType: String?,limit: String?,offset: String?,type:String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getallbusinesslist(listType,limit,offset)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<AllAndSearchBusinessListResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                if (type.equals("active")){
                    mAllBusinessListResponse.postValue(loginResp)
                }else{
                    mdeletedBusinessListResponse.postValue(loginResp)
                }

            }
        }

    }

    fun searchBusiness( i:SearchBusinessRequest,type: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.searchBusiness(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<AllAndSearchBusinessListResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                if (type.equals("active")){
                    mAllBusinessListResponse.postValue(loginResp)
                }else{
                    mdeletedBusinessListResponse.postValue(loginResp)
                }

            }
        }

    }

    fun editBusinessList(businessId: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.editBusinessList(businessId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as EditBusinessListResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mEditBusinessListResponse.postValue(loginResp)
            }
        }

    }

    fun archiveBusinesss(businessId: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.archiveBusinesss(businessId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ArchiveBusinessResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mArchiveBusinessResponse.postValue(loginResp)
            }
        }

    }

    fun updateNewBusiness(businessId: String, i: CreateAndUpdateBusinessRequest) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateBusinessRequest(businessId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as UpdateAndNewBusinessResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateAndAddnewBusinessResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun reActiveBusinesss(businessId: String) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.reActiveBusinesss(businessId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ReActivateBusinessResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mReActivateBusinessResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun doFileDownload(filename: String, file: File) {

        mIsLoading.postValue(true)
        scope.launch {
            var fileResponse = repository.doDownloadFile(filename, file)
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