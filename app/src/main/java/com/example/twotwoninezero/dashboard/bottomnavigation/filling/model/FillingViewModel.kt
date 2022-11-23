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
    var mDeleteSoldDestroyedResponse: MutableLiveData<DeleteSoldDestroyedResponse> = MutableLiveData()
    var mDeleteCreditOverPaymentById: MutableLiveData<DeleteCreditOverPaymentById> = MutableLiveData()
    var mDeleteLowMileageByIdResponse: MutableLiveData<DeleteLowMileageByIdResponse> = MutableLiveData()
    var mSaveCurrentSuspendResponse: MutableLiveData<SaveCurrentSuspendResponse> = MutableLiveData()
    var mUpdateCurrentSuspendResponse: MutableLiveData<UpdateCurrentSuspendResponse> = MutableLiveData()
    var mUpdatePriorSuspendedResponse: MutableLiveData<UpdatePriorSuspendedResponse> = MutableLiveData()
    var mEditGetCurrentSuspendedByIdResponse: MutableLiveData<EditGetCurrentSuspendedByIdResponse> = MutableLiveData()
    var mEditgetSoldDestroyedByIdResponse: MutableLiveData<EditgetSoldDestroyedByIdResponse> = MutableLiveData()
    var mGetCreditOverPaymentByIdResponse: MutableLiveData<GetCreditOverPaymentByIdResponse> = MutableLiveData()
    var mGetLowMileageByIdResponse: MutableLiveData<GetLowMileageByIdResponse> = MutableLiveData()
    var mGetPriorSuspendedByIdResponse: MutableLiveData<GetPriorSuspendedByIdResponse> = MutableLiveData()
    var mSaveSoldDestroyedVehicleResponse: MutableLiveData<SaveSoldDestroyedVehicleResponse> = MutableLiveData()
    var mSaveCreditOverPaymentResponse: MutableLiveData<SaveCreditOverPaymentResponse> = MutableLiveData()
    var mUpdateCreditOverPaymentResponse: MutableLiveData<UpdateCreditOverPaymentResponse> = MutableLiveData()
    var mSaveLowMileageVehicleResponse: MutableLiveData<SaveLowMileageVehicleResponse> = MutableLiveData()
    var mUpdateLowMileageVehicleResponse: MutableLiveData<UpdateLowMileageVehicleResponse> = MutableLiveData()
    var mupdateSoldDestroyedVehicle: MutableLiveData<updateSoldDestroyedVehicle> = MutableLiveData()
    var mGetPriorSuspendedByFilingIdResponse: MutableLiveData<List<GetPriorSuspendedByFilingIdResponse>> = MutableLiveData()
    var mGetSoldDestroyedByFilingIdResponse: MutableLiveData<List<GetSoldDestroyedByFilingIdResponse>> = MutableLiveData()
    var mGetCreditOverPaymentByFilingIdResponse: MutableLiveData<List<GetCreditOverPaymentByFilingIdResponse>> = MutableLiveData()
    var mGetLowMileageByFilingIdResponse: MutableLiveData<List<GetLowMileageByFilingIdResponse>> = MutableLiveData()
    var mDeletePriorSuspendedResponse: MutableLiveData<DeletePriorSuspendedResponse> = MutableLiveData()
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

    fun deleteSoldDestroyedById(businessId:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteSoldDestroyedById(businessId,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteSoldDestroyedResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteSoldDestroyedResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun deleteCreditOverPaymentById(businessId:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteCreditOverPaymentById(businessId,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteCreditOverPaymentById
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteCreditOverPaymentById.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun deleteLowMileageById(businessId:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deleteLowMileageById(businessId,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as DeleteLowMileageByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeleteLowMileageByIdResponse.postValue(loginResp)
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

    fun editgetSoldDestroyedById(id: String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.editgetSoldDestroyedById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as EditgetSoldDestroyedByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mEditgetSoldDestroyedByIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getCreditOverPaymentById(id: String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getCreditOverPaymentById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as GetCreditOverPaymentByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetCreditOverPaymentByIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getLowMileageById(id: String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getLowMileageById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as GetLowMileageByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetLowMileageByIdResponse.postValue(loginResp)
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

    fun deletePriorSuspendedById(id:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.deletePriorSuspendedById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as DeletePriorSuspendedResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mDeletePriorSuspendedResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getPriorSuspendedById(id:String,filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getPriorSuspendedById(id,filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as GetPriorSuspendedByIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetPriorSuspendedByIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun saveSoldDestroyedVehicle(filling:String,i:SaveSoldDestroyedVehicleRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveSoldDestroyedVehicle(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as SaveSoldDestroyedVehicleResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveSoldDestroyedVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun saveCreditOverPayment(filling:String,i:SaveCreditOverPaymentRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveCreditOverPayment(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as SaveCreditOverPaymentResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveCreditOverPaymentResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun updateCreditOverPayment(id:String,filling:String,i:UpdateCreditOverPaymentRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateCreditOverPayment(id,filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as UpdateCreditOverPaymentResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateCreditOverPaymentResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun saveLowMileageVehicle(filling:String,i:SaveLowMileageVehicleRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.saveLowMileageVehicle(filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as SaveLowMileageVehicleResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSaveLowMileageVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }
    fun updateLowMileageVehicle(id:String,filling:String,i:UpdateLowMileageVehicleRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateLowMileageVehicle(id,filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as UpdateLowMileageVehicleResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateLowMileageVehicleResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun updateSoldDestroyedVehicle(id:String,filling:String,i:UpdateSoldDestroyedVehicleRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateSoldDestroyedVehicle(id,filling,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as updateSoldDestroyedVehicle
                Log.d(" API SUCCESS ", loginResp.toString())
                mupdateSoldDestroyedVehicle.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun getSoldDestroyedByFilingId(filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getSoldDestroyedByFilingId(filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as List<GetSoldDestroyedByFilingIdResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetSoldDestroyedByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }

    fun getCreditOverPaymentByFilingId(filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getCreditOverPaymentByFilingId(filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as List<GetCreditOverPaymentByFilingIdResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetCreditOverPaymentByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }


    fun getLowMileageByFilingId(filling:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getLowMileageByFilingId(filling)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            }else {
                val loginResp = apiResponse?.data as List<GetLowMileageByFilingIdResponse>
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetLowMileageByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }
        }

    }









}