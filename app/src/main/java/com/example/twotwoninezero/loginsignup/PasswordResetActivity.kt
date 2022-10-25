package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.twotwoninezero.R
import com.example.twotwoninezero.dashboard.DashBoardActivity

class PasswordResetActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PasswordResetActivity::class.java)
        }
    }
    var resetPassword:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        resetPassword=findViewById(R.id.resetPassword)
        resetPassword?.setOnClickListener {
            alertDialogBox()
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