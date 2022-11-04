package com.example.twotwoninezero.common

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

const val PREFS_FILENAME = "appstateencript"
const val JWT_TOKEN = "jwtToken"

class SharedPrefs private constructor()  {
    companion object {
        private var INSTANCE: SharedPrefs? = null
        private var encriptar:String?=null
        lateinit var prefs: SharedPreferences
        @RequiresApi(Build.VERSION_CODES.M)
        fun getInstance(context: Context): SharedPrefs {
            encriptar = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            return INSTANCE ?: SharedPrefs().also {
                INSTANCE = it
                //  prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
                prefs = EncryptedSharedPreferences.create(PREFS_FILENAME, encriptar!!,context.applicationContext,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
            }
        }
    }

    var jwtToken: String?
        get() = prefs.getString(JWT_TOKEN, "")
        set(value: String?) = prefs.edit().putString(JWT_TOKEN, value).apply()
}