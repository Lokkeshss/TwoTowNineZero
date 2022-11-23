package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.solddestroyedstolenvehicle

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.*
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.service.SaveSoldDestroyedVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import com.example.twotwoninezero.service.UpdateSoldDestroyedVehicleRequest
import kotlinx.android.synthetic.main.fragment_add_new_prior_year_suspended_exempt_vehicles.*
import kotlinx.android.synthetic.main.fragment_add_new_reporting_suspended_exempt_vehicles.*
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import kotlinx.android.synthetic.main.taxyear_and_forms.*
import java.text.SimpleDateFormat
import java.util.*

class AddNewSoldDestroyedorStolenVehicle : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel

    var TypeOfLoss = listOf<String>("sold","destroyed","stolen")
    var mTypeOfLossAdapter: TypeOfLossAdapter?=null
    var firstUsedMonth:String=""
    var soldDateText:String=""
    var weightCategory:String=""
    var isLogging:String="N"
    var documentName:String=""
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    val myCalendarToDate = Calendar.getInstance()
    val myCalendarSoldDate = Calendar.getInstance()
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    var id:String=""
    var editWeight=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)


        mFillingViewModel.mTaxableWeightResponseList.observe(this, androidx.lifecycle.Observer {
            mTaxableWeightList=it
        })

        mFillingViewModel.mEditgetSoldDestroyedByIdResponse.observe(this, androidx.lifecycle.Observer {
            addnewDestroyedStolenVehicleVIN.setText(it.vin)
            addnewDestroyedStolenVehicleTaxableWeight.setText(editWeight)
            addnewDestroyedStolenVehicleFirstUsedMonth.setText(it.firstUsedMonth)
            firstUsedMonth=it.firstUsedMonth
            addnewDestroyedSoldVehicleDestroyedDate.setText(it.soldDestroyedDate)
            soldDateText=it.soldDestroyedDate
            addnewDestroyedStolenVehicleTypeLoss.setText(it.lossType)
            addnewDestroyedStolenVehicleVINCorrection.setText(it.refundExplanation)
            addnewDestroyedStolenVehicleLogging.isChecked=it.isLogging.equals("Y")
            isLogging=it.isLogging
            weightCategory=it.weightCategory
        })

        mFillingViewModel.mSaveSoldDestroyedVehicleResponse.observe(this, androidx.lifecycle.Observer {
          if (it.code==200){
              showToast(it.message)
              requireActivity().onBackPressed()
          }else{
              showToast(it.message)
          }
        })
        mFillingViewModel.mupdateSoldDestroyedVehicle.observe(this, androidx.lifecycle.Observer {
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
        return inflater.inflate(
            R.layout.fragment_add_new_sold_destroyedor_stolen_vehicle,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            editWeight = it.getString("weight").toString()
            val filingId = it.getString("filingId")

            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.editgetSoldDestroyedById(id, filingId.toString())
                addnewDestroyedStolenVehicleSubmit.setText("Update")
            }

        }


        addnewDestroyedStolenVehicleTaxableWeight.isFocusable=false
        addnewDestroyedStolenVehicleTaxableWeight.isClickable=false

        addnewDestroyedStolenVehicleFirstUsedMonth.isFocusable=false
        addnewDestroyedStolenVehicleFirstUsedMonth.isClickable=false

        addnewDestroyedSoldVehicleDestroyedDate.isFocusable=false
        addnewDestroyedSoldVehicleDestroyedDate.isClickable=false

        addnewDestroyedStolenVehicleTypeLoss.isFocusable=false
        addnewDestroyedStolenVehicleTypeLoss.isClickable=false
        mFillingViewModel.gettaxableweight()

        addnewDestroyedStolenVehicleTaxableWeight.setOnClickListener {
            showTaxableWeight()
        }

        val toDate: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: android.widget.DatePicker?,
                year: Int,
                month: Int,
                day: Int
            ) {
                myCalendarToDate[Calendar.YEAR] = year
                myCalendarToDate[Calendar.MONTH] = month
                myCalendarToDate[Calendar.DAY_OF_MONTH] = day
                updateLabelToDate()
            }
        }

        val soldDate: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: android.widget.DatePicker?,
                year: Int,
                month: Int,
                day: Int
            ) {
                myCalendarSoldDate[Calendar.YEAR] = year
                myCalendarSoldDate[Calendar.MONTH] = month
                myCalendarSoldDate[Calendar.DAY_OF_MONTH] = day
                updateLabelSoldDate()
            }
        }

        addnewDestroyedStolenVehicleFirstUsedMonth.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                toDate,
                myCalendarToDate[Calendar.YEAR],
                myCalendarToDate[Calendar.MONTH],
                myCalendarToDate[Calendar.DAY_OF_MONTH]
            ).show()
        }

        addnewDestroyedSoldVehicleDestroyedDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                soldDate,
                myCalendarSoldDate[Calendar.YEAR],
                myCalendarSoldDate[Calendar.MONTH],
                myCalendarSoldDate[Calendar.DAY_OF_MONTH]
            ).show()
        }

        addnewDestroyedStolenVehicleTypeLoss.setOnClickListener {

            showAdapterList()
        }


        addnewDestroyedStolenVehicleLogging.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    isLogging="Y"
                }else{
                    isLogging="N"
                }
        }


        addnewDestroyedStolenVehicleSubmit.setOnClickListener {
            if (addnewDestroyedStolenVehicleVIN.text.toString().isNullOrEmpty()){
                showToast("VIN must be at least 17 characters long.")
            }else if (firstUsedMonth.isNullOrEmpty()){
                showToast("Select First Used Month")
            }else if (isLogging.isNullOrEmpty()){
                showToast("Select Vehicle used for Logging?")
            }else if (addnewDestroyedStolenVehicleTypeLoss.text.toString().isNullOrEmpty()){
                showToast("Select Type Of Loss")
            }else if (addnewDestroyedStolenVehicleVINCorrection.text.toString().isNullOrEmpty()){
                showToast("Select Explanation for VIN correction")
            }else if (soldDateText.isNullOrEmpty()){
                showToast("Select Sold Date Month")
            }else if (weightCategory.isNullOrEmpty()){
                showToast("Select Taxable Gross Weight")
            }else{
                if (addnewDestroyedStolenVehicleSubmit.text.toString().equals("Save ")){
                    val i = SaveSoldDestroyedVehicleRequest(documentName, filingId,firstUsedMonth,isLogging,
                        addnewDestroyedStolenVehicleTypeLoss.text.toString(),addnewDestroyedStolenVehicleVINCorrection.text.toString(),
                        soldDateText,
                        addnewDestroyedStolenVehicleVIN.text.toString(),weightCategory)
                    mFillingViewModel.saveSoldDestroyedVehicle(filingId,i)
                }else{
                    val i = UpdateSoldDestroyedVehicleRequest(documentName,firstUsedMonth,isLogging,addnewDestroyedStolenVehicleTypeLoss.text.toString(),
                        addnewDestroyedStolenVehicleVINCorrection.text.toString(),soldDateText,
                        addnewDestroyedStolenVehicleVIN.text.toString(),weightCategory)

                    mFillingViewModel.updateSoldDestroyedVehicle(id,filingId,i)

                }


            }
        }

        addnewDestroyedStolenVehicleCancel.setOnClickListener {

        }



    }

    private fun showAdapterList() {

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)

            title.text="Type Of Loss"

        mTypeOfLossAdapter = TypeOfLossAdapter(TypeOfLoss){ type->
                addnewDestroyedStolenVehicleTypeLoss?.setText(type)
                customDialog.dismiss()
            }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTypeOfLossAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun updateLabelToDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        addnewDestroyedStolenVehicleFirstUsedMonth?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-MM-dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        firstUsedMonth=dateFormatS.format(myCalendarToDate.time)
        // filterToDateValue=dateFormatS.format(myCalendarFromDate.time)
    }

    private fun updateLabelSoldDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        addnewDestroyedSoldVehicleDestroyedDate?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-MM-dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        soldDateText=dateFormatS.format(myCalendarToDate.time)
        // filterToDateValue=dateFormatS.format(myCalendarFromDate.time)
    }



    private fun showTaxableWeight() {

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Weight"

        mTaxableWeightSpinnerAdapter= TaxableWeightSpinnerAdapter(mTaxableWeightList){ weight, id->
            addnewDestroyedStolenVehicleTaxableWeight?.setText(weight)
            weightCategory=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            addnewDestroyedStolenVehicleTaxableWeight?.setText("")
            weightCategory=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }


}