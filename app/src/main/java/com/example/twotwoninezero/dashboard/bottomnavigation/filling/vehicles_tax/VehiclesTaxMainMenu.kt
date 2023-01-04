package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.progress_bar_view.*
import kotlinx.android.synthetic.main.vehicles_tax_main_menu.*

class VehiclesTaxMainMenu : BaseFragment() {
    var filingId:String=""
    var formType:String=""

    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290")){
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){

                    findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))

                }else{

                    if (it.totalDueToIRS.equals("0.00") && !it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                        findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))
                    }else{
                        if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToIRSPaymentOptionsFragment(filingId))
                        }else{
                            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))

                        }
                    }

                }
            }else if (it.filingInfo.formType.equals("8849S6")){

                findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))

            /*    if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00")){

                    findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToIRSPaymentOptionsFragment(filingId))
                    }else{
                        findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToFormSummaryFragment(filingId))

                    }

                }*/

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
        val view = inflater.inflate(R.layout.vehicles_tax_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }
        arguments?.let {
            filingId= it.getString("filingId").toString()
            formType= it.getString("formType").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")

        taxableVehicleInformationMC.visibility=View.GONE
        reportingsuspendedMC.visibility=View.GONE
        priorYearSuspendedMC.visibility=View.GONE
        soldDestroyedStolenVehicleMC.visibility=View.GONE
        lowMileageVehicleMC.visibility=View.GONE
        creditforoverpaymentMC.visibility=View.GONE

        if (formType.equals("2290")){
            taxableVehicleInformationMC.visibility=View.VISIBLE
            reportingsuspendedMC.visibility=View.VISIBLE
            priorYearSuspendedMC.visibility=View.VISIBLE
            soldDestroyedStolenVehicleMC.visibility=View.VISIBLE
            lowMileageVehicleMC.visibility=View.VISIBLE
            creditforoverpaymentMC.visibility=View.GONE
        }else if (formType.equals("2290A1")){
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToTaxableGrossWeightIncreaseFragment(filingId))
        }else if (formType.equals("2290A2")){
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToExceededMileageVehiclesFragment(filingId))
        }else if (formType.equals("2290V")){
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToVinCorrectionTaxableVehicleFragment(filingId)
            )
        }else if (formType.equals("8849S6")){
            taxableVehicleInformationMC.visibility=View.GONE
            reportingsuspendedMC.visibility=View.GONE
            priorYearSuspendedMC.visibility=View.GONE
            soldDestroyedStolenVehicleMC.visibility=View.VISIBLE
            lowMileageVehicleMC.visibility=View.VISIBLE
            creditforoverpaymentMC.visibility=View.VISIBLE
        }

        taxableVehicleInformation.setOnClickListener {
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToTaxableVehicleInformationFragment(filingId)
            )
        }

        reportingsuspended.setOnClickListener {
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToReportingSuspendedExemptVehicleFragment(filingId)
            )
        }
        priorYearSuspended.setOnClickListener {
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToPriorYearSuspendedExemptVehiclesFragment(filingId)
            )
        }

        soldDestroyedStolenVehicle.setOnClickListener {
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToSoldDestroyedorStolenVehicleFragment(filingId))
        }

        lowMileageVehicle.setOnClickListener {
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToLowMileageVehicleFragment(filingId))
        }

        creditforoverpayment.setOnClickListener {
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToCreditForAnOverpaymentOfTaxFragment(filingId))
        }

        vehiclesTaxMainMenuSubmit.setOnClickListener {

            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)

        }

        vehiclesTaxMainMenuCancel.setOnClickListener {
            findNavController().navigate(VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToTaxYearAndFormFragment("","",filingId))
        }

    }

}