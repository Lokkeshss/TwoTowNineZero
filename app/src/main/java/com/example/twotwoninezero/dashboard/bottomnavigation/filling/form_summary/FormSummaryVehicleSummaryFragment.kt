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
import com.example.twotwoninezero.service.GetSummaryDetailsByFilingIdResponse
import kotlinx.android.synthetic.main.fragment_form_summary_tax_year_information.*
import kotlinx.android.synthetic.main.fragment_form_summary_vehicle_summary.*


class FormSummaryVehicleSummaryFragment : BaseFragment() {

    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            displayVehicleSummary(it)

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
        return inflater.inflate(R.layout.fragment_form_summary_vehicle_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        summaryBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let{
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }


        TaxableVehicle_Edit.setOnClickListener {

        findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToTaxableVehicleInformationFragment(filingId))
        }
        currentsuspendedvehicle_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToReportingSuspendedExemptVehicleFragment(filingId))
        }
        Prioryearsuspended_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToPriorYearSuspendedExemptVehiclesFragment(filingId))
        }
        SoldDestroyedCredits_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToSoldDestroyedorStolenVehicleFragment(filingId))
        }
        LowMileageCredits_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToLowMileageVehicleFragment(filingId))
        }
        TaxableGrossWeight_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToTaxableGrossWeightIncreaseFragment(filingId))
        }
        exceededMilage_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToExceededMileageVehiclesFragment(filingId))
        }
        vinCorrection_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToVinCorrectionTaxableVehicleFragment(filingId))
        }

        soldDestroyed_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToSoldDestroyedorStolenVehicleFragment(filingId))
        }

        lowMileageCredits_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToLowMileageVehicleFragment(filingId))
        }

        creditForOverpayment_Edit.setOnClickListener {
            findNavController().navigate(FormSummaryVehicleSummaryFragmentDirections.actionFormSummaryVehicleSummaryFragmentToCreditForAnOverpaymentOfTaxFragment(filingId))
        }

    }

    private fun displayVehicleSummary(it: GetSummaryDetailsByFilingIdResponse?) {


        if (it != null) {
            if (it.filingInfo.formType.equals("2290")){
                vehicleSummaryFormTypeLL.visibility=View.VISIBLE
                vehicleSummaryAmendementOneTypeLL.visibility=View.GONE
                vehicleSummaryAmendementTwoTypeLL.visibility=View.GONE
                vehicleSummaryVinCorrectionTypeLL.visibility=View.GONE
                vehicleSummaryFormeightTypeLL.visibility=View.GONE
            }else if (it.filingInfo.formType.equals("2290A1")){
                vehicleSummaryFormTypeLL.visibility=View.GONE
                vehicleSummaryAmendementOneTypeLL.visibility=View.VISIBLE
                vehicleSummaryAmendementTwoTypeLL.visibility=View.GONE
                vehicleSummaryVinCorrectionTypeLL.visibility=View.GONE
                vehicleSummaryFormeightTypeLL.visibility=View.GONE
            }else if (it.filingInfo.formType.equals("2290A2")){
                vehicleSummaryFormTypeLL.visibility=View.GONE
                vehicleSummaryAmendementOneTypeLL.visibility=View.GONE
                vehicleSummaryAmendementTwoTypeLL.visibility=View.VISIBLE
                vehicleSummaryVinCorrectionTypeLL.visibility=View.GONE
                vehicleSummaryFormeightTypeLL.visibility=View.GONE
            }else if (it.filingInfo.formType.equals("2290V")){
                vehicleSummaryFormTypeLL.visibility=View.GONE
                vehicleSummaryAmendementOneTypeLL.visibility=View.GONE
                vehicleSummaryAmendementTwoTypeLL.visibility=View.GONE
                vehicleSummaryVinCorrectionTypeLL.visibility=View.VISIBLE
                vehicleSummaryFormeightTypeLL.visibility=View.GONE
            }else if (it.filingInfo.formType.equals("8849S6")){
                vehicleSummaryFormTypeLL.visibility=View.GONE
                vehicleSummaryAmendementOneTypeLL.visibility=View.GONE
                vehicleSummaryAmendementTwoTypeLL.visibility=View.GONE
                vehicleSummaryVinCorrectionTypeLL.visibility=View.GONE
                vehicleSummaryFormeightTypeLL.visibility=View.VISIBLE
            }
        }

        /*form 2290*/
        if (it != null) {
            if (it.taxableVehicle.isNotEmpty()){
                TaxableVehicle_NoofVehicle.setText(it.taxableVehicle.size.toString())
                TaxableVehicle_TotalTaxAmount.setText(it.taxableTotal)
            }

            if (it.currentSuspended.isNotEmpty()){
                currentsuspendedvehicle_NoofVehicle.setText(it.currentSuspended.size.toString())
            }

            if (it.priorSuspended.isNotEmpty()){
                Prioryearsuspended_NoofVehicle.setText(it.priorSuspended.size.toString())
            }

            if (it.soldVehicle.isNotEmpty()){
                SoldDestroyedCredits_NoofVehicle.setText(it.soldVehicle.size.toString())
                SoldDestroyedCredits_TotalTaxAmount.setText(it.soldTotal)
            }

            if (it.lowMileage.isNotEmpty()){
                LowMileageCredits_NoofVehicle.setText(it.lowMileage.size.toString())
                LowMileageCredits_TotalTaxAmount.setText(it.mileageTotal)
            }

        }
        /*Amend one*/
        if (it != null) {
            if (it.amendement1.isNotEmpty()){
                TaxableGrossWeight_NoofVehicle.setText(it.amendement1.size.toString())
                TaxableGrossWeight_TotalTaxAmount.setText(it.amendment1Total)
            }
        }
        /*Amend two*/
        if (it != null) {
            if (it.amendement2.isNotEmpty()){
                exceededMilage_NoofVehicle.setText(it.amendement2.size.toString())
                exceededMilage_TotalTaxAmount.setText(it.amendment2Total)
            }
        }
        /*vin correction*/
        if (it != null) {
            if (it.vinCorrection.isNotEmpty()){
                vinCorrection_NoofVehicle.setText(it.vinCorrection.size.toString())
                vinCorrection_TotalTaxAmount.setText("")
            }
        }
        /*form88*/
        if (it != null) {
            if (it.soldVehicle.isNotEmpty()){
                soldDestroyed_NoofVehicle.setText(it.soldVehicle.size.toString())
                soldDestroyed_TotalTaxAmount.setText(it.soldTotal)
            }
            if (it.lowMileage.isNotEmpty()){
                lowMileageCredits_NoofVehicle.setText(it.lowMileage.size.toString())
                lowMileageCredits_TotalTaxAmount.setText(it.mileageTotal)
            }
            if (it.creditOverPayment.isNotEmpty()){
                creditForOverpayment_NoofVehicle.setText(it.creditOverPayment.size.toString())
                creditForOverpayment_TotalTaxAmount.setText(it.creditTotal)
            }
        }
    }
}