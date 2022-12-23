package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.lowmileagevehicles

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.common.getOnlyDateFromDate
import com.example.twotwoninezero.common.getOnlyMonthFromDate
import com.example.twotwoninezero.common.getOnlyyearFromDate
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.service.SaveLowMileageVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import com.example.twotwoninezero.service.UpdateLowMileageVehicleRequest
import kotlinx.android.synthetic.main.fragment_add_new_low_mileage_vehicle.*
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
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
    var filingId:String=""
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    val myCalendarToDate = Calendar.getInstance()

    var minDate:String=""
    var maxDate:String=""
    var resultLauncher: ActivityResultLauncher<Intent>? = null
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

        mFillingViewModel.mGetLowMileageDateResponse.observe(this, Observer {
            minDate=it.minDate
            maxDate=it.maxDate
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

             filingId = it.getString("filingId").toString()
            id = it.getString("id").toString()
            weight = it.getString("weight").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.getLowMileageById(id, filingId.toString())
                lowMileageVehicleSubmit.setText("Update")
            }
            mFillingViewModel.getLowMileageDate(filingId)
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
            val mDialog= DatePickerDialog(
                requireContext(),
                toDate,
                myCalendarToDate[Calendar.YEAR],
                myCalendarToDate[Calendar.MONTH],
                myCalendarToDate[Calendar.DAY_OF_MONTH]
            )
            val minDay = getOnlyDateFromDate(minDate).toInt()
            val minMonth =  getOnlyMonthFromDate(minDate).toInt()
            val minYear =  getOnlyyearFromDate(minDate).toInt()
            myCalendarToDate.set(minYear, minMonth-1, minDay)
            mDialog.datePicker.minDate = myCalendarToDate.timeInMillis

            // Changing mCalendar date from current to
            // some random MAX day 20/08/2021 20 Aug 2021
            val maxDay = getOnlyDateFromDate(maxDate).toInt()
            val maxMonth = getOnlyMonthFromDate(maxDate).toInt()
            val maxYear = getOnlyyearFromDate(maxDate).toInt()
            myCalendarToDate.set(maxYear, maxMonth-1, maxDay)
            mDialog.datePicker.maxDate = myCalendarToDate.timeInMillis
            mDialog.show()
        }

        lowMileageVehicleSubmit.setOnClickListener {
            if (lowMileageVehicleVIN.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            }else if (lowMileageVehicleVIN.text.toString().length<17){
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

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult> { result ->
                val data = result.data
                if (data != null) {
                    val sUri: Uri? = data.data
                    ConvertToString(sUri!!)
                }
            })

        lowMileageVehicleUploadDocument.setOnClickListener {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                selectPDF()
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 300)
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


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context as Context,permission) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission(permission: String, requestCode: Int) {
        requestPermissions(arrayOf(permission),requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            300 ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectPDF()
                } else {
                    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 300)
                }
        }
    }

    private fun selectPDF() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        resultLauncher!!.launch(intent)
    }

    fun ConvertToString(uri: Uri) {
        try {
            val ins: InputStream? = requireActivity().getContentResolver().openInputStream(uri)
            val bytes = getBytes(ins!!)
            documentName = Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("error", "onActivityResult: $e")
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }


}