package com.example.twotwoninezero.base

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twotwoninezero.R
import com.example.twotwoninezero.common.CustomProgressbar
import com.example.twotwoninezero.common.showSuccessToast


abstract class BaseFragment: Fragment() {
    abstract fun initViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    fun setViewModel(viewModel: BaseViewModel) {

        viewModel.mIsLoading
            .observe(this, Observer { aBoolean: Boolean ->
                if (aBoolean) {
                    showProgressbar()
                } else {
                    dismissProgressbar()
                }
            })

        viewModel.mFailureMessage.observe(this, Observer {
            // this is the place where we are getting all error toast message
            // we manually changed api error response to meaning full message as "Check Your InterNet Connection"
            if (isOnline()){
                showSuccessToast(it)
            }else{
                showSuccessToast("Check Your InterNet Connection")
            }

            println("BaseFragment mFailureMessage "+it)
           // showSuccessToast("Check Your InterNet Connection")
        })
    }

    fun commonCallAndMailFunction() {
        val dialogView = layoutInflater.inflate(R.layout.common_callemail_dialog, null)

       val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog?.setCancelable(false)
        val callone = dialogView.findViewById<ImageView>(R.id.callone)
        val calltwo = dialogView.findViewById<ImageView>(R.id.calltwo)
        val sendmail = dialogView.findViewById<ImageView>(R.id.sendmail)
        val ok = dialogView.findViewById<TextView>(R.id.ok)


        callone.setOnClickListener {
            customDialog?.dismiss()
            callFunction("8680003843")
        }

        calltwo.setOnClickListener {
            customDialog?.dismiss()
            callFunction("8680003843")
        }
        sendmail.setOnClickListener {
            customDialog?.dismiss()
            //callFunction("")
        }
        ok.setOnClickListener {
            customDialog?.dismiss()
        }

    }

    fun callFunction(phone_number:String){
        val phone_intent = Intent(Intent.ACTION_CALL)
        phone_intent.data = Uri.parse("tel:$phone_number")
        startActivity(phone_intent)
    }

    private fun showProgressbar() {
        CustomProgressbar.getProgressbar(context as Context, false).show()
    }

    private fun dismissProgressbar() {
        CustomProgressbar.getProgressbar(context as Context, false).dismiss()
    }

    fun getStringFromId(id: Int): String {
        return (activity as Activity).resources.getString(id)
    }

    fun getIntegerFromId(id: Int): Int {
        return (activity as Activity).resources.getInteger(id)
    }


    fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun isOnline(): Boolean {
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

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


}