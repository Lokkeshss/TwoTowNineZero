package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.fragment_home_filing_details_view.*
import kotlinx.android.synthetic.main.fragment_home_filing_details_view.fragmentBack


class HomeFilingDetailsViewFragment : BaseFragment() {
    override fun initViewModel() {

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
        arguments?.let {

            val businessname = it.getString("businessName")
            val formType = it.getString("formtype")
            val invoiceNo = it.getString("invoiceNo")
            val firstUsedMonth = it.getString("firstUsedMonth")
            val createdDate = it.getString("createdDate")
            val status = it.getString("status")
            val invoiceStatus = it.getString("invoiceStatus")

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
    }

}