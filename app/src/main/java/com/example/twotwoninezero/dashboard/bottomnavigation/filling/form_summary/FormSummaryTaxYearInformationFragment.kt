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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import kotlinx.android.synthetic.main.fragment_form_summary_business_information.*
import kotlinx.android.synthetic.main.fragment_form_summary_tax_year_information.*


class FormSummaryTaxYearInformationFragment : BaseFragment() {

    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {
            formSummarytaxyearInfoFormType.setText(it.filingInfo.formTypeName)
            formSummarytaxyearTaxYear.setText(it.filingInfo.displayTaxYr)
            formSummaryFirstUsedMonth.setText(it.filingInfo.filingMonth)
            if (it.filingInfo.amendedMonth.isNullOrEmpty()){
                formSummaryAmendmentMonthTI.visibility=View.GONE
                formSummaryAmendmentMonth.setText("")
            }else{
                formSummaryAmendmentMonthTI.visibility=View.VISIBLE
                formSummaryAmendmentMonth.setText(it.filingInfo.amendedMonth)
            }

            if (it.filingInfo.taxYearEndMonth.isNullOrEmpty()){
                formSummaryMonthIncomeTaxTI.visibility=View.GONE
                formSummaryMonthIncomeTax.setText("")
            }else{
                formSummaryMonthIncomeTaxTI.visibility=View.VISIBLE
                formSummaryMonthIncomeTax.setText(it.filingInfo.taxYearEndMonth)
            }

            if (it.filingInfo.formType.equals("8849S6")){
                formSummarytaxyearTaxYearTI.visibility=View.GONE
                formSummaryFirstUsedMonthTI.visibility=View.GONE
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
            R.layout.fragment_form_summary_tax_year_information,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taxyearBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let{
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }

        taxYearInformationEdit.setOnClickListener {
            findNavController().navigate(FormSummaryTaxYearInformationFragmentDirections.actionFormSummaryTaxYearInformationFragmentToTaxYearAndFormFragment("","",filingId)
            )
        }

    }

}