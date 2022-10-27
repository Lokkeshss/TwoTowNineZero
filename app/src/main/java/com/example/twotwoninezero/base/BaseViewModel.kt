package com.example.twotwoninezero.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

open class BaseViewModel() : ViewModel() {
    val mIsLoading : MutableLiveData<Boolean>  = MutableLiveData<Boolean>()
    var mFailureMessage : MutableLiveData<String> = MutableLiveData<String>()
    var mSuccessMessage : MutableLiveData<String> = MutableLiveData<String>()
    // co-routines related
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    val scope = CoroutineScope(coroutineContext)
    fun cancelAllRequests() = coroutineContext.cancel()

}