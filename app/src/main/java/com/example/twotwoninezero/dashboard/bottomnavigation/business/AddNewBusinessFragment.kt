package com.example.twotwoninezero.dashboard.bottomnavigation.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.BusinessTypeListSpinnerAdapter
import com.example.twotwoninezero.common.CountryListSpinnerAdapter
import com.example.twotwoninezero.common.SearchAdapter
import com.example.twotwoninezero.common.StateListSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.business.model.BusinessViewModel
import com.example.twotwoninezero.service.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_business.*

class AddNewBusinessFragment : BaseFragment() {

    var ADD="ADD"
    var UPDATE="UPDATE"
    var BusinessId:String?=null

    var editBusinessListResponse: EditBusinessListResponse?=null
    val businessType= listOf<String>("Individual","Estates","Partnership","Corporation","Other Organizations","Limited Liability Company(LLC)")
    val signinTitle= listOf<String>("Owner","President","Manager","Employee","Others")

    private lateinit var mBusinessViewModel : BusinessViewModel
    var mCountryListSpinnerAdapter: CountryListSpinnerAdapter?=null
    var mStateListSpinnerAdapter: StateListSpinnerAdapter?=null
    var mBusinessTypeListSpinnerAdapter: BusinessTypeListSpinnerAdapter?=null
    var mSearchAdapter: SearchAdapter?=null

    /*   var commonContactCallMain:ImageView?=null
     var notification:ImageView?=null

    var businessProfileMain:MaterialCardView?=null
    var signingAuthInfoMain:MaterialCardView?=null
    var contactInfoMain:MaterialCardView?=null
    var thirdPartydesignMain:MaterialCardView?=null
    var businessProfileChild:LinearLayout?=null
   var signingAuthInfoChild:LinearLayout?=null
   var contactInfoChild:LinearLayout?=null
   var thirdPartydesignChild:LinearLayout?=null
   var thirdPartydesign_childtwo:LinearLayout?=null
   var test:TextView?=null
     var addNewBusinessCancel:TextView?=null
     var addNewBusinessSubmit:TextView?=null

    var businessProfileBusinessName:EditText?=null
     var businessProfileEin:EditText?=null
     var businessProfileBusinessType:EditText?=null
     var signingAuthInfoName:EditText?=null
     var signingAuthInfoPhone:EditText?=null
     var signingAuthInfoTitle:EditText?=null
     var contactInfoAddress:EditText?=null
     var contactInfoCounty:EditText?=null
     var contactInfostate:EditText?=null
     var contactInfocity:EditText?=null
     var contactInfoZipCode:EditText?=null
     var contactInfoPhoneNumber:EditText?=null
     var contactInfoEmailAddress:EditText?=null
     var ThirdPartyDesignName:EditText?=null
     var ThirdPartyDesignPhonenumber:EditText?=null
     var thirdPartydesign_checkbox:CheckBox?=null*/

    var businessProfileBusinessTypeId:Int?=null
    var contactInfostateId:String?=null
    var thirdPartydesign_checkboxString:String?=null
    var contactInfoCountyID:String?=null
    var checkboxBoolean:Boolean?=false

    var mgetCountryAdapterList:List<GetCountryItem> = ArrayList()
    var mGetBusinessTypeRequestItemAdapterList:List<GetBusinessTypeRequestItem> = ArrayList()
    var mGetStateReponse:List<GetStateReponse> = ArrayList()


