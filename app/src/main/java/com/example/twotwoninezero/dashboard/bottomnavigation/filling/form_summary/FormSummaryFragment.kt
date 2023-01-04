package com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.VehiclesTaxMainMenuDirections
import com.example.twotwoninezero.service.GetSummaryDetailsByFilingIdResponse
import kotlinx.android.synthetic.main.fragment_form_summary.*
import kotlinx.android.synthetic.main.progress_bar_view.*

// if user click edit in main screen stay here
// else do what as flow
class FormSummaryFragment : BaseFragment() {

    private var forLocalUseSummaryDetails: GetSummaryDetailsByFilingIdResponse? = null
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mupdateRejErrorDesc.observe(this, Observer {
            if (it==1){
                mFillingViewModel.validateXML(filingId)
            }else{

            }
        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            forLocalUseSummaryDetails=it

            formSummaryTotalTaxAmount.setText(it.totalTaxAmt)
            formSummaryTotalCreditAmount.setText(it.totalCreditAmt)
            formSummaryTaxduetotheIRS.setText(it.totalDueToIRS)

            if (it.filingInfo.formType.equals("2290")){
                formSummaryTotalTaxAmountTI.visibility=View.VISIBLE
                formSummaryTotalCreditAmountTI.visibility=View.VISIBLE
                formSummaryTaxduetotheIRSTI.visibility=View.VISIBLE

                if (it.taxableVehicle.isEmpty()){
                    formSummaryPaymentScreen.visibility=View.GONE
                }else{
                    if (it.taxableVehicle.size<0){
                        formSummaryPaymentScreen.visibility=View.GONE
                    }else{
                        if (it.totalTaxAmt.toDouble()<it.totalCreditAmt.toDouble()){
                            formSummaryPaymentScreen.visibility=View.GONE
                        }else{
                            formSummaryPaymentScreen.visibility=View.VISIBLE
                        }

                    }
                }

/*
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    formSummaryPaymentScreen.visibility=View.GONE
                }else{

                }*/

            }else if (it.filingInfo.formType.equals("2290A1")){
                formSummaryTotalTaxAmountTI.visibility=View.VISIBLE
                formSummaryTotalCreditAmountTI.visibility=View.GONE
                formSummaryTaxduetotheIRSTI.visibility=View.VISIBLE
                if (it.totalDueToIRS.equals("0.00")&& it.totalTaxAmt.equals("0.00")){
                    formSummaryPaymentScreen.visibility=View.GONE
                }else{
                    formSummaryPaymentScreen.visibility=View.VISIBLE
                }
            }else if (it.filingInfo.formType.equals("2290A2")){
                formSummaryTotalTaxAmountTI.visibility=View.VISIBLE
                formSummaryTotalCreditAmountTI.visibility=View.GONE
                formSummaryTaxduetotheIRSTI.visibility=View.VISIBLE
                if (it.totalDueToIRS.equals("0.00")&& it.totalTaxAmt.equals("0.00")){
                    formSummaryPaymentScreen.visibility=View.GONE
                }else{
                    formSummaryPaymentScreen.visibility=View.VISIBLE
                }

            }else if (it.filingInfo.formType.equals("2290V")){
                formSummaryPaymentScreen.visibility=View.GONE
                formSummaryTotalTaxAmountTI.visibility=View.GONE
                formSummaryTotalCreditAmountTI.visibility=View.GONE
                formSummaryTaxduetotheIRSTI.visibility=View.GONE
            }else if (it.filingInfo.formType.equals("8849S6")){
                formSummaryPaymentScreen.visibility=View.GONE
                formSummaryTotalTaxAmountTI.visibility=View.GONE
                formSummaryTotalCreditAmountTI.visibility=View.VISIBLE
                formSummaryTaxduetotheIRSTI.visibility=View.VISIBLE
            }

            // todo undo this below code later
/*

            if (it.irsPayment!!.paymentMode.isNullOrEmpty()) {
                formSummaryPaymentScreen.visibility=View.VISIBLE
                formSummaryTotalTaxAmountTI.visibility=View.VISIBLE
                formSummaryTotalCreditAmountTI.visibility=View.VISIBLE
                formSummaryTaxduetotheIRSTI.visibility=View.VISIBLE
            }else{
                formSummaryPaymentScreen.visibility=View.VISIBLE
            }
*/


            if (!it.amendement1.isEmpty()){
                if (it.amendment1Total.equals("0.00")){
                    // keep user in this screen it self
                    //for backpress  take user to form page
                }else{
                    // re direct the user to payment screen if irspayment is null and amendement total is not 0
                    // and when user click back button take him to payment page even he selected payment
                }
            }

             if (!it.amendement2.isEmpty()){
                 if (it.amendment2Total.equals("0.00")){
                     // keep user in this screen it self
                     // for backpress take user to form page
                 }else{
                     // re direct the user to payment screen if irspayment is null and amendement total is not 0
                     // and when user click back button take him to payment page even he selected payment
                 }
             }


            if (!it.vinCorrection.isEmpty()){
                // keep user in this screen it self
                // for backpress take user to form page
            }

            if (it.filingInfo.formType.equals("8849S6")){
                if(it.totalCreditAmt.equals("0.00") && it.totalDueToIRS.equals("0.00")){
                    // keep user in this screen it self
                    //  for backpress take user to form page
                }else{
                    // when user click proceed move to our payment gateway page
                    // here their is no government payment
                }
            }

            if (it.filingInfo.formType.equals("2290")){
              if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                // no further move
                  // for back press move to form screen
              }else if (it.totalDueToIRS.equals("0.00") && !it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                // should not allow user to move further and ask user to select 8849s6 else add taxable vehicle
                  // for back press move to form screen
              }else if (it.totalCreditAmt.toDouble() > it.totalTaxAmt.toDouble()){
                // do not preceed to next screen
                  // for back press move to form screen
              }else {
                // all pass move to next screen
              }
            }

            // todo undo this below line based on logic
/*
            if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                formSummaryPaymentScreen.visibility=View.GONE
                formSummarySubmit.isEnabled=false
            }else{
                formSummaryPaymentScreen.visibility=View.VISIBLE
                if (it.filingInfo.formType.equals("2290")||
                    it.filingInfo.formType.equals("2290A1")||
                    it.filingInfo.formType.equals("2290A2")||
                    it.filingInfo.formType.equals("2290V")||
                    it.filingInfo.formType.equals("8849S6")){
                    formSummarySubmit.isEnabled=true
                }else{
                    formSummarySubmit.isEnabled=true
                }
            }
*/



        })


        mFillingViewModel.mValidateXMLResponse.observe(this, Observer {
                if (it.code==200){
                    showToast(it.message)
                    findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToPaymentandIRSsubmissionFragment(filingId))
                }else{
                    showToast(it.messageList!![0].toString())
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
        return inflater.inflate(R.layout.fragment_form_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
             filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }

        progress_bar.progress=66
        progress_text.setText("4 of 6")

        formSummaryBusinessInformation.setOnClickListener {
            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToFormSummaryBusinessInformationFragment(filingId))
        }

        formSummaryVehicleSummary.setOnClickListener {
            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToFormSummaryVehicleSummaryFragment(filingId))
        }

        formSummaryPaymentScreen.setOnClickListener {
            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToFormSummaryPaymentMethod(filingId))
        }

        formSummaryTaxYearInformation.setOnClickListener {
            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToFormSummaryTaxYearInformationFragment(filingId))
        }

        formSummarySubmit.setOnClickListener {

            if (forLocalUseSummaryDetails!=null) {
                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290")) {

                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalCreditAmt.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        if (forLocalUseSummaryDetails!!.filingInfo.finalReturn.equals("1")){
                            mFillingViewModel.updateRejErrorDesc(filingId)
                        }else{
                            showToast("Please check the value")
                        }

                    }else{
                        mFillingViewModel.updateRejErrorDesc(filingId)
                    }
                }else if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290A1")) {
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        if (forLocalUseSummaryDetails!!.filingInfo.finalReturn.equals("1")){
                            mFillingViewModel.updateRejErrorDesc(filingId)
                        }else{
                            showToast("Please check the value")
                        }

                    }else{
                        mFillingViewModel.updateRejErrorDesc(filingId)
                    }
                }else if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290A2")) {
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        if (forLocalUseSummaryDetails!!.filingInfo.finalReturn.equals("1")){
                            mFillingViewModel.updateRejErrorDesc(filingId)
                        }else{
                            showToast("Please check the value")
                        }

                    }else{
                        mFillingViewModel.updateRejErrorDesc(filingId)
                    }
                }else if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290V")) {
                    if (forLocalUseSummaryDetails!!.vinCorrection.isNotEmpty()){
                        mFillingViewModel.updateRejErrorDesc(filingId)
                    }else{
                        showToast("Please check the value")
                    }


                }else if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("8849S6")) {
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalCreditAmt.equals("0.00")) {


                            showToast("Please check the value")


                    }else{
                        mFillingViewModel.updateRejErrorDesc(filingId)
                    }
                }
            }




        }

        formSummaryPrevious.setOnClickListener {



            if (forLocalUseSummaryDetails!=null){
                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") && forLocalUseSummaryDetails?.totalCreditAmt.equals("0.00") && forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")){

                        findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToTaxableVehicleInformation(filingId,forLocalUseSummaryDetails?.filingInfo!!.formType))

                    }else{

                        if (forLocalUseSummaryDetails?.irsPayment!!.paymentMode.isNullOrEmpty()){

                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToTaxableVehicleInformation(filingId,forLocalUseSummaryDetails?.filingInfo!!.formType))

                        }else{
                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToIRSPaymentOptionsFragment(filingId))
                        }

                    }
                }

                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290A1")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") && forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")){

                        findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToTaxableGrossWeightIncreaseFragment(filingId))

                    }else{

                        if (forLocalUseSummaryDetails?.irsPayment!!.paymentMode.isNullOrEmpty()){

                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToTaxableGrossWeightIncreaseFragment(filingId))

                        }else{
                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToIRSPaymentOptionsFragment(filingId))
                        }

                    }
                }

                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290A2")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") && forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")){

                        findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToExceededMileageVehiclesFragment(filingId))
                    }else{

                        if (forLocalUseSummaryDetails?.irsPayment!!.paymentMode.isNullOrEmpty()){

                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToExceededMileageVehiclesFragment(filingId))

                        }else{
                            findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToIRSPaymentOptionsFragment(filingId))
                        }

                    }
                }

                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("2290V")){

                        findNavController().navigate(FormSummaryFragmentDirections.actionFormSummaryFragmentToVinCorrectionTaxableVehicleFragment(filingId))

                }

                if (forLocalUseSummaryDetails?.filingInfo?.formType.equals("8849S6")){


                        findNavController().navigate(
                            FormSummaryFragmentDirections.actionFormSummaryFragmentToTaxableVehicleInformation(
                                filingId,
                                forLocalUseSummaryDetails?.filingInfo!!.formType
                            )
                        )


                }

            }
        }
/*
Based on Form type need to check wheather all document are fulled then only we should pass it to payment screen
* */
    }

}