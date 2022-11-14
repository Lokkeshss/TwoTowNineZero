package com.example.twotwoninezero.dashboard.bottomnavigation.filling.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.repo.FillingRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class FillingViewModel:BaseViewModel() {

    private val repository : FillingRepo = FillingRepo(ApiFactory.ttApi)
    var mGetBusinessTypeRequestItem : MutableLiveData<List<AllAndSearchBusinessListResponse>> = MutableLiveData()
    var mGetFillingFormResponse : MutableLiveData<List<GetFillingFormResponse>> = MutableLiveData()
    var mGetFillingTaxYearResponse: MutableLiveData<List<GetFillingTaxYearResponse>> = MutableLiveData()
    var mgetFillingFirstUsedMonthResponse: MutableLiveData<List<getFillingFirstUsedMonthResponse>> = MutableLiveData()
    var mGetTaxableVehicleResponse: MutableLiveData<List<GetTaxableVehicleResponse>> = MutableLiveData()
    var mSaveUpdateFilingResponse: MutableLiveData<SaveUpdateFilingResponse> = MutableLiveData()
    var mEditTaxableVehicleByIdResponse: MutableLiveData<EditTaxableVehicleByIdResponse> = MutableLiveData()
    var mSaveTaxableVehicleResponse: MutableLiveData<SaveTaxableVehicleResponse> = MutableLiveData()
    var mDeleteTaxableVehicleResponse: MutableLiveData<DeleteTaxableVehicleResponse> = MutableLiveData()
    var mDeleteCurrentSuspendedeById: MutableLiveData<DeleteCurrentSuspendedeById> = MutableLiveData()
    var mSaveCurrentSuspendResponse: MutableLiveData<SaveCurrentSuspendResponse> = MutableLiveData()
    var mUpdateCurrentSuspendResponse: MutableLiveData<UpdateCurrentSuspendResponse> = MutableLiveData()
    var mUpdatePriorSuspendedResponse: MutableLiveData<UpdatePriorSuspendedResponse> = MutableLiveData()
    var mEditGetCurrentSuspendedByIdResponse: MutableLiveData<EditGetCurrentSuspendedByIdResponse> = MutableLiveData()
    var mGetPriorSuspendedByFilingIdResponse: MutableLiveData<List<GetPriorSuspendedByFilingIdResponse>> = MutableLiveData()
    var mSavePriorSuspendedResponse: MutableLiveData<SavePriorSuspendedResponse> = MutableLiveData()
    var mGetCurrentSuspendedByFilingIdResponse: MutableLiveData<List<GetCurrentSuspendedByFilingIdResponse>> = MutableLiveData()
    var mTaxableWeightResponseList : MutableLiveData<List<TaxableWeightResponse>> = MutableLiveData()

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
                mGetBusinessTypeRequestItem.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }


    }

    fun getTaxableVehicleById(businessId: String,filing: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getTaxableVehicleById(businessId,filing)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as EditTaxableVehicleByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mEditTaxableVehicleByIdResponse.postValue(loginResp)
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
    fun updateTaxableVehicle(businessId:String,filling:String,i: SaveTaxableVehicleRequest?) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateTaxableVehicle(businessId,filling,i)
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

    fun deleteTaxableVehicleById(businessId:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteTaxableVehicleById(businessId,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteTaxableVehicleResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteTaxableVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun deleteCurrentSuspendedeById(businessId:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteCurrentSuspendedeById(businessId,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteCurrentSuspendedeById
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteCurrentSuspendedeById.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun GetCurrentSuspendedByFilingId(filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.GetCurrentSuspendedByFilingId(filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as List<GetCurrentSuspendedByFilingIdResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetCurrentSuspendedByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun saveCurrentSuspended(filling:String,i:SaveCurrentSuspendRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveCurrentSuspended(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as SaveCurrentSuspendResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveCurrentSuspendResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun updateCurrentSuspended(id: String,filling:String,i:UpdateCurrentSuspendRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateCurrentSuspended(id,filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as UpdateCurrentSuspendResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateCurrentSuspendResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun updatePriorSuspended(id: String,filling:String,i:UpdatePriorSuspendedRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updatePriorSuspended(id,filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as UpdatePriorSuspendedResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdatePriorSuspendedResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun editGetCurrentSuspendedById(id: String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.editGetCurrentSuspendedById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as EditGetCurrentSuspendedByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mEditGetCurrentSuspendedByIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getPriorSuspendedByFilingId(filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getPriorSuspendedByFilingId(filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as List<GetPriorSuspendedByFilingIdResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetPriorSuspendedByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun savePriorSuspended(filling:String,i : SavePriorSuspendedRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.savePriorSuspended(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as SavePriorSuspendedResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSavePriorSuspendedResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }






}