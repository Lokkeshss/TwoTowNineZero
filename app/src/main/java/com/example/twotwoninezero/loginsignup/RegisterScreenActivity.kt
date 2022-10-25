package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.twotwoninezero.R
import com.example.twotwoninezero.dashboard.DashBoardActivity

class RegisterScreenActivity : AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RegisterScreenActivity::class.java)
        }
    }
    var loginHere:TextView?=null
    var register:Button?=null
    var howdoyouHereUsLL:LinearLayout?=null
    var imTaxPayer:CheckBox?=null
    var edt_hereus:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)
        register=findViewById(R.id.register)
        loginHere=findViewById(R.id.loginHere)
        howdoyouHereUsLL=findViewById(R.id.howdoyouHereUsLL)
        imTaxPayer=findViewById(R.id.imTaxPayer)
        edt_hereus=findViewById(R.id.edt_hereus)
        loginHere?.setOnClickListener {
            startActivity(LoginScreenActivity.getStartIntent(this))
        }
        register?.setOnClickListener {
            startActivity(DashBoardActivity.getStartIntent(this))
        }
        howdoyouHereUsLL?.setOnClickListener {
            Toast.makeText(applicationContext,"Click",Toast.LENGTH_SHORT).show()
        }
        edt_hereus?.setOnClickListener {
            howdoyouHereUsLL?.visibility= View.VISIBLE
        }
        imTaxPayer?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                Toast.makeText(applicationContext,"ischecked",Toast.LENGTH_SHORT).show()
                howdoyouHereUsLL?.visibility= View.GONE
            }else{
                Toast.makeText(applicationContext,"Not checked",Toast.LENGTH_SHORT).show()
                howdoyouHereUsLL?.visibility= View.GONE
            }
        }
    }

}