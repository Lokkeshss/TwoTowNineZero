package com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.BusinessNameListAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.FirstUsedMonthAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.FormTypeAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxYearAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.VehiclesTaxMainMenuDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.FilterCategoryAdapter
import com.example.twotwoninezero.service.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.progress_bar_view.*
import kotlinx.android.synthetic.main.taxyear_and_forms.*
import kotlinx.android.synthetic.main.vehicles_tax_main_menu.*

class TaxYearAndFormFragment : BaseFragment() {
    var allowForEdit:Boolean=false
    private lateinit var mFillingViewModel : FillingViewModel
    var mGetBusinessTypeRequestItemAdapterList:List<AllAndSearchBusinessListResponse> = ArrayList()
    var mGetFillingFormResponseAdapterList:List<GetFillingFormResponse> = ArrayList()
    var mGetFillingTaxYearResponseAdapterList:List<GetFillingTaxYearResponse> = ArrayList()
    var mgetFillingFirstUsedMonthResponseAdapterList:List<getFillingFirstUsedMonthResponse> = ArrayList()
    var mBusinessNameListAdapter: BusinessNameListAdapter?=null
    var mFormTypeAdapter: FormTypeAdapter?=null
    var mTaxYearAdapter: TaxYearAdapter?=null
    var mFirstUsedMonthAdapter: FirstUsedMonthAdapter?=null
    var mAmendment_Month: FirstUsedMonthAdapter?=null
    var finalReturn: String="0"
    var addressChange: String="0"
    var amendedMonth : String=""
    var businesid: String=""
    var filingFirstUsedMonth: String=""
    var filingYearId: String=""

    var taxyearendmonth: String=""
    var filingTaxYrId: String=""
    var filingId: String=""
    var formType: String=""
    var filingMonth: String=""
    companion object{

       // var formType: String=""
        //var formType: String=""
    }
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetBusinessTypeRequestItem.observe(this, Observer {
            mGetBusinessTypeRequestItemAdapterList=it
            if (allowForEdit){
                if (it.isNotEmpty()){
                    it.forEach { i->
                        if (i.id.toString().equals(businesid)){
                            fillingBusinessName.setText(i.businessName)
                        }

                    }
                }
            }
        })
        mFillingViewModel.mGetFillingFormResponse.observe(this, Observer {
            mGetFillingFormResponseAdapterList=it

            if (allowForEdit){
                if (it.isNotEmpty()){
                    it.forEach { i->
                        if (i.type.equals(formType)){
                            fillingFormType.setText(i.desc)

                        }
                    }
                }
            }
        })

        mFillingViewModel.mGetFillingTaxYearResponse.observe(this, Observer {
            mGetFillingTaxYearResponseAdapterList=it
            if (allowForEdit){
                if (it.isNotEmpty()){
                    it.forEach { i->
                        if (i.id.toString().equals(filingTaxYrId)){
                            fillingTaxYear.setText(i.displayYear)
                        }

                    }
                }
            }
        })
        mFillingViewModel.mgetFillingFirstUsedMonthResponse.observe(this, Observer {
            mgetFillingFirstUsedMonthResponseAdapterList=it
            if (allowForEdit){
                if (it.isNotEmpty()){

                    if (formType.equals("8849S6")) {
                        it.forEach {i->
                            if (i.firstUsedMonthId.toString().equals(taxyearendmonth)){
                                fillingYourIncomeTax.setText(i.firstUsedMonth)
                            }
                        }
                    }else{
                        it.forEach { i->
                            if (i.firstUsedMonthId.toString().equals(filingMonth)){
                                fillingFirstUserMonth.setText(i.firstUsedMonth)

                            }

                            if (i.firstUsedMonthId.toString().equals(amendedMonth)){
                                fillingAmendmentMonth.setText(i.firstUsedMonth)
                            }

                        }
                    }

                }
            }
        })
        mFillingViewModel.mSaveUpdateFilingResponse.observe(this, Observer {
           if (it.code==200){
               showToast(it.message)
               filingId=it.filingId
               findNavController().navigate(
                   TaxYearAndFormFragmentDirections.actionTaxYearAndFormFragmentToTaxableVehicleInformation(filingId,formType)
               )
           }else{
               showToast(it.message)
           }
        })

