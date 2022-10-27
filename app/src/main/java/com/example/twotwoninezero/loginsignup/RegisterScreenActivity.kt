package com.example.twotwoninezero.loginsignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseActivity
import com.example.twotwoninezero.common.SearchAdapter
import com.example.twotwoninezero.dashboard.DashBoardActivity
import com.example.twotwoninezero.loginsignup.model.SignUpViewModel
import com.example.twotwoninezero.service.RegistrationRequest
import com.google.gson.Gson

class RegisterScreenActivity : BaseActivity() {

    val hereusList= listOf<String>("Google Search","Facebook","Email","Mail Flyer","Youtube","Text Reminder","Whatsapp","Other")
    private lateinit var mSignUpViewModel: SignUpViewModel
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RegisterScreenActivity::class.java)
        }
    }
    lateinit var loginCardView: CardView
    var loginHere:TextView?=null
    var register:Button?=null
    var howdoyouHereUsLL:LinearLayout?=null
    var imTaxPayer:CheckBox?=null
    var termsandcondition:CheckBox?=null
    var edt_fname:EditText?=null
    var edt_email:EditText?=null
    var edt_cfemail:EditText?=null
    var edt_password:EditText?=null
    var edt_hereus:EditText?=null
    var isTaxPayer:String?=null
    var hereus:String?=null
    var mSearchAdapter:SearchAdapter?=null
    var searchtextRV:RecyclerView?=null

    override fun initViewModel() {
        mSignUpViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(SignUpViewModel::class.java)
        setViewModel(mSignUpViewModel)

        mSignUpViewModel.mRegistrationRequest.observe(this, Observer {
            if (it.code==200){
                showMessage(it.message)
                alertDialogBox()
            }else{
                showMessage(it.message)
            }

        })

    }

    override fun getLayoutResourceId(): Int {
       return R.layout.activity_register_screen
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register_screen)
        searchtextRV = findViewById(R.id.searchtextRV)
        loginCardView = findViewById(R.id.loginCardView)
        loginCardView.setBackgroundResource(R.drawable.loginsignup_cardview_cornor)
        register=findViewById(R.id.register)
        loginHere=findViewById(R.id.loginHere)
        howdoyouHereUsLL=findViewById(R.id.howdoyouHereUsLL)

        imTaxPayer=findViewById(R.id.imTaxPayer)
        termsandcondition=findViewById(R.id.termsandcondition)
        edt_hereus=findViewById(R.id.edt_hereus)
        edt_fname=findViewById(R.id.edt_fname)
        edt_email=findViewById(R.id.edt_email)
        edt_cfemail=findViewById(R.id.edt_cfemail)
        edt_password=findViewById(R.id.edt_password)
        howdoyouHereUsLL?.visibility= View.GONE
        edt_hereus?.setFocusable(false)
        edt_hereus?.setClickable(true)

        mSearchAdapter=SearchAdapter(hereusList){data->
            edt_hereus?.setText(data)
            hereus=data
            howdoyouHereUsLL?.visibility= View.GONE
        }

        val mLayoutManager = LinearLayoutManager(this)
        searchtextRV?.layoutManager = mLayoutManager
        searchtextRV?.adapter = mSearchAdapter

        loginHere?.setOnClickListener {
            startActivity(LoginScreenActivity.getStartIntent(this))
        }
        register?.setOnClickListener {

            if (edt_fname?.text.toString().isNullOrEmpty()){
                showMessage("Enter full name")
            }else if (edt_email?.text.toString().isNullOrEmpty()){
                showMessage("Enter your email")
            }else if (edt_cfemail?.text.toString().isNullOrEmpty()){
                showMessage("ReEnter your email")
            }else if (!edt_email?.text.toString().equals(edt_cfemail?.text.toString())){
                showMessage("Email id mismatch")
            }else if (edt_password?.text.toString().isNullOrEmpty()){
                showMessage("Enter the password")
            }else if (edt_hereus?.text.toString().isNullOrEmpty()){
                showMessage("Select How did you hear us?")
            }else{
                registerFunction()
            }


        }
        howdoyouHereUsLL?.setOnClickListener {
            Toast.makeText(applicationContext,"Click",Toast.LENGTH_SHORT).show()
        }
        edt_hereus?.setOnClickListener {
            howdoyouHereUsLL?.visibility= View.VISIBLE
        }
        imTaxPayer?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isTaxPayer="Y"
                Toast.makeText(applicationContext,"ischecked",Toast.LENGTH_SHORT).show()
              //  howdoyouHereUsLL?.visibility= View.GONE
            }else{
                isTaxPayer="N"
                Toast.makeText(applicationContext,"Not checked",Toast.LENGTH_SHORT).show()
             //   howdoyouHereUsLL?.visibility= View.GONE
            }
        }
    }

    private fun registerFunction() {
        val i = RegistrationRequest(
            edt_email?.text.toString(), edt_fname?.text.toString(),
            edt_password?.text.toString(),isTaxPayer.toString(), "2","google"
        )
        val gson = Gson()
        val jsonbranch: String = gson.toJson(i)

        println("jsonbranch  "+jsonbranch)

        mSignUpViewModel.registerFunction(i)
    }


    fun alertDialogBox(){
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.checkyour_mail_alert,null)
        val mailOk = view.findViewById<Button>(R.id.mailOk)
        val alertImage = view.findViewById<ImageView>(R.id.alertImage)
        val alertMainText = view.findViewById<TextView>(R.id.alertMainText)
        val alertdesc = view.findViewById<TextView>(R.id.alertdesc)
        builder.setView(view)
        alertImage.setImageResource(R.drawable.successimage)
        alertMainText.text="Success!"

        alertdesc.text="You have been successfully registered"
        mailOk.setOnClickListener {
            builder.dismiss()
            startActivity(DashBoardActivity.getStartIntent(this))
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }
}