package com.example.twotwoninezero.base

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twotwoninezero.common.CustomProgressbar
import com.example.twotwoninezero.common.hasNetworkConnection
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initViewModel()
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        initViewModel()
    }
    fun showMessage(message: String) {
        Toast.makeText(this@BaseActivity,message, Toast.LENGTH_SHORT).show()
    }

    open fun showSnackBar(activity: Activity, message: String?) {
        val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_SHORT).show()
    }

    fun setViewModel(viewModel: BaseViewModel) {
        viewModel.mIsLoading.observe(this, Observer { aBoolean: Boolean ->
            if (aBoolean) {
                showProgressbar()
            } else {
                dismissProgressbar()
            }
        })
        viewModel.mFailureMessage.observe(
            this,
            Observer { msg: String ->
                dismissProgressbar()
                // this is the place where we are getting all error toast message
                // we manually changed api error response to meaning full message as "Check Your InterNet Connection"
                if (isOnline()){
                    showMessage(msg)
                }else{
                    showMessage("Check your Internet Connection")
                }

                println("BaseActivity mFailureMessage "+msg)
               // showMessage("Check Your InterNet Connection")
            }
        )
        viewModel.mSuccessMessage.observe(
            this,
            Observer { msg: String ->
                dismissProgressbar()
                showMessage(msg)
            }
        )
//        viewModel.mAlertMessages.observe(
//            this,
//            Observer { msg: String ->
//                dismissProgressbar()
//                Log.e("BaseActivity ","mAlertMessages "+msg)
//                //showAlertDialog(msg)
//            }
//        )
    }


    fun getStringFromId(id: Int): String {
        return applicationContext.resources.getString(id)
    }

    fun getIntegerFromId(id: Int): Int {
        return applicationContext.resources.getInteger(id)
    }


    fun showProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this, false).show()
        }
    }


    fun dismissProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this, false).dismiss()
        }
    }
    fun hasNetworkConnection(message: String): Boolean {
        val hasInternet = hasNetworkConnection()
        if (!hasInternet) {
            // no network
            showMessage(message)
        }
        return hasInternet
    }

    override fun onResume() {
        super.onResume()
        if (sAppMinimized) {
            sAppMinimized = false
        }
        sAppAlive = true
    }

    override fun onPause() {
        super.onPause()
        sAppAlive = false
    }

    override fun onStop() {
        super.onStop()
        if (!sAppAlive) {
            sAppMinimized = true
        }
    }

    fun getResourceString(id: Int): String {
        return resources.getString(id)
    }

    fun getResourceDimen(id: Int): Float {
        return resources.getDimension(id)
    }

    fun getResourceColor(id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    fun getResourceInteger(id: Int): Int {
        return applicationContext.resources.getInteger(id)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = this.supportFragmentManager.fragments
        if (fragments != null) {
            val var5 = fragments.iterator()

            while (var5.hasNext()) {
                val fragment = var5.next() as Fragment
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


    fun getVersionName(): String {
        try {
            return packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getPhoneModel() : String?{
        return Build.MODEL
    }

    fun getPhoneOs() : String?{
        return "Android "+Build.VERSION.RELEASE.toString()
    }

    fun getVersionCode(): Int {
        try {
            return packageManager.getPackageInfo(packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }


    fun showToast(msg: String?) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    companion object {

        var sAppAlive: Boolean = false
        var sAppMinimized = true

    }

}