        mFillingViewModel.mGetBusinessTypeRequestItemForEdit.observe(this, Observer {

        })

        mFillingViewModel.mGetFillingFormResponseForEdit.observe(this, Observer {

        })

        mFillingViewModel.mGetFillingTaxYearResponseForEdit.observe(this, Observer {

        })

        mFillingViewModel.mgetFillingFirstUsedMonthResponseForEdit.observe(this, Observer {

        })

        mFillingViewModel.mGetFilingByIdResponse.observe(this, Observer {

            fillingSubmit.setText("Update")
            allowForEdit=true


            filingTaxYrId=it.filingTaxYrId.toString()
            filingId=it.id.toString()
            businesid=it.businessId.toString()
            formType = it.formType
            filingMonth = it.filingMonth


            finalReturn=it.finalReturn
            addressChange=it.addressChange
            amendedMonth =it.amendedMonth
            filingFirstUsedMonth=it.filingMonth
          //  filingYearId=it.filingYear
            filingYearId=it.filingTaxYrId.toString()
            taxyearendmonth=it.taxYearEndMonth

            mFillingViewModel.getallbusinesslist("active","0","0","active")
            mFillingViewModel.getFormType()

            if (!it.formType.equals("8849S6")) {
                mFillingViewModel.gettaxyear(it.formType)
            }

            if (it.formType.equals("2290")){
                fieldVisibility(View.VISIBLE,View.GONE,View.GONE,View.VISIBLE,View.VISIBLE)
                mFillingViewModel.getFirstUsedMonth(filingTaxYrId,formType)
                amendedMonth =""
                filingFirstUsedMonth=it.filingMonth
                filingYearId=it.filingTaxYrId.toString()
                taxyearendmonth=""
            }else if (it.formType.equals("2290A1")){
                fieldVisibility(View.VISIBLE,View.VISIBLE,View.GONE,View.VISIBLE,View.VISIBLE)
                mFillingViewModel.getFirstUsedMonth(filingTaxYrId,formType)
                amendedMonth =it.amendedMonth
                filingFirstUsedMonth=it.filingMonth
                filingYearId=it.filingTaxYrId.toString()
                taxyearendmonth=""
            }else if (it.formType.equals("2290A2")){
                fieldVisibility(View.VISIBLE,View.VISIBLE,View.GONE,View.VISIBLE,View.VISIBLE)
                mFillingViewModel.getFirstUsedMonth(filingTaxYrId,formType)
                amendedMonth =it.amendedMonth
                filingFirstUsedMonth=it.filingMonth
                filingYearId=it.filingTaxYrId.toString()
                taxyearendmonth=""
            }else if (it.formType.equals("2290V")){
                fieldVisibility(View.VISIBLE,View.GONE,View.GONE,View.GONE,View.VISIBLE)
                mFillingViewModel.getFirstUsedMonth(filingTaxYrId,formType)
                amendedMonth =""
                filingFirstUsedMonth=it.filingMonth
                filingYearId=it.filingTaxYrId.toString()
                taxyearendmonth=""
            }else if (it.formType.equals("8849S6")){
                fieldVisibility(View.GONE,View.GONE,View.VISIBLE,View.GONE,View.GONE)
                mFillingViewModel.getFirstUsedMonth("0",formType)
                amendedMonth =""
                filingFirstUsedMonth=""
                filingYearId=""
                taxyearendmonth=it.taxYearEndMonth
            }else{
                fieldVisibility(View.GONE,View.GONE,View.GONE,View.GONE,View.GONE)
            }

            finalReturn=it.finalReturn
            addressChange=it.addressChange

            fillingFinalReturn.isChecked=it.finalReturn.equals("1")
            fillingAddressChange.isChecked=it.addressChange.equals("1")


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
        val view  = inflater.inflate(R.layout.taxyear_and_forms, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString("businessName")
            val id = it.getString("businessId")
            filingId = it.getString("filingId").toString()

            println("businessName "+name)
            println("businessName "+id)
            if (id != null && id.isNotEmpty()) {
                fillingBusinessName.setText(name)
                businesid=id
            }

            if (filingId !=null &&filingId.isNotEmpty()){
                mFillingViewModel.getFilingById(filingId)
            }

        }


        progress_bar.progress=16
        progress_text.setText("1 of 6")

        mFillingViewModel.getallbusinesslist("active","0","0","active")
        mFillingViewModel.getFormType()

        TIfillingFirstUserMonth.visibility=View.GONE
        TTfillingAmendmentMonth.visibility=View.GONE
        TIfillingYourIncomeTax.visibility=View.GONE
        LLSwitch.visibility=View.GONE

        fillingBusinessName?.setFocusable(false)
        fillingBusinessName?.setClickable(true)
        fillingFormType?.setFocusable(false)
        fillingFormType?.setClickable(true)
        fillingTaxYear?.setFocusable(false)
        fillingTaxYear?.setClickable(true)
        fillingFirstUserMonth?.setFocusable(false)
        fillingFirstUserMonth?.setClickable(true)
        fillingAmendmentMonth?.setFocusable(false)
        fillingAmendmentMonth?.setClickable(true)
        fillingYourIncomeTax?.setFocusable(false)
        fillingYourIncomeTax?.setClickable(true)

        fillingBusinessName.setOnClickListener {
            if (!mGetBusinessTypeRequestItemAdapterList.isNullOrEmpty()){
               showAdapterList("business")
            }
        }

        fillingFormType.setOnClickListener {
            showAdapterList("formtype")
        }
        fillingTaxYear.setOnClickListener {
            showAdapterList("taxyear")

        }
        fillingFirstUserMonth.setOnClickListener {

            showAdapterList("firstusermonth")

        }
        fillingAmendmentMonth.setOnClickListener {
            showAdapterList("AmendmentMonth")
        }

        fillingYourIncomeTax.setOnClickListener {
            showAdapterList("incomeTax")
        }

        fillingFinalReturn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                finalReturn="1"
            }else{
                finalReturn="0"
            }
        }
        fillingAddressChange.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                addressChange="1"
            }else{
                addressChange="0"
            }
        }


        fillingSubmit.setOnClickListener {
            if (formType.equals("2290")){

                if (fillingBusinessName.text.toString().isNullOrEmpty()){
                    showToast("Business name is required")
                } else if (fillingFormType.text.toString().isNullOrEmpty()){
                    showToast("Form type is required")
                } else if (fillingTaxYear.text.toString().isNullOrEmpty()){
                    showToast("Tax year is required")
                }else if (fillingFirstUserMonth.text.toString().isNullOrEmpty()){
                    showToast("First used month is required")
                }else{
                    if (fillingSubmit.text.toString().equals("Submit")){
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.saveFiling(i)
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }else{
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.updateFiling(filingId,i)
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }

                }
            }else if (formType.equals("2290A1")){
                if (fillingBusinessName.text.toString().isNullOrEmpty()){
                    showToast("Business name is required")
                } else if (fillingFormType.text.toString().isNullOrEmpty()){
                    showToast("Form type is required")
                } else if (fillingTaxYear.text.toString().isNullOrEmpty()){
                    showToast("Tax year is required")
                }else if (fillingFirstUserMonth.text.toString().isNullOrEmpty()) {
                    showToast("First used month is required")
                }else if (fillingAmendmentMonth.text.toString().isNullOrEmpty()){
                    showToast("Amendment month is required")
                }else{
                    if (fillingSubmit.text.toString().equals("Submit")){
                        if (filingFirstUsedMonth.toInt()<=amendedMonth.toInt()){
                            val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                                businesid,filingId,filingFirstUsedMonth,filingYearId
                                ,finalReturn,formType,taxyearendmonth)
                            mFillingViewModel.saveFiling(i)
                            val gson = Gson()
                            val jsonbranch: String = gson.toJson(i)

                            println("jsonbranch filing stepone  "+jsonbranch)
                        }else{
                            showToast("Amendment month should be greater than first used month")
                        }

                    }else{
                        if (filingFirstUsedMonth.toInt()<=amendedMonth.toInt()){
                            val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                                businesid,filingId,filingFirstUsedMonth,filingYearId
                                ,finalReturn,formType,taxyearendmonth)
                            mFillingViewModel.updateFiling(filingId,i)
                            val gson = Gson()
                            val jsonbranch: String = gson.toJson(i)

                            println("jsonbranch filing stepone  "+jsonbranch)
                        }else{
                            showToast("Amendment month should be greater than first used month")
                        }
                    }

                }
            }else if (formType.equals("2290A2")){
                if (fillingBusinessName.text.toString().isNullOrEmpty()){
                    showToast("Business name is required")
                } else if (fillingFormType.text.toString().isNullOrEmpty()){
                    showToast("Form type is required")
                } else if (fillingTaxYear.text.toString().isNullOrEmpty()){
                    showToast("Tax year is required")
                }else if (fillingFirstUserMonth.text.toString().isNullOrEmpty()) {
                    showToast("First used month is required")
                }else if (fillingAmendmentMonth.text.toString().isNullOrEmpty()){
                    showToast("Amendment month is required")
                }else{
                    if (fillingSubmit.text.toString().equals("Submit")){
                        if (filingFirstUsedMonth.toInt()<=amendedMonth.toInt()){
                            val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                                businesid,filingId,filingFirstUsedMonth,filingYearId
                                ,finalReturn,formType,taxyearendmonth)
                            mFillingViewModel.saveFiling(i)
                            val gson = Gson()
                            val jsonbranch: String = gson.toJson(i)

                            println("jsonbranch filing stepone  "+jsonbranch)
                        }else{
                            showToast("Amendment month should be greater than first used month")
                        }
                    }else{
                        if (filingFirstUsedMonth.toInt()<=amendedMonth.toInt()){
                            val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                                businesid,filingId,filingFirstUsedMonth,filingYearId
                                ,finalReturn,formType,taxyearendmonth)
                            mFillingViewModel.updateFiling(filingId,i)
                            val gson = Gson()
                            val jsonbranch: String = gson.toJson(i)

                            println("jsonbranch filing stepone  "+jsonbranch)
                        }else{
                            showToast("Amendment month should be greater than first used month")
                        }
                    }

                }
            }else if (formType.equals("2290V")){

                if (fillingBusinessName.text.toString().isNullOrEmpty()){
                    showToast("Business name is required")
                } else if (fillingFormType.text.toString().isNullOrEmpty()){
                    showToast("Form type is required")
                } else if (fillingTaxYear.text.toString().isNullOrEmpty()){
                    showToast("Tax year is required")
                }else if (fillingFirstUserMonth.text.toString().isNullOrEmpty()){
                    showToast("First used month is required")
                }else{
                    if (fillingSubmit.text.toString().equals("Submit")){
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.saveFiling(i)

                        //formType  filingFirstUsedMonth  amendedMonth
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }else{
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.updateFiling(filingId,i)

                        //formType  filingFirstUsedMonth  amendedMonth
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }

                }
            }else if (formType.equals("8849S6")){

                if (fillingBusinessName.text.toString().isNullOrEmpty()){
                    showToast("Business name is required")
                } else if (fillingFormType.text.toString().isNullOrEmpty()){
                    showToast("Form type is required")
                } else if (fillingYourIncomeTax.text.toString().isNullOrEmpty()){
                    showToast("Tax year ends month is required")
                }else{

                    if (fillingSubmit.text.toString().equals("Submit")){
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.saveFiling(i)
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }else{
                        val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                            businesid,filingId,filingFirstUsedMonth,filingYearId
                            ,finalReturn,formType,taxyearendmonth)
                        mFillingViewModel.updateFiling(filingId,i)
                        val gson = Gson()
                        val jsonbranch: String = gson.toJson(i)

                        println("jsonbranch filing stepone  "+jsonbranch)
                    }

                }
            }


        }

    }

    private fun showAdapterList(type: String) {
        allowForEdit=false
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)

        if (type.equals("business")){
            title.text="Business Type"
            mBusinessNameListAdapter=
                BusinessNameListAdapter(mGetBusinessTypeRequestItemAdapterList){ businesstype, id->
                    fillingBusinessName?.setText(businesstype)
                    businesid=id.toString()
                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mBusinessNameListAdapter

        }else if (type.equals("formtype")){
            title.text="Form Type"
            mFormTypeAdapter=
                FormTypeAdapter(mGetFillingFormResponseAdapterList){ formtype, type->
                    fillingFormType?.setText(formtype)
                    formType=type
                    if (!type.equals("8849S6")) {
                        mFillingViewModel.gettaxyear(type)
                    }

                    if (type.equals("2290")){
                        fieldVisibility(View.VISIBLE,View.GONE,View.GONE,View.VISIBLE,View.VISIBLE)
                    }else if (type.equals("2290A1")){
                        fieldVisibility(View.VISIBLE,View.VISIBLE,View.GONE,View.VISIBLE,View.VISIBLE)
                    }else if (type.equals("2290A2")){
                        fieldVisibility(View.VISIBLE,View.VISIBLE,View.GONE,View.VISIBLE,View.VISIBLE)
                    }else if (type.equals("2290V")){
                        fieldVisibility(View.VISIBLE,View.GONE,View.GONE,View.GONE,View.VISIBLE)
                    }else if (type.equals("8849S6")){
                        mFillingViewModel.getFirstUsedMonth("0",type)
                        fieldVisibility(View.GONE,View.GONE,View.VISIBLE,View.GONE,View.GONE)
                    }else{
                        fieldVisibility(View.GONE,View.GONE,View.GONE,View.GONE,View.GONE)
                    }

                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mFormTypeAdapter
        }else if (type.equals("taxyear")){
            title.text="Tax Year"
            mTaxYearAdapter=
                TaxYearAdapter(mGetFillingTaxYearResponseAdapterList){ formtype, type,id->
                    fillingTaxYear?.setText(formtype)
                    fillingFirstUserMonth?.setText("")
                    filingFirstUsedMonth=""
                    fillingAmendmentMonth?.setText("")
                    amendedMonth=""
                    filingYearId=id
                    mFillingViewModel.getFirstUsedMonth(filingYearId,type)
                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mTaxYearAdapter
        }else if (type.equals("incomeTax")){
            title.text="Month Your Income Tax Year Ends"
            mFirstUsedMonthAdapter=
                FirstUsedMonthAdapter(mgetFillingFirstUsedMonthResponseAdapterList){ formtype, type->
                    fillingYourIncomeTax?.setText(formtype)
                    taxyearendmonth=type
                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mFirstUsedMonthAdapter
        }else if (type.equals("firstusermonth")){
            title.text="First User Month"
            mFirstUsedMonthAdapter=
                FirstUsedMonthAdapter(mgetFillingFirstUsedMonthResponseAdapterList){ formtype, type->
                    fillingFirstUserMonth?.setText(formtype)
                    filingFirstUsedMonth=type
                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mFirstUsedMonthAdapter
        }else if (type.equals("AmendmentMonth")) {

            title.text = "Amendment Month"
            mAmendment_Month =
                FirstUsedMonthAdapter(mgetFillingFirstUsedMonthResponseAdapterList) { formtype, type ->
                    fillingAmendmentMonth?.setText(formtype)
                    amendedMonth=type
                    customDialog.dismiss()
                }


            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mAmendment_Month
        }

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    fun fieldVisibility(visible: Int, gone: Int, gone1: Int, visible1: Int, gone2: Int) {

        TIfillingFirstUserMonth.visibility=visible
        TTfillingAmendmentMonth.visibility=gone
        TIfillingYourIncomeTax.visibility=gone1
        LLSwitch.visibility=visible1
        TIfillingTaxYear.visibility=gone2

        fillingFirstUserMonth.setText("")
        fillingTaxYear.setText("")
        fillingAmendmentMonth.setText("")
        fillingYourIncomeTax.setText("")
        fillingFinalReturn.isChecked=false
        fillingAddressChange.isChecked=false

         finalReturn="0"
         addressChange="0"
         amendedMonth =""
         filingFirstUsedMonth=""
         filingYearId=""
         taxyearendmonth=""

    }





}