package com.example.twotwoninezero.dashboard.bottomnavigation.filling.payment_and_irssubmition

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.VehiclesTaxMainMenuDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.FilterCategoryAdapter
import com.example.twotwoninezero.service.ApplyCouponRequest
import com.example.twotwoninezero.service.SaveConsentSubmit
import com.example.twotwoninezero.service.UpdateConsentDisclosureRequest
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.fragment_paymentand_i_r_ssubmission.*
import kotlinx.android.synthetic.main.progress_bar_view.*
import kotlinx.android.synthetic.main.vehicles_tax_main_menu.*

class PaymentandIRSsubmissionFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    var consentDisclosure:String="0"
    var formTypeForRedirect = ""
    var finalreturnvalue = ""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mSubmissionFeeResponse.observe(this, Observer {
            irsPaymentOptionFilingFee.setText("$ "+it.feeAmount.toString())
            irsPaymentOptionProcessingFee.setText("$ "+it.serviceCharge.toString())
            irsPaymentOptionSubTotal.setText("$ "+it.subTotal.toString())
            irsPaymentOptionPaidAmount.setText("$ "+it.paidAmount.toString())
            irsPaymentOptionDiscountAmount.setText("$ "+it.discountAmount.toString())
            irsPaymentOptionDiscountPercent.setText("Discount Amount ("+it.discountPercentage.toInt()+"%)")
            irsPaymentOptionNetAmount.setText("$ "+it.diffAmount.toString())
            if (it.couponStatus!=null){
                if ( it.couponStatus.equals("invalid")){
                    showToast(it.couponStatus+" Coupon Code")
                }else{
                    showToast(it.couponStatus)
                }
            }
        })

        mFillingViewModel.mUpdateConsentDisclosureResponse.observe(this, Observer {
                if (it.code==200){
                  /*  if (formTypeForRedirect.equals("2290V")){
                        findNavController().navigate(PaymentandIRSsubmissionFragmentDirections.
                        actionPaymentandIRSsubmissionFragmentToIrsConfirmationFragment("","","","",formTypeForRedirect))
                    }else{
                    }*/

                    findNavController().navigate(PaymentandIRSsubmissionFragmentDirections.actionPaymentandIRSsubmissionFragmentToIRSPaymentSubmissionFeePaymentOptionFragment(filingId))

                }else{
                    showToast(it.message)
                }
        })

        mFillingViewModel.mSaveConsentSubmitResponse.observe(this, Observer {

            if (it.submitStatus.isNullOrEmpty()){

            }
            else{

                findNavController().navigate(PaymentandIRSsubmissionFragmentDirections.
                actionPaymentandIRSsubmissionFragmentToIrsConfirmationFragment("","","","",formTypeForRedirect,finalreturnvalue))

            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            mFillingViewModel.submissionFee(filingId)

            formTypeForRedirect = it.filingInfo.formType
           val forLocalUseSummaryDetails = it

            if (it.filingInfo.finalReturn.equals("1")){

                if (it.filingInfo.formType.equals("2290")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalCreditAmt.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        irsPaymentOptionCreditCardMain.visibility=View.GONE
                        finalreturnvalue="0"
                    }else{
                        irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
                        finalreturnvalue="1"
                    }

                }else if (it.filingInfo.formType.equals("2290A1")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        irsPaymentOptionCreditCardMain.visibility=View.GONE
                        finalreturnvalue="0"
                    }else{
                        irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
                        finalreturnvalue="1"
                    }
                }else if (it.filingInfo.formType.equals("2290A2")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalTaxAmt.equals("0.00")) {

                        irsPaymentOptionCreditCardMain.visibility=View.GONE
                        finalreturnvalue="0"
                    }else{
                        irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
                        finalreturnvalue="1"
                    }
                }else if (it.filingInfo.formType.equals("2290V")){
                    finalreturnvalue="0"
                    irsPaymentOptionCreditCardMain.visibility=View.GONE
                }else if (it.filingInfo.formType.equals("8849S6")){
                    if (forLocalUseSummaryDetails?.totalDueToIRS.equals("0.00") &&
                        forLocalUseSummaryDetails?.totalCreditAmt.equals("0.00")) {

                        irsPaymentOptionCreditCardMain.visibility=View.GONE
                        finalreturnvalue="0"
                    }else{
                        finalreturnvalue="1"
                        irsPaymentOptionCreditCardMain.visibility=View.VISIBLE

                    }
                }
            }else{
                if (it.filingInfo.formType.equals("2290V")){
                    finalreturnvalue="0"
                    irsPaymentOptionCreditCardMain.visibility=View.GONE
                }else{
                    finalreturnvalue="1"
                    irsPaymentOptionCreditCardMain.visibility=View.VISIBLE
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
        return inflater.inflate(R.layout.fragment_paymentand_i_r_ssubmission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }

        progress_bar.progress=82
        progress_text.setText("5 of 6")

        irsPaymentOptionApplyCouponButton.setOnClickListener {
            if (irsPaymentOptionCouponCode.text.toString().isNullOrEmpty()){
                showToast("Enter your Coupon code.")
            }else{
                mFillingViewModel.applyCoupon(filingId, ApplyCouponRequest(irsPaymentOptionCouponCode.text.toString()))

            }
        }

        irsPaymentOptionConsentDisclosure.setOnClickListener {
            showConsentDisclosure()
        }

        irsPaymentOptionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                consentDisclosure="1"
            }else{
                consentDisclosure="0"
            }
        }



        irsPaymentOptionSubmit.setOnClickListener {
            if (consentDisclosure.equals("0")){
                showToast("Please click the Consent for Submission checkbox to continue")
            }else{

                  if (formTypeForRedirect.equals("2290V")){
                      val i = SaveConsentSubmit(consentDisclosure)
                      mFillingViewModel.saveConsentSubmit(filingId,i)
                  }else{
                      if (finalreturnvalue.equals("0")){
                          val i = SaveConsentSubmit(consentDisclosure)
                          mFillingViewModel.saveConsentSubmit(filingId,i)
                      }else{
                          val i = UpdateConsentDisclosureRequest(consentDisclosure,irsPaymentOptionCouponCode.text.toString())
                          mFillingViewModel.updateConsentDisclosure(filingId,i)
                      }

                  }


            }
        }

        irsPaymentOptionCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun showConsentDisclosure() {
        val dialogView = layoutInflater.inflate(R.layout.irs_paymentdisclosure_layoutview, null)
        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val window: Window = customDialog.getWindow()!!
        val wlp: WindowManager.LayoutParams = window.getAttributes()

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.setAttributes(wlp)
        customDialog.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        customDialog.setCancelable(false)

        val close = dialogView.findViewById<ImageView>(R.id.close)

        close.setOnClickListener {

            customDialog.dismiss()
        }
    }

}