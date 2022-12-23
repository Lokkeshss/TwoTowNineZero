package com.example.twotwoninezero.loginsignup.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.twotwoninezero.base.BaseViewModel
import com.example.twotwoninezero.loginsignup.repo.SignUpRepo
import com.example.twotwoninezero.service.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.String
import kotlin.toString

class SignUpViewModel:BaseViewModel() {
    var mLoginResponse : MutableLiveData<LoginResponse> = MutableLiveData()
    var mRegistrationRequest : MutableLiveData<RegisterResponse> = MutableLiveData()
    var mRegistrationRequestList : MutableLiveData<List<RegisterResponseList>>? = MutableLiveData()
    var mForgotPasswordResponse : MutableLiveData<ForgotPasswordResponse> = MutableLiveData()
    private val repository : SignUpRepo = SignUpRepo(ApiFactory.ttApi)

    fun loginFunction(i: LoginRequest) {

        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.doLogin(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val loginResp = apiResponse?.data as LoginResponse
                Log.d(" API SUCCESS ", loginResp.toString())
                mLoginResponse.postValue(loginResp)
                /*  val someresp = repository.getAppMeta()
                if (someresp?.error == true) {
                    mFailureMessage.postValue("Error in retreiving Application Defaults!")
                }*/
            }

        }


    }
    fun registerFunction(i: RegistrationRequest) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.doRegister(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val registerResp = apiResponse?.data as RegisterResponse
                Log.d(" API SUCCESS ", registerResp.toString())
                mRegistrationRequest.postValue(registerResp)
                 /* val someresp = repository.getAppMeta()
                if (someresp?.error == true) {
                    mFailureMessage.postValue("Error in retreiving Application Defaults!")
                }*/
            }
        }
    }

/*
    fun registerFunction(i: RegistrationRequest) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.doRegister(i)
            mIsLoading.postValue(false)

        println("apiResponse "+apiResponse?.data.toString())

        }
    }
*/

    fun forgotPasswordFunction(i: ForgotPasswordEmailRequest) {
        mIsLoading.postValue(true)
        scope.launch {
            val apiResponse = repository.doForgotPassword(i)
            mIsLoading.postValue(false)
            if (apiResponse?.error == true) {
                Log.d("API Error", apiResponse?.msg.toString())
                mFailureMessage.postValue(apiResponse?.msg)
            } else {
                val registerResp = apiResponse?.data as ForgotPasswordResponse
                Log.d(" API SUCCESS ", registerResp.toString())
                mForgotPasswordResponse.postValue(registerResp)
                /*  val someresp = repository.getAppMeta()
                if (someresp?.error == true) {
                    mFailureMessage.postValue("Error in retreiving Application Defaults!")
                }*/
            }
        }

    }
}