    override fun initViewModel() {
        mBusinessViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(BusinessViewModel::class.java)
        setViewModel(mBusinessViewModel)

        mBusinessViewModel.mGetCountry.observe(this, Observer {
            mgetCountryAdapterList=it
        })
        mBusinessViewModel.mGetBusinessTypeRequestItem.observe(this, Observer {
            mGetBusinessTypeRequestItemAdapterList=it
        })
        mBusinessViewModel.mGetStateReponse.observe(this, Observer {
            mGetStateReponse=it

        })
        mBusinessViewModel.mEditBusinessListResponse.observe(this, Observer {
            editBusinessListResponse=it
            editBusinessResponse(editBusinessListResponse!!)
        })

        mBusinessViewModel.mUpdateAndAddnewBusinessResponse.observe(this, Observer {
                if (it.code==200){
                    showToast(it.message)
                    requireActivity().onBackPressed()
                }else{
                    showToast(it.message)

                }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_new_business, container, false)

        /*test=view.findViewById(R.id.test)

        commonContactCallMain = view.findViewById(R.id.commonContactCallMain)
        notification = view.findViewById(R.id.notification)

        businessProfileMain=view.findViewById(R.id.businessProfileMain)
        businessProfileChild=view.findViewById(R.id.businessProfileChild)
        businessProfileBusinessName=view.findViewById(R.id.businessProfileBusinessName)
        businessProfileEin=view.findViewById(R.id.businessProfileEin)
        businessProfileBusinessType=view.findViewById(R.id.businessProfileBusinessType)

        signingAuthInfoMain=view.findViewById(R.id.signingAuthInfoMain)
        signingAuthInfoChild=view.findViewById(R.id.signingAuthInfoChild)
        signingAuthInfoName=view.findViewById(R.id.signingAuthInfoName)
        signingAuthInfoPhone=view.findViewById(R.id.signingAuthInfoPhone)
        signingAuthInfoTitle=view.findViewById(R.id.signingAuthInfoTitle)

        contactInfoMain=view.findViewById(R.id.contactInfoMain)
        contactInfoChild=view.findViewById(R.id.contactInfoChild)
        contactInfoAddress=view.findViewById(R.id.contactInfoAddress)
        contactInfoCounty=view.findViewById(R.id.contactInfoCounty)
        contactInfostate=view.findViewById(R.id.contactInfostate)
        contactInfocity=view.findViewById(R.id.contactInfocity)
        contactInfoZipCode=view.findViewById(R.id.contactInfoZipCode)
        contactInfoPhoneNumber=view.findViewById(R.id.contactInfoPhoneNumber)
        contactInfoEmailAddress=view.findViewById(R.id.contactInfoEmailAddress)

        thirdPartydesignMain=view.findViewById(R.id.thirdPartydesignMain)
        thirdPartydesignChild=view.findViewById(R.id.thirdPartydesignChild)
        thirdPartydesign_checkbox=view.findViewById(R.id.thirdPartydesign_checkbox)
        thirdPartydesign_childtwo=view.findViewById(R.id.thirdPartydesign_childtwo)
        ThirdPartyDesignName=view.findViewById(R.id.ThirdPartyDesignName)
        ThirdPartyDesignPhonenumber=view.findViewById(R.id.ThirdPartyDesignPhonenumber)

        addNewBusinessCancel=view.findViewById(R.id.addNewBusinessCancel)
        addNewBusinessSubmit=view.findViewById(R.id.addNewBusinessSubmit)*/

/*

        arguments?.let {
            val id = it.getString("businessId")
            if (id != null && id.isNotEmpty()) {
                println("mEditBusinessListResponse "+id)
                mBusinessViewModel.editBusinessList(id)
            }
        }

        businessProfileBusinessType?.setFocusable(false)
        businessProfileBusinessType?.setClickable(true)
        contactInfoCounty?.setFocusable(false)
        contactInfoCounty?.setClickable(true)
        contactInfostate?.setFocusable(false)
        contactInfostate?.setClickable(true)
        signingAuthInfoTitle?.setFocusable(false)
        signingAuthInfoTitle?.setClickable(true)

        mBusinessViewModel.getCountry()
        mBusinessViewModel.getBusinessTypeRequestItem()

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        businessProfileBusinessType?.setOnClickListener {
            // call to open dialog
            showBusinessType()
        }

        signingAuthInfoTitle?.setOnClickListener {
            signinAuthTitle()
        }
        contactInfoCounty?.setOnClickListener {
            if (mgetCountryAdapterList.isNullOrEmpty()){
                mBusinessViewModel.getCountry()
            }else{
                contactInfoCountry()
            }

        }
        contactInfostate?.setOnClickListener {
            if (mGetStateReponse.isNullOrEmpty()){
                mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
            }else{
                contactState()
            }
        }



       // businessProfileBusinessType?.setText("Individual")

        test?.setOnClickListener {

        }

        businessProfileChild?.setVisibility(View.VISIBLE)
        businessProfileMain?.setOnClickListener {
            if (businessProfileChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    businessProfileChild!!,
                    AutoTransition()
                )
                businessProfileChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    businessProfileChild!!,
                    AutoTransition()
                )
                businessProfileChild?.setVisibility(View.GONE)
            }
        }

        signingAuthInfoMain?.setOnClickListener {

            if (signingAuthInfoChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    signingAuthInfoChild!!,
                    AutoTransition()
                )
                signingAuthInfoChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    signingAuthInfoChild!!,
                    AutoTransition()
                )
                signingAuthInfoChild?.setVisibility(View.GONE)
            }
        }
        contactInfoMain?.setOnClickListener {

            if (contactInfoChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    contactInfoChild!!,
                    AutoTransition()
                )
                contactInfoChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    contactInfoChild!!,
                    AutoTransition()
                )
                contactInfoChild?.setVisibility(View.GONE)
            }
        }

        thirdPartydesignMain?.setOnClickListener {
            if (thirdPartydesignChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    thirdPartydesignChild!!,
                    AutoTransition()
                )
                thirdPartydesignChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    thirdPartydesignChild!!,
                    AutoTransition()
                )
                thirdPartydesignChild?.setVisibility(View.GONE)
            }
        }

        thirdPartydesign_checkbox?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                checkboxBoolean=true
                    TransitionManager.beginDelayedTransition(
                        thirdPartydesign_childtwo!!,
                        AutoTransition()
                    )
                thirdPartydesign_childtwo?.setVisibility(View.VISIBLE)

            }else{
                checkboxBoolean=false
                TransitionManager.beginDelayedTransition(
                    thirdPartydesign_childtwo!!,
                    AutoTransition()
                )
                thirdPartydesign_childtwo?.setVisibility(View.GONE)
            }
        }


        addNewBusinessCancel?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        addNewBusinessSubmit?.setOnClickListener {
            //createNewBusiness(UPDATE,response.id.toString())

            if (addNewBusinessSubmit?.text.toString().equals("Submit")){
                createNewBusiness(ADD,"")
            }else{
                createNewBusiness(UPDATE, BusinessId.toString())
            }


        }
*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id = it.getString("businessId")
            if (id != null && id.isNotEmpty()) {
                println("mEditBusinessListResponse "+id)
                mBusinessViewModel.editBusinessList(id)
            }
        }

        businessProfileBusinessType?.setFocusable(false)
        businessProfileBusinessType?.setClickable(true)
        contactInfoCounty?.setFocusable(false)
        contactInfoCounty?.setClickable(true)
        contactInfostate?.setFocusable(false)
        contactInfostate?.setClickable(true)
        signingAuthInfoTitle?.setFocusable(false)
        signingAuthInfoTitle?.setClickable(true)

        mBusinessViewModel.getCountry()
        mBusinessViewModel.getBusinessTypeRequestItem()

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        businessProfileBusinessType?.setOnClickListener {
            // call to open dialog
            showBusinessType()
        }

        signingAuthInfoTitle?.setOnClickListener {
            signinAuthTitle()
        }
        contactInfoCounty?.setOnClickListener {
            if (mgetCountryAdapterList.isNullOrEmpty()){
                mBusinessViewModel.getCountry()
            }else{
                contactInfoCountry()
            }

        }
        contactInfostate?.setOnClickListener {
            if (mGetStateReponse.isNullOrEmpty()){
                mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
            }else{
                contactState()
            }
        }



        // businessProfileBusinessType?.setText("Individual")

        test?.setOnClickListener {

        }

        businessProfileChild?.setVisibility(View.VISIBLE)
        taxableVehicleMain?.setOnClickListener {
            if (businessProfileChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    businessProfileChild!!,
                    AutoTransition()
                )
                businessProfileChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    businessProfileChild!!,
                    AutoTransition()
                )
                businessProfileChild?.setVisibility(View.GONE)
            }
        }

        signingAuthInfoMain?.setOnClickListener {

            if (signingAuthInfoChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    signingAuthInfoChild!!,
                    AutoTransition()
                )
                signingAuthInfoChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    signingAuthInfoChild!!,
                    AutoTransition()
                )
                signingAuthInfoChild?.setVisibility(View.GONE)
            }
        }
        contactInfoMain?.setOnClickListener {

            if (contactInfoChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    contactInfoChild!!,
                    AutoTransition()
                )
                contactInfoChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    contactInfoChild!!,
                    AutoTransition()
                )
                contactInfoChild?.setVisibility(View.GONE)
            }
        }

        thirdPartydesignMain?.setOnClickListener {
            if (thirdPartydesignChild?.visibility== View.GONE){
                TransitionManager.beginDelayedTransition(
                    thirdPartydesignChild!!,
                    AutoTransition()
                )
                thirdPartydesignChild?.setVisibility(View.VISIBLE)
            }else{
                TransitionManager.beginDelayedTransition(
                    thirdPartydesignChild!!,
                    AutoTransition()
                )
                thirdPartydesignChild?.setVisibility(View.GONE)
            }
        }

        thirdPartydesign_checkbox?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                checkboxBoolean=true
                TransitionManager.beginDelayedTransition(
                    thirdPartydesign_childtwo!!,
                    AutoTransition()
                )
                thirdPartydesign_childtwo?.setVisibility(View.VISIBLE)

            }else{
                checkboxBoolean=false
                TransitionManager.beginDelayedTransition(
                    thirdPartydesign_childtwo!!,
                    AutoTransition()
                )
                thirdPartydesign_childtwo?.setVisibility(View.GONE)
            }
        }


        addNewBusinessCancel?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        addNewBusinessSubmit?.setOnClickListener {
            //createNewBusiness(UPDATE,response.id.toString())

            if (addNewBusinessSubmit?.text.toString().equals("Submit")){
                createNewBusiness(ADD,"")
            }else{
                createNewBusiness(UPDATE, BusinessId.toString())
            }


        }


    }

    private fun showBusinessType(){

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Business Type"

        mBusinessTypeListSpinnerAdapter=BusinessTypeListSpinnerAdapter(mGetBusinessTypeRequestItemAdapterList){ businesstype, id->
            businessProfileBusinessType?.setText(businesstype)
            businessProfileBusinessTypeId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mBusinessTypeListSpinnerAdapter

        cancel.setOnClickListener {
            businessProfileBusinessType?.setText("")
            businessProfileBusinessTypeId=0
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun signinAuthTitle(){
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Title"
        mSearchAdapter=SearchAdapter(signinTitle){ title->

            signingAuthInfoTitle?.setText(title)
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mSearchAdapter

        cancel.setOnClickListener {
            contactInfoCounty?.setText("")
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun contactState(){
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select State"
        mStateListSpinnerAdapter= StateListSpinnerAdapter(mGetStateReponse){ country, id->
            contactInfostate?.setText(country)
            contactInfostateId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mStateListSpinnerAdapter

        cancel.setOnClickListener {
            contactInfostate?.setText("")
            contactInfostateId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }


    private fun contactInfoCountry() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Country"

        mCountryListSpinnerAdapter=CountryListSpinnerAdapter(mgetCountryAdapterList){ country, id->
            contactInfoCounty?.setText(country)
            contactInfoCountyID=id
            contactInfostate?.setText("")
            contactInfostateId=""
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mCountryListSpinnerAdapter

        cancel.setOnClickListener {
            contactInfoCounty?.setText("")
            contactInfoCountyID=""
            contactInfostate?.setText("")
            contactInfostateId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
            customDialog.dismiss()
        }
    }


    private fun editBusinessResponse(response: EditBusinessListResponse) {
        addNewBusinessSubmit?.setText("Update")
        contactInfoAddress?.setText(response.address1)
        businessProfileBusinessName?.setText(response.businessName)
        contactInfocity?.setText(response.city)
        signingAuthInfoPhone?.setText(response.signingAuthorityPhone)
        businessProfileEin?.setText(response.ein)
        contactInfostate?.setText(response.stateName)
        contactInfoCounty?.setText(response.countryName)
        thirdPartydesign_checkbox?.isChecked=response.thirdpartyStatus
        businessProfileBusinessType?.setText(response.bizType)
        contactInfoEmailAddress?.setText(response.email)
        contactInfoPhoneNumber?.setText(response.phone)
        signingAuthInfoName?.setText(response.signingAuthorityName)
        signingAuthInfoTitle?.setText(response.signingAuthorityTitle)
        ThirdPartyDesignName?.setText(response.thirdPartyDesigneeName)
        ThirdPartyDesignPhonenumber?.setText(response.thirdPartyDesigneePhone)
        contactInfoZipCode?.setText(response.zipCode)

        contactInfoCountyID=response.countryId
        contactInfostateId=response.stateId
        businessProfileBusinessTypeId=response.businessType
        checkboxBoolean=response.thirdpartyStatus

        BusinessId=response.id.toString()

        mBusinessViewModel.getCountryState(contactInfoCountyID.toString())

       /* businessProfileBusinessTypeId!!,
        contactInfoCountyID.toString(),
        contactInfostateId.toString(),
        checkboxBoolean!!,
        "89768",
        "11111"*/

    }

    private fun createNewBusiness(type: String, businessId: String) {
        if (businessProfileBusinessName?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Business Name")
        }else if (businessProfileEin?.text.toString().isNullOrEmpty()){
            showToast("Please Enter EIN Number")
        }else if (businessProfileBusinessType?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Business Type")
        }else if (signingAuthInfoName?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Signing Authority Name")
        }else if (signingAuthInfoPhone?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Signing Authority Phone Number")
        }else if (signingAuthInfoTitle?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Signing Authority Title")
        }else if (contactInfoAddress?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Contact Address")
        }else if (contactInfoCounty?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Country")
        }else if (contactInfostate?.text.toString().isNullOrEmpty()){
            showToast("Please Enter State")
        }else if (contactInfocity?.text.toString().isNullOrEmpty()){
            showToast("Please Enter City")
        }else if (contactInfoZipCode?.text.toString().isNullOrEmpty()){
            showToast("Please Enter ZipCode")
        }else if (contactInfoZipCode?.text.toString().length<5){
            showToast("Please Enter Valid ZipCode")
        }else if (contactInfoPhoneNumber?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Contact Phone Number")
        }else if (contactInfoEmailAddress?.text.toString().isNullOrEmpty()){
            showToast("Please Enter Contact Email Id")
        }else if (checkboxBoolean==true){
            if (ThirdPartyDesignName?.text.toString().isNullOrEmpty()){
                showToast("Please Enter Thirdparty DesignName")
            }else if (ThirdPartyDesignPhonenumber?.text.toString().isNullOrEmpty()){
                showToast("Please Enter Thridparty DesignPhone")
            }else{
                val i = CreateAndUpdateBusinessRequest(
                    contactInfoAddress?.text.toString(),businessProfileBusinessName?.text.toString(),
                    businessProfileBusinessTypeId!!,contactInfocity?.text.toString(),contactInfoCountyID.toString(),
                    signingAuthInfoPhone?.text.toString(),businessProfileEin?.text.toString(),
                    contactInfoEmailAddress?.text.toString(),
                    contactInfoPhoneNumber?.text.toString(),signingAuthInfoName?.text.toString(),
                    signingAuthInfoTitle?.text.toString(),contactInfostateId.toString(),ThirdPartyDesignName?.text.toString(),
                    ThirdPartyDesignPhonenumber?.text.toString(), checkboxBoolean!!,
                    contactInfoZipCode?.text.toString(),"89768","11111"
                )

                val gson = Gson()
                val jsonbranch: String = gson.toJson(i)

                println("jsonbranch business  "+jsonbranch)
                if (type.equals("ADD")){
                    mBusinessViewModel.addNewBusiness(i)
                }else{
                    mBusinessViewModel.updateNewBusiness(businessId,i)
                }

            }
        }else{
            val i = CreateAndUpdateBusinessRequest(
                contactInfoAddress?.text.toString(),businessProfileBusinessName?.text.toString(),
                businessProfileBusinessTypeId!!,contactInfocity?.text.toString(), contactInfoCountyID.toString(),
                signingAuthInfoPhone?.text.toString(),businessProfileEin?.text.toString(),
                contactInfoEmailAddress?.text.toString(),
                contactInfoPhoneNumber?.text.toString(),signingAuthInfoName?.text.toString(),
                signingAuthInfoTitle?.text.toString(),
                contactInfostateId.toString(),ThirdPartyDesignName?.text.toString(),
                ThirdPartyDesignPhonenumber?.text.toString(), checkboxBoolean!!,
                contactInfoZipCode?.text.toString(),"89768","11111"
            )

            val gson = Gson()
            val jsonbranch: String = gson.toJson(i)

            println("jsonbranch business  "+jsonbranch)

            if (type.equals("ADD")){
                mBusinessViewModel.addNewBusiness(i)
            }else{
                mBusinessViewModel.updateNewBusiness(businessId,i)
            }
        }
    }


}