package com.example.twotwoninezero.dashboard.bottomnavigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.CountryListSpinnerAdapter
import com.example.twotwoninezero.common.StateListSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.model.ProfileViewModel
import com.example.twotwoninezero.service.GetCountryItem
import com.example.twotwoninezero.service.GetStateReponse
import com.example.twotwoninezero.service.SaveUpdateTaxableVehicleRequest
import com.example.twotwoninezero.service.TaxPreparerRequest
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_business.*
import kotlinx.android.synthetic.main.fragment_tax_preparer.*


class TaxPreparerFragment : BaseFragment() {
    private lateinit var mProfileViewModel : ProfileViewModel

    var mCountryListSpinnerAdapter: CountryListSpinnerAdapter?=null
    var mStateListSpinnerAdapter: StateListSpinnerAdapter?=null
    var mgetCountryAdapterList:List<GetCountryItem> = ArrayList()
    var mGetStateReponse:List<GetStateReponse> = ArrayList()
    var taxPrepayer:String=""
    var optradio:String=""
    var optradio1:String=""
    var countryId:String=""
    var preparerType:String=""
    var selfEmployed:String=""
    var state:String=""

    var manualCountry=""
    var manualState=""
    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
        setViewModel(mProfileViewModel)

        mProfileViewModel.mGetTaxPreparerResponse.observe(this, Observer {
            taxPrepayer=it.taxPreparer?:""
            preparerType=it.preparerType
            optradio=it.preparerType
            optradio1="Y"
            selfEmployed=it.selfEmployed?:""
            if (it.taxPreparer.isNullOrEmpty()){
                taxPayerYesOrNo.isChecked=false
            }else{
                taxPayerYesOrNo.isChecked=it.taxPreparer.equals("Y")
            }

            if (it.selfEmployed.isNullOrEmpty()){

            }else{
                taxPayerIndividual.isChecked=it.selfEmployed.equals("Y")
                taxPayerFirm.isChecked=it.selfEmployed.equals("N")
            }

            if (it.firmName.isNullOrEmpty()){
                taxPayerFirmNameTI.visibility=View.GONE
                taxPayerEINTI.visibility=View.GONE
            }else{
                taxPayerFirmNameTI.visibility=View.VISIBLE
                taxPayerEINTI.visibility=View.VISIBLE
            }


            taxPayerPersonalName.setText(it.preparerName)
            taxPayerEmail.setText(it.emailAddress)
            taxPayerPhoneNumber.setText(it.phone)
            taxPayerPTIN.setText(it.ptin)
            taxPayerFirmName.setText(it.firmName)
            taxPayerEIN.setText(it.firmEIN)
            taxPayerAddress1.setText(it.address1)
            taxPayerAddress2.setText(it.address2)
          //  taxPayerCountry.setText(it.preparerName)
            countryId=it.countryId
          //  taxPayerState.setText(it.preparerName)
            state=it.state
            taxPayerCity.setText(it.city)
            taxPayerZipCode.setText(it.zipCode)
            if (isOnline()) {
                mProfileViewModel.getCountry()
            }else{
                showToast(getString(R.string.internet_required))
            }


        })

        mProfileViewModel.mGetCountry.observe(this, Observer {
            mgetCountryAdapterList=it
            if (manualCountry.equals("Yes")){

            }else{
                it.forEach { i->
                    if (i.id.toString().equals(countryId)){
                        taxPayerCountry.setText(i.countryName)
                        mProfileViewModel.getCountryState(countryId)
                    }
                }
            }


        })

        mProfileViewModel.mGetStateReponse.observe(this, Observer {
            mGetStateReponse=it
            if (manualState.equals("Yes")){

            }else{
                it.forEach { i->
                    if (i.id.toString().equals(state)){
                        taxPayerState.setText(i.stateName)
                    }

                }
            }

        })

