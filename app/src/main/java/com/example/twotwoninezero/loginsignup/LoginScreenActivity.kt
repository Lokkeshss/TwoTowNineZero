package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseActivity
import com.example.twotwoninezero.dashboard.DashBoardActivity
import com.example.twotwoninezero.loginsignup.model.SignUpViewModel
import com.example.twotwoninezero.service.LoginRequest

class LoginScreenActivity : BaseActivity() {
    private lateinit var mSignUpViewModel: SignUpViewModel
    lateinit var loginCardView:CardView
    var login:Button?=null
    var resetPassword:TextView?=null
    var registerHere:TextView?=null
    var edt_email:EditText?=null
    var edt_password:EditText?=null
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginScreenActivity::class.java)
        }
    }

    override fun initViewModel() {
        mSignUpViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(SignUpViewModel::class.java)
        setViewModel(mSignUpViewModel)

        mSignUpViewModel.mLoginResponse.observe(this, Observer {
            if (it.code==200){
                showMessage(it.message)
                startActivityFunction()
            }else{
                showMessage(it.message)
            }
        })
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_login_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_login_screen)
        loginCardView = findViewById(R.id.loginCardView)
        loginCardView.setBackgroundResource(R.drawable.loginsignup_cardview_cornor)
        login=findViewById(R.id.login)
        edt_email=findViewById(R.id.edt_email)
        edt_password=findViewById(R.id.edt_password)
        resetPassword=findViewById(R.id.resetPassword)
        registerHere=findViewById(R.id.registerHere)

        login?.setOnClickListener {
            if (edt_email?.text.toString().isNullOrEmpty()){
                showMessage("Please enter the email id")
            }else if (edt_password?.text.toString().isNullOrEmpty()){
                showMessage("Please enter the password")
            }else {
                if (isOnline()) {
                    val i=LoginRequest(edt_email?.text.toString(),edt_password?.text.toString())
                    mSignUpViewModel.loginFunction(i)
                } else {
                    showToast(getString(R.string.internet_required))
                }

            }
        }
        resetPassword?.setOnClickListener {
            edt_password?.setText("")
            edt_email?.setText("")
            startActivity(PasswordResetActivity.getStartIntent(this))
        }
        registerHere?.setOnClickListener {
            edt_password?.setText("")
            edt_email?.setText("")
            startActivity(RegisterScreenActivity.getStartIntent(this))
        }
    }

    private fun startActivityFunction() {
        startActivity(DashBoardActivity.getStartIntent(this))
    }
}