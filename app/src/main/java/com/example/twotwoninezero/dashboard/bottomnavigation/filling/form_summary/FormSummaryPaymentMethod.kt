package com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import kotlinx.android.synthetic.main.fragment_form_summary_payment_method.*

class FormSummaryPaymentMethod : BaseFragment() {
    var filingId:String=""
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {


           /* if (it.irsPayment.equals("{}")){

            }*/
                if (it.irsPayment!!.paymentMode.isNullOrEmpty()){

                    paymentMethodDirectDebit.visibility=View.GONE
                }else{
                    paymentMethodTitle.setText(it.irsPayment.paymentMode)
                    if (it.irsPayment.paymentMode.equals("Direct debit")){
                        paymentMethodDirectDebit.visibility=View.VISIBLE
                        PaymentMethodBankName.setText(it.irsPayment.bankName)
                        PaymentMethodAccountType.setText(it.irsPayment.acctType)
                        PaymentMethodAccountNumber.setText(it.irsPayment.retypebankAccNo)
                        PaymentMethodRoutingNumber.setText(it.irsPayment.routingTransitNo)
                    }else{
                        paymentMethodDirectDebit.visibility=View.GONE
                    }
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
        return inflater.inflate(R.layout.fragment_form_summary_payment_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        arguments?.let{
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }

        PaymentMethodEdit.setOnClickListener {
           findNavController().navigate(FormSummaryPaymentMethodDirections.actionFormSummaryPaymentMethodToIRSPaymentOptionsFragment(filingId))
        }

    }


}