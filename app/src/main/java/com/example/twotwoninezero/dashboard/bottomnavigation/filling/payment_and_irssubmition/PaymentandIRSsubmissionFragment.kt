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
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.FilterCategoryAdapter
import com.example.twotwoninezero.service.ApplyCouponRequest
import com.example.twotwoninezero.service.UpdateConsentDisclosureRequest
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.fragment_paymentand_i_r_ssubmission.*
import kotlinx.android.synthetic.main.progress_bar_view.*

class PaymentandIRSsubmissionFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    var consentDisclosure:String="0"
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
                  findNavController().navigate(PaymentandIRSsubmissionFragmentDirections.actionPaymentandIRSsubmissionFragmentToIRSPaymentSubmissionFeePaymentOptionFragment(filingId))
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
        return inflater.inflate(R.layout.fragment_paymentand_i_r_ssubmission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.submissionFee(filingId)
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
                val i = UpdateConsentDisclosureRequest(consentDisclosure,irsPaymentOptionCouponCode.text.toString())
                mFillingViewModel.updateConsentDisclosure(filingId,i)

            }
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