        mProfileViewModel.mUploadTaxpayerResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
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
        return inflater.inflate(R.layout.fragment_tax_preparer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        mProfileViewModel.getTaxPreparer()

        taxPayerCountry.isFocusable=false
        taxPayerCountry.isClickable=true

        taxPayerState.isFocusable=false
        taxPayerState.isClickable=true

        taxPayerfragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        taxPayerCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        taxPayerCountry.setOnClickListener {
            manualCountry="Yes"

            if (mgetCountryAdapterList.isNullOrEmpty()){
                if (isOnline()) {
                    mProfileViewModel.getCountry()
                }else{
                    showToast(getString(R.string.internet_required))
                }


            }else{
                contactInfoCountry()
            }
        }

        taxPayerState.setOnClickListener {
            manualState="Yes"
            if (mGetStateReponse.isNullOrEmpty()){
                mProfileViewModel.getCountryState(countryId)
            }else{
                contactState()
            }
        }

        taxPayerYesOrNo.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                taxPrepayer="Y"

            }else{
                taxPrepayer="N"
                makeEmpty()
            }
        }

        taxPayerRG.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId==R.id.taxPayerIndividual){
                selfEmployed="Y"
                preparerType="SELFEMPLOYED"
                optradio="SELFEMPLOYED"
                optradio1="Y"
                taxPayerFirmNameTI.visibility=View.GONE
                taxPayerEINTI.visibility=View.GONE
                taxPayerFirmName.setText("")
                taxPayerEIN.setText("")
            }else{
                selfEmployed="Y"
                preparerType="FIRM"
                optradio="FIRM"
                optradio1="Y"
                taxPayerFirmNameTI.visibility=View.VISIBLE
                taxPayerEINTI.visibility=View.VISIBLE
                taxPayerFirmName.setText("")
                taxPayerEIN.setText("")
            }
        }


        taxPayerSubmit.setOnClickListener {
            if (taxPrepayer.equals("Y")){
                if (taxPayerPersonalName.text.toString().isNullOrEmpty()){
                    showToast("Name is required")
                }else if (taxPayerEmail.text.toString().isNullOrEmpty()){
                    showToast("Email address is required.")
                }else if (taxPayerPhoneNumber.text.toString().isNullOrEmpty()){
                    showToast("Contact number is required")
                }else if (taxPayerPTIN.text.toString().isNullOrEmpty()){
                    showToast("PTIN is required.\n" +
                            "Type P Followed by 8 Digit Number And Space Not Allowed")
                }else if (taxPayerFirmName.text.toString().isNullOrEmpty()){
                    showToast("Firm name is required.")
                }else if (taxPayerEIN.text.toString().isNullOrEmpty()){
                    showToast("EIN is required.")
                }else if (taxPayerAddress1.text.toString().isNullOrEmpty()){
                        showToast("Address is required.")
                }else if (taxPayerCountry.text.toString().isNullOrEmpty()){
                        showToast("Country is required.")
                }else if (taxPayerState.text.toString().isNullOrEmpty()){
                        showToast("State is required.")
                }else if (taxPayerCity.text.toString().isNullOrEmpty()){
                        showToast("City is required.")
                }else if (taxPayerZipCode.text.toString().isNullOrEmpty()){
                        showToast("Zip Code is required.")
                }else{

                }
                val i = TaxPreparerRequest(taxPayerAddress1.text.toString(),taxPayerAddress2.text.toString(),taxPayerCity.text.toString(),countryId, taxPayerEmail.text.toString(),
                    taxPayerEIN.text.toString(),taxPayerFirmName.text.toString(),"","",optradio,optradio1,
                    taxPayerPhoneNumber.text.toString(),taxPayerPersonalName.text.toString(),preparerType,taxPayerPTIN.text.toString(),selfEmployed,state,
                    taxPrepayer,taxPayerZipCode.text.toString())
                mProfileViewModel.updateTaxpreparer(i)
            }else{
                val i = TaxPreparerRequest("","","","", "",
                    "","","","","SELFEMPLOYED","N",
                    "","","","","","",
                    "N","")
                mProfileViewModel.updateTaxpreparer(i)
            }



        }

    }

    private fun makeEmpty() {
        taxPayerAddress1.setText("")
        taxPayerAddress2.setText("")
        taxPayerCity.setText("")
        countryId =""
        taxPayerEmail.setText("")
        taxPayerEIN.setText("")
        taxPayerFirmName.setText("")
        optradio=""
        optradio1=""
        taxPayerPhoneNumber.setText("")
        taxPayerPersonalName.setText("")
        preparerType=""
        taxPayerPTIN.setText("")
        selfEmployed=""
        state=""
        taxPrepayer=""
        taxPayerZipCode.setText("")
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

        mCountryListSpinnerAdapter= CountryListSpinnerAdapter(mgetCountryAdapterList){ country, id->
            taxPayerCountry?.setText(country)
            countryId=id
            taxPayerState?.setText("")
            state=""
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mCountryListSpinnerAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            mProfileViewModel.getCountryState(countryId)
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
            taxPayerState?.setText(country)
            state=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mStateListSpinnerAdapter

        cancel.setOnClickListener {

            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}