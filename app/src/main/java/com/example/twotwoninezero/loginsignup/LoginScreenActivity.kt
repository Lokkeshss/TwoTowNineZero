package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.SplashScreen.OnBoardingScreenActivity
import com.example.twotwoninezero.dashboard.DashBoardActivity

class LoginScreenActivity : AppCompatActivity() {
    lateinit var loginCardView:CardView
    var login:Button?=null
    var resetPassword:TextView?=null
    var registerHere:TextView?=null
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginScreenActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        loginCardView = findViewById(R.id.loginCardView)
        loginCardView.setBackgroundResource(R.drawable.loginsignup_cardview_cornor)
        login=findViewById(R.id.login)
        resetPassword=findViewById(R.id.resetPassword)
        registerHere=findViewById(R.id.registerHere)

        login?.setOnClickListener {
            startActivity(DashBoardActivity.getStartIntent(this))
        }
        resetPassword?.setOnClickListener {
            startActivity(PasswordResetActivity.getStartIntent(this))
        }
        registerHere?.setOnClickListener {
            startActivity(RegisterScreenActivity.getStartIntent(this))
        }
    }
}