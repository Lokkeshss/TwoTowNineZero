package com.example.twotwoninezero.dashboard.bottomnavigation.filling.isr_confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import kotlinx.android.synthetic.main.fragment_irs_confirmation.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class IrsConfirmationFragment : BaseFragment() {

    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mPaymentOptionMethodResponse.observe(this, Observer {

            //abc(it.paymentMode)

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
        return inflater.inflate(R.layout.fragment_irs_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress_bar.progress=100
        progress_text.setText("6 of 6")


        arguments?.let {
            filingId = it.getString("filingId").toString()
            var formtype = it.getString("formtype").toString()
            var finalreturnvalue = it.getString("finalreturnvalue").toString()
            if (!formtype.isNullOrEmpty()){
                irsPaymentOptionCreditCardMain.visibility=View.GONE
                irsPaymentOptionCreditCardfeeDetails.visibility=View.GONE
            }else{
                irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
                irsPaymentOptionCreditCardfeeDetails.visibility=View.VISIBLE
            }

            if (finalreturnvalue.equals("0")){
                irsPaymentOptionCreditCardMain.visibility=View.GONE
                irsPaymentOptionCreditCardfeeDetails.visibility=View.GONE
            }else{
                irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
                irsPaymentOptionCreditCardfeeDetails.visibility=View.VISIBLE
            }
            //mFillingViewModel.getPaymentOptionByFilingId(filingId)

            transactionID.setText(it.getString("transactionID").toString())
            transactionVoucherNo.setText(it.getString("transactionVoucherNo").toString())
            transactionPaymentStatus.setText(it.getString("transactionPaymentStatus").toString())
            transactionPaidAmount.setText(it.getString("transactionPaidAmount").toString())

        }

        transactionSubmit.setOnClickListener {
            findNavController().navigate(
                IrsConfirmationFragmentDirections.actionIrsConfirmationFragmentToHomeScreenFragment("",
                    "","","","","","","","")
            )
            //requireActivity().finish()
        }


    }

/*    fun abc(paymentMode: String) {
        transactionID.setText(transactionId)
        transactionVoucherNo.setText(refId)
        transactionPaymentStatus.setText(status)
        transactionPaidAmount.setText(amount)
    }*/

}