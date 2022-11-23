package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.creditforanapproval

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.service.SaveCreditOverPaymentRequest
import com.example.twotwoninezero.service.UpdateCreditOverPaymentRequest
import kotlinx.android.synthetic.main.fragment_add_new_credit_for_an_overpayment_of_tax.*
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import java.text.SimpleDateFormat
import java.util.*


class AddNewCreditForAnOverpaymentOfTaxFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel

    val myCalendarToDate = Calendar.getInstance()
    var dateOfTaxPayment:String=""
    var documentName:String=""
    var id:String=""
    var paymentPrice:Int=0
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


        arguments?.let {

            val filingId = it.getString("filingId").toString()

            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.getCreditOverPaymentById(id,filingId)
                creditForAnOverPaymentSubmit.setText("Update")
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

        creditForAnOverPaymentDateOfPayment.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                toDate,
                myCalendarToDate[Calendar.YEAR],
                myCalendarToDate[Calendar.MONTH],
                myCalendarToDate[Calendar.DAY_OF_MONTH]
            ).show()
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

        creditForAnOverPaymentCancel.setOnClickListener {

        }
    }

    private fun updateLabelToDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        creditForAnOverPaymentDateOfPayment?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-MM-dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        dateOfTaxPayment=dateFormatS.format(myCalendarToDate.time)
        // filterToDateValue=dateFormatS.format(myCalendarFromDate.time)
    }

}