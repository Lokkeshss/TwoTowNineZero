package com.example.twotwoninezero.common

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

fun Context.hasNetworkConnection(): Boolean {
    val cm:ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm.activeNetwork != null
    } else {
        (cm.activeNetworkInfo != null || cm.activeNetworkInfo!!.isConnected)
    }
}