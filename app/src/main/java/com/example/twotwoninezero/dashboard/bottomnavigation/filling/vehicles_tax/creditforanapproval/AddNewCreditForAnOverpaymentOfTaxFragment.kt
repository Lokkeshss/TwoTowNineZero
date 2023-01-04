package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.creditforanapproval

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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.getOnlyDateFromDate
import com.example.twotwoninezero.common.getOnlyMonthFromDate
import com.example.twotwoninezero.common.getOnlyyearFromDate
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.service.SaveCreditOverPaymentRequest
import com.example.twotwoninezero.service.UpdateCreditOverPaymentRequest
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_credit_for_an_overpayment_of_tax.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*



class AddNewCreditForAnOverpaymentOfTaxFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel

    val myCalendarToDate = Calendar.getInstance()
    var dateOfTaxPayment:String=""
    var documentName:String=""
    var id:String=""
    var filingId:String=""
    var paymentPrice:Int=0

    var minDate:String=""
    var maxDate:String=""
    var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetCreditOverPaymentByIdResponse.observe(this, androidx.lifecycle.Observer {
            creditForAnOverPaymentVIN.setText(it.vin)
            creditForAnOverPaymentDateOfPayment.setText(it.paymentDate)
            dateOfTaxPayment=it.paymentDate
            documentName=it.documentName
            creditForAnOverpaymentPrice.setText(it.amountOfClaim)
            creditForAnOverpaymentVinCorrection.setText(it.explanation)
        })

        mFillingViewModel.mSaveCreditOverPaymentResponse.observe(this, androidx.lifecycle.Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mUpdateCreditOverPaymentResponse.observe(this, androidx.lifecycle.Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetCreditOverPaymentDateResponse.observe(this, androidx.lifecycle.Observer {
            minDate=it.minDate
            maxDate=it.maxDate
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
            R.layout.fragment_add_new_credit_for_an_overpayment_of_tax,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        creditForAnOverPaymentCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let {
            filingId= it.getString("filingId").toString()
           // val filingId = it.getString("filingId").toString()

            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.getCreditOverPaymentById(id,filingId)
                creditForAnOverPaymentSubmit.setText("Update")
            }

            mFillingViewModel.getCreditOverPaymentDate(filingId)

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

        creditForAnOverPaymentDateOfPayment.setOnClickListener {
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

        creditForAnOverpaymentminus.setOnClickListener {
            if (!creditForAnOverpaymentPrice.text.toString().equals("0")){
                paymentPrice=creditForAnOverpaymentPrice.text.toString().toInt()-1
                creditForAnOverpaymentPrice.setText(paymentPrice.toString())
            }
        }

        creditForAnOverpaymentpluse.setOnClickListener {

                paymentPrice=creditForAnOverpaymentPrice.text.toString().toInt()+1
                creditForAnOverpaymentPrice.setText(paymentPrice.toString())

        }

        creditForAnOverPaymentSubmit.setOnClickListener {

            if (creditForAnOverpaymentPrice.text.toString().isNullOrEmpty()){
                showToast("Please enter the clime price")
            }else if (creditForAnOverpaymentVinCorrection.text.toString().isNullOrEmpty()){
                showToast("Please enter explanation for refund")
            }else if (dateOfTaxPayment.isNullOrEmpty()){
                showToast("Select Date of tax payment")
            }else if (creditForAnOverPaymentVIN.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            }else if (creditForAnOverPaymentVIN.text.toString().length<17){
                showToast("VIN must be at least 17 characters long")
            }else{
                if (creditForAnOverPaymentSubmit.text.toString().equals("Save ")){
                    val i = SaveCreditOverPaymentRequest(creditForAnOverpaymentPrice.text.toString(),documentName,
                        creditForAnOverpaymentVinCorrection.text.toString(), filingId,dateOfTaxPayment,creditForAnOverPaymentVIN.text.toString())

                    mFillingViewModel.saveCreditOverPayment(filingId,i )
                }else{

                    val i = UpdateCreditOverPaymentRequest(creditForAnOverpaymentPrice.text.toString(),documentName,
                        creditForAnOverpaymentVinCorrection.text.toString(), filingId,dateOfTaxPayment,creditForAnOverPaymentVIN.text.toString())

                    mFillingViewModel.updateCreditOverPayment(id,filingId,i )
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

        creditForAnOverpaymentuploadDocument.setOnClickListener {
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
        creditForAnOverPaymentDateOfPayment?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-MM-dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        dateOfTaxPayment=dateFormatS.format(myCalendarToDate.time)
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