package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseActivity
import com.example.twotwoninezero.dashboard.DashBoardActivity
import com.example.twotwoninezero.loginsignup.model.SignUpViewModel
import com.example.twotwoninezero.service.ForgotPasswordEmailRequest

class PasswordResetActivity : BaseActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PasswordResetActivity::class.java)
        }
    }
    private lateinit var mSignUpViewModel: SignUpViewModel
    var resetPassword:Button?=null
    lateinit var loginCardView: CardView
    lateinit var edt_email: EditText
    override fun initViewModel() {
        mSignUpViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(SignUpViewModel::class.java)
        setViewModel(mSignUpViewModel)

        mSignUpViewModel.mForgotPasswordResponse.observe(this, Observer {

            if (it.code==200){
                showMessage(it.message)
                alertDialogBox()
            }else{
                showMessage(it.message)
            }
        })
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_password_reset
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_password_reset)
        loginCardView = findViewById(R.id.loginCardView)
        edt_email = findViewById(R.id.edt_email)
        loginCardView.setBackgroundResource(R.drawable.loginsignup_cardview_cornor)
        resetPassword=findViewById(R.id.resetPassword)
        resetPassword?.setOnClickListener {

            if (edt_email.text.toString().isNullOrEmpty()){
                showMessage("Please Enter Register Email Id")
            }else{
                val i = ForgotPasswordEmailRequest(edt_email.text.toString(),"email")
                mSignUpViewModel.forgotPasswordFunction(i)

            }

        }
    }


    fun alertDialogBox(){
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.checkyour_mail_alert,null)
        val mailOk = view.findViewById<Button>(R.id.mailOk)
        builder.setView(view)
        mailOk.setOnClickListener {
            builder.dismiss()
            startActivity(LoginScreenActivity.getStartIntent(this))
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}