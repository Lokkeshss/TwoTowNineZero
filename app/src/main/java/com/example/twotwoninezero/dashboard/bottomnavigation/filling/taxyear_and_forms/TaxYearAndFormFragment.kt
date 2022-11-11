package com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.BusinessTypeListSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.business.BusinessScreenFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.FirstUsedMonthAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.FormTypeAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxYearAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.service.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.taxyear_and_forms.*

class TaxYearAndFormFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mGetBusinessTypeRequestItemAdapterList:List<GetBusinessTypeRequestItem> = ArrayList()
    var mGetFillingFormResponseAdapterList:List<GetFillingFormResponse> = ArrayList()
    var mGetFillingTaxYearResponseAdapterList:List<GetFillingTaxYearResponse> = ArrayList()
    var mgetFillingFirstUsedMonthResponseAdapterList:List<getFillingFirstUsedMonthResponse> = ArrayList()
    var mBusinessTypeListSpinnerAdapter: BusinessTypeListSpinnerAdapter?=null
    var mFormTypeAdapter: FormTypeAdapter?=null
    var mTaxYearAdapter: TaxYearAdapter?=null
    var mFirstUsedMonthAdapter: FirstUsedMonthAdapter?=null
    var mAmendment_Month: FirstUsedMonthAdapter?=null
    var finalReturn: String="0"
    var addressChange: String="0"
    var amendedMonth : String=""
    var businesid: String=""
    var filingMonth: String=""
    var filingYearId: String=""
    var FormType: String=""
    var taxyearendmonth: String=""
    companion object{
        var filingId: String=""
    }
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetBusinessTypeRequestItem.observe(this, Observer {
            mGetBusinessTypeRequestItemAdapterList=it
        })
        mFillingViewModel.mGetFillingFormResponse.observe(this, Observer {
            mGetFillingFormResponseAdapterList=it
        })

        mFillingViewModel.mGetFillingTaxYearResponse.observe(this, Observer {
            mGetFillingTaxYearResponseAdapterList=it
        })
        mFillingViewModel.mgetFillingFirstUsedMonthResponse.observe(this, Observer {
            mgetFillingFirstUsedMonthResponseAdapterList=it
        })
        mFillingViewModel.mSaveUpdateFilingResponse.observe(this, Observer {
           if (it.code==200){
               showToast(it.message)
               filingId=it.filingId
               findNavController().navigate(
                   TaxYearAndFormFragmentDirections.actionTaxYearAndFormFragmentToTaxableVehicleInformation()
               )
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
        val view  = inflater.inflate(R.layout.taxyear_and_forms, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString("businessName")
            val id = it.getString("businessId")
            println("businessName "+name)
            println("businessName "+id)
            if (id != null && id.isNotEmpty()) {
                fillingBusinessName.setText(name)
                businesid=id
            }
        }

        mFillingViewModel.getBusinessTypeRequestItem()
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

            val i=SaveUpdateFilingRequest(addressChange,amendedMonth,
                businesid,filingId,filingMonth,filingYearId
                ,finalReturn,FormType,taxyearendmonth)

            val gson = Gson()
            val jsonbranch: String = gson.toJson(i)

            println("jsonbranch filing stepone  "+jsonbranch)

            mFillingViewModel.saveFiling(i)

        }

    }


    private fun showAdapterList(type: String) {

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
            mBusinessTypeListSpinnerAdapter=
                BusinessTypeListSpinnerAdapter(mGetBusinessTypeRequestItemAdapterList){ businesstype, id->
                    fillingBusinessName?.setText(businesstype)
                    businesid=id.toString()
                    customDialog.dismiss()
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            custom_rv?.layoutManager = mLayoutManager
            custom_rv?.adapter = mBusinessTypeListSpinnerAdapter

        }else if (type.equals("formtype")){
            title.text="Form Type"
            mFormTypeAdapter=
                FormTypeAdapter(mGetFillingFormResponseAdapterList){ formtype, type->
                    fillingFormType?.setText(formtype)
                    FormType=type
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
                        mFillingViewModel.getFirstUsedMonth(type)
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
                    filingYearId=id
                    mFillingViewModel.getFirstUsedMonth(type)
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
                    filingMonth=type
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
         filingMonth=""
         filingYearId=""
         taxyearendmonth=""

    }





}