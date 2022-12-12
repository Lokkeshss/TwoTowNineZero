package com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.repo.FillingRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch

class FIllingFormSummaryViewModel: BaseViewModel() {
    private val repository : FillingRepo = FillingRepo(ApiFactory.ttApi)

    var mGetSummaryDetailsByFilingIdResponse : MutableLiveData<GetSummaryDetailsByFilingIdResponse> = MutableLiveData()
    var mPaymentOptionMethodResponse : MutableLiveData<PaymentOptionMethodResponse> = MutableLiveData()
    var mSavePaymentOptionResponse : MutableLiveData<SavePaymentOptionResponse> = MutableLiveData()
    var mValidateXMLResponse : MutableLiveData<ValidateXMLResponse> = MutableLiveData()
    var mupdateRejErrorDesc : MutableLiveData<Int> = MutableLiveData()
    var mSubmissionFeeResponse : MutableLiveData<SubmissionFeeResponse> = MutableLiveData()
    var mUpdateConsentDisclosureResponse : MutableLiveData<UpdateConsentDisclosureResponse> = MutableLiveData()
    var mGetPaymentDetResponse : MutableLiveData<GetPaymentDetResponse> = MutableLiveData()
    var mCaptureCCPaymentResponse : MutableLiveData<CaptureCCPaymentResponse> = MutableLiveData()
    var mGetCountry : MutableLiveData<List<GetCountryItem>> = MutableLiveData()
    var mGetStateReponse : MutableLiveData<List<GetStateReponse>> = MutableLiveData()


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


    fun GetSummaryDetailsByFilingIdResponse(filingId:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.GetSummaryDetailsByFilingIdResponse(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetSummaryDetailsByFilingIdResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetSummaryDetailsByFilingIdResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun getPaymentOptionByFilingId(filingId:String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getPaymentOptionByFilingId(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as PaymentOptionMethodResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mPaymentOptionMethodResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun savePaymentOption(filingId:String,i: SavePaymentOptionRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.savePaymentOption(filingId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as SavePaymentOptionResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSavePaymentOptionResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun validateXML(filingId: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.validateXML(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as ValidateXMLResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mValidateXMLResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun updateRejErrorDesc(filingId: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateRejErrorDesc(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as Int
                Log.d(" API SUCCESS ", loginResp.toString())
                mupdateRejErrorDesc.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }


    fun submissionFee(filingId: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.submissionFee(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as SubmissionFeeResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSubmissionFeeResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }


    fun applyCoupon(filingId: String,i:ApplyCouponRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.applyCoupon(filingId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as SubmissionFeeResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mSubmissionFeeResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun updateConsentDisclosure(filingId: String,i:UpdateConsentDisclosureRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.updateConsentDisclosure(filingId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as UpdateConsentDisclosureResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mUpdateConsentDisclosureResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun getPaymentDet(filingId: String) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.getPaymentDet(filingId)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as GetPaymentDetResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mGetPaymentDetResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }

    fun captureCCPayment(filingId: String,i:CaptureCCPaymentRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.captureCCPayment(filingId,i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as CaptureCCPaymentResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mCaptureCCPaymentResponse.postValue(loginResp)
                // println("loginResploginResp "+loginResp.toString())
            }

        }

    }



}