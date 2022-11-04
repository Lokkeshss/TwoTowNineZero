package com.example.twotwoninezero

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.twotwoninezero.common.SharedPrefs

class ThisApplication : MultiDexApplication() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        application = this
        publicPrefs = SharedPrefs.getInstance(applicationContext)
        // for sustom crashlytics
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object{
        lateinit var publicPrefs: SharedPrefs
        lateinit var application: ThisApplication
            private set

    }

}