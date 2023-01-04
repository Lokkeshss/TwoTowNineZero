package com.example.twotwoninezero.dashboard.bottomnavigation.business

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import kotlinx.android.synthetic.main.fragment_add_new_business.test
import kotlinx.android.synthetic.main.fragment_i_r_s_payment_submission_fee_payment_option.*
import java.util.*
import kotlin.collections.ArrayList

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id = it.getString("businessId")
            if (id != null && id.isNotEmpty()) {
                println("mEditBusinessListResponse "+id)
                if (isOnline()) {
                    mBusinessViewModel.editBusinessList(id)

                }else{
                    showToast(getString(R.string.internet_required))
                }

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


        if (isOnline()) {
            mBusinessViewModel.getCountry()
        }else{
            showToast(getString(R.string.internet_required))
        }

        if (isOnline()) {
            mBusinessViewModel.getBusinessTypeRequestItem()
        }else{
            showToast(getString(R.string.internet_required))
        }


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

                if (isOnline()) {
                    mBusinessViewModel.getCountry()
                }else{
                    showToast(getString(R.string.internet_required))
                }


            }else{
                contactInfoCountry()
            }

        }
        contactInfostate?.setOnClickListener {
            if (mGetStateReponse.isNullOrEmpty()){
                if (isOnline()) {
                    mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
                }else{
                    showToast(getString(R.string.internet_required))
                }

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

        businessProfileEin.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                var isValid = true
                if (working.length == 2 && before == 0) {

                        working += "-"
                        businessProfileEin.setText(working)
                        businessProfileEin.setSelection(working.length)

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })

        signingAuthInfoPhone.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                if (working.length == 3 && before == 0) {

                    working += "-"
                    signingAuthInfoPhone.setText(working)
                    signingAuthInfoPhone.setSelection(working.length)

                }
                if (working.length == 7){
                    working += "-"
                    signingAuthInfoPhone.setText(working)
                    signingAuthInfoPhone.setSelection(working.length)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })




        contactInfoPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                if (working.length == 3 && before == 0) {

                    working += "-"
                    contactInfoPhoneNumber.setText(working)
                    contactInfoPhoneNumber.setSelection(working.length)

                }
                if (working.length == 7){
                    working += "-"
                    contactInfoPhoneNumber.setText(working)
                    contactInfoPhoneNumber.setSelection(working.length)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })



        ThirdPartyDesignPhonenumber.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                if (working.length == 3 && before == 0) {

                    working += "-"
                    ThirdPartyDesignPhonenumber.setText(working)
                    ThirdPartyDesignPhonenumber.setSelection(working.length)

                }
                if (working.length == 7){
                    working += "-"
                    ThirdPartyDesignPhonenumber.setText(working)
                    ThirdPartyDesignPhonenumber.setSelection(working.length)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })


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
            mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
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
            if (isOnline()) {
                mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
                customDialog.dismiss()
            }else{
                showToast(getString(R.string.internet_required))
            }

        }
    }


    private fun editBusinessResponse(response: EditBusinessListResponse) {
        addNewBusinessSubmit?.setText("Update")
        contactInfoAddress?.setText(response.address1)
        businessProfileBusinessName?.setText(response.businessName)
        contactInfocity?.setText(response.city)
        val first= response.signingAuthorityPhone.replaceRange(3,3,"-")
        val signingAuthorityPhone= first.replaceRange(7,7,"-")
        signingAuthInfoPhone?.setText(signingAuthorityPhone)
        val ein = response.ein.replaceRange(2,2,"-")
        businessProfileEin?.setText(ein)
        contactInfostate?.setText(response.stateName)
        contactInfoCounty?.setText(response.countryName)
        thirdPartydesign_checkbox?.isChecked=response.thirdpartyStatus
        businessProfileBusinessType?.setText(response.bizType)
        contactInfoEmailAddress?.setText(response.email)
        val firstphone= response.phone.replaceRange(3,3,"-")
        val phone= firstphone.replaceRange(7,7,"-")
        contactInfoPhoneNumber?.setText(phone)
        signingAuthInfoName?.setText(response.signingAuthorityName)
        signingAuthInfoTitle?.setText(response.signingAuthorityTitle)
        ThirdPartyDesignName?.setText(response.thirdPartyDesigneeName)
        val firstthirdpartyphone= response.thirdPartyDesigneePhone.replaceRange(3,3,"-")
        val thirdPartyDesigneePhone= firstthirdpartyphone.replaceRange(7,7,"-")
        ThirdPartyDesignPhonenumber?.setText(thirdPartyDesigneePhone)
        contactInfoZipCode?.setText(response.zipCode)

        contactInfoCountyID=response.countryId
        contactInfostateId=response.stateId
        businessProfileBusinessTypeId=response.businessType
        checkboxBoolean=response.thirdpartyStatus

        BusinessId=response.id.toString()
        if (isOnline()) {
            mBusinessViewModel.getCountryState(contactInfoCountyID.toString())
        }else{
            showToast(getString(R.string.internet_required))
        }


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
                    signingAuthInfoPhone?.text.toString().replace("-", ""),businessProfileEin?.text.toString().replace("-", ""),
                    contactInfoEmailAddress?.text.toString(),
                    contactInfoPhoneNumber?.text.toString().replace("-", ""),signingAuthInfoName?.text.toString(),
                    signingAuthInfoTitle?.text.toString(),contactInfostateId.toString(),ThirdPartyDesignName?.text.toString(),
                    ThirdPartyDesignPhonenumber?.text.toString().replace("-", ""), checkboxBoolean!!,
                    contactInfoZipCode?.text.toString(),"89768","11111"
                )

                val gson = Gson()
                val jsonbranch: String = gson.toJson(i)

                println("jsonbranch business  "+jsonbranch)
                if (isOnline()) {
                    if (type.equals("ADD")){
                        mBusinessViewModel.addNewBusiness(i)
                    }else{
                        mBusinessViewModel.updateNewBusiness(businessId,i)
                    }

                }else{
                    showToast(getString(R.string.internet_required))
                }

            }
        }else{
            val i = CreateAndUpdateBusinessRequest(
                contactInfoAddress?.text.toString(),businessProfileBusinessName?.text.toString(),
                businessProfileBusinessTypeId!!,contactInfocity?.text.toString(), contactInfoCountyID.toString(),
                signingAuthInfoPhone?.text.toString().replace("-", ""),businessProfileEin?.text.toString().replace("-", ""),
                contactInfoEmailAddress?.text.toString(),
                contactInfoPhoneNumber?.text.toString().replace("-", ""),signingAuthInfoName?.text.toString(),
                signingAuthInfoTitle?.text.toString(),
                contactInfostateId.toString(),ThirdPartyDesignName?.text.toString(),
                ThirdPartyDesignPhonenumber?.text.toString().replace("-", ""), checkboxBoolean!!,
                contactInfoZipCode?.text.toString(),"89768","11111"
            )

            val gson = Gson()
            val jsonbranch: String = gson.toJson(i)

            println("jsonbranch business  "+jsonbranch)
            if (isOnline()) {
                if (type.equals("ADD")){
                    mBusinessViewModel.addNewBusiness(i)
                }else{
                    mBusinessViewModel.updateNewBusiness(businessId,i)
                }
            }else{
                showToast(getString(R.string.internet_required))
            }

        }
    }


}