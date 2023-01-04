package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.home.model.HomeViewModel
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.fragment_home_filing_details_view.*
import kotlinx.android.synthetic.main.fragment_home_filing_details_view.fragmentBack


class HomeFilingDetailsViewFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    var filingId=""
    override fun initViewModel() {
        homeViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            HomeViewModel::class.java
        )
        setViewModel(homeViewModel)

        homeViewModel.mGetIRSRejectionDetailsResponse.observe(this, Observer {
            if (it.irsRejectArray.isEmpty()){

            }else{
                showConsentDisclosure(it.irsRejectArray.get(0))
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
        val view = inflater.inflate(R.layout.fragment_home_filing_details_view, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }
        arguments?.let {

             filingId = it.getString("filingId").toString()
            val businessname = it.getString("businessName")
            val formType = it.getString("formtype")
            val invoiceNo = it.getString("invoiceNo")
            val firstUsedMonth = it.getString("firstUsedMonth")
            val createdDate = it.getString("createdDate")
            val status = it.getString("status")
            val invoiceStatus = it.getString("invoiceStatus")

            println("invoiceNo "+invoiceNo)
            filterInvoiceNumber?.isFocusable = false
            filterInvoiceNumber?.isClickable = true

            filterFirstUsedMonth?.isFocusable = false
            filterFirstUsedMonth?.isClickable = true

            filterCreatedDate?.isFocusable = false
            filterCreatedDate?.isClickable = true

            filterStatus?.isFocusable = false
            filterStatus?.isClickable = true

            filterInvoiceStatus?.isFocusable = false
            filterInvoiceStatus?.isClickable = true

            if (businessname != null && businessname.isNotEmpty()) {
                businessName.setText(businessname)
                formtype.setText(formType)
                filterInvoiceNumber.setText(invoiceNo)
                filterFirstUsedMonth.setText(firstUsedMonth)
                filterCreatedDate.setText(createdDate)
                filterStatus.setText(status)
                filterInvoiceStatus.setText(invoiceStatus)
            }
            fragmentBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        filterStatus.setOnClickListener {
            if (filterStatus.text.toString().equals("IRS_REJECTED")){
                if (isOnline()) {
                    homeViewModel.getIRSRejectionDetails(filingId)
                }else{
                    showToast(getString(R.string.internet_required))
                }

            }
        }


    }

    private fun showConsentDisclosure(i:String) {
        val dialogView = layoutInflater.inflate(R.layout.irs_rejected_reasonlayoutview, null)
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

        val irs_rejectReason = dialogView.findViewById<TextView>(R.id.irs_rejectReason)
        val close = dialogView.findViewById<ImageView>(R.id.close)

        irs_rejectReason.setText(i)

        close.setOnClickListener {

            customDialog.dismiss()
        }
    }

}