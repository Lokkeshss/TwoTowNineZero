package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.lowmileagevehicles

import android.app.DatePickerDialog
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
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.service.SaveLowMileageVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import com.example.twotwoninezero.service.UpdateLowMileageVehicleRequest
import kotlinx.android.synthetic.main.fragment_add_new_low_mileage_vehicle.*
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import java.text.SimpleDateFormat
import java.util.*


class AddNewLowMileageVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel: FillingViewModel

    var documentName:String=""
    var firstUsedMonth:String=""
    var isLogging:String="N"
    var weight:String=""
    var weightCategory:String=""
    var id:String=""
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    val myCalendarToDate = Calendar.getInstance()
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(viewModelStore,defaultViewModelProviderFactory).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mTaxableWeightResponseList.observe(this, androidx.lifecycle.Observer {
            mTaxableWeightList=it
        })

        mFillingViewModel.mGetLowMileageByIdResponse.observe(this, androidx.lifecycle.Observer {
            lowMileageVehicleVIN.setText(it.vin)
            lowMileageVehicleTaxableWeight.setText(weight)
            lowMileageVehicleFirstUsedMonth.setText(it.firstUsedMonth)
            lowMileageVehicleVINCorrection.setText(it.refundExplanation)
            lowMileageVehicleVehicleLogging.isChecked=it.isLogging.equals("Y")
            weightCategory=it.weightCategory
            firstUsedMonth=it.firstUsedMonth
            isLogging=it.isLogging

        })

        mFillingViewModel.mSaveLowMileageVehicleResponse.observe(this, Observer {
            if (it.code==200){
                requireActivity().onBackPressed()
                showToast(it.message)
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mUpdateLowMileageVehicleResponse.observe(this, Observer {
            if (it.code==200){
                requireActivity().onBackPressed()
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
        return inflater.inflate(R.layout.fragment_add_new_low_mileage_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val filingId = it.getString("filingId")
            id = it.getString("id").toString()
            weight = it.getString("weight").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.getLowMileageById(id, filingId.toString())
                lowMileageVehicleSubmit.setText("Update")
            }

        }
        mFillingViewModel.gettaxableweight()
        lowMileageVehicleTaxableWeight.isFocusable=false
        lowMileageVehicleTaxableWeight.isClickable=false

        lowMileageVehicleFirstUsedMonth.isFocusable=false
        lowMileageVehicleFirstUsedMonth.isClickable=false

        lowMileageVehicleTaxableWeight.setOnClickListener {
            showTaxableWeight()
        }

        lowMileageVehicleVehicleLogging.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                isLogging="Y"
            }else{
                isLogging="N"
            }
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

        lowMileageVehicleFirstUsedMonth.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                toDate,
                myCalendarToDate[Calendar.YEAR],
                myCalendarToDate[Calendar.MONTH],
                myCalendarToDate[Calendar.DAY_OF_MONTH]
            ).show()
        }

        lowMileageVehicleSubmit.setOnClickListener {
            if (lowMileageVehicleVIN.text.toString().isNullOrEmpty()){
                showToast("VIN must be at least 17 characters long.")
            }else if (firstUsedMonth.isNullOrEmpty()){
                showToast("Select First Used Month")
            }else if (isLogging.isNullOrEmpty()){
                showToast("Select Vehicle used for Logging?")
            }else if (lowMileageVehicleVINCorrection.text.toString().isNullOrEmpty()){
                showToast("Select Explanation for VIN correction")
            }else if (weightCategory.isNullOrEmpty()){
                showToast("Select Taxable Gross Weight")
            }else{
                if (lowMileageVehicleSubmit.text.toString().equals("Save ")){

                    val i= SaveLowMileageVehicleRequest(documentName, filingId,firstUsedMonth,isLogging,lowMileageVehicleVINCorrection.text.toString(),
                        lowMileageVehicleVIN.text.toString(),weightCategory)
                    mFillingViewModel.saveLowMileageVehicle(filingId,i)
                }else{
                    val i= UpdateLowMileageVehicleRequest( "",firstUsedMonth,isLogging,lowMileageVehicleVINCorrection.text.toString(),
                        lowMileageVehicleVIN.text.toString(),weightCategory)
                    mFillingViewModel.updateLowMileageVehicle(id,filingId,i)

                }

            }
        }
    }

    private fun updateLabelToDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        lowMileageVehicleFirstUsedMonth?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-MM-dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        firstUsedMonth=dateFormatS.format(myCalendarToDate.time)
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
            lowMileageVehicleTaxableWeight?.setText(weight)
            weightCategory=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            lowMileageVehicleTaxableWeight?.setText("")
            weightCategory=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }



}