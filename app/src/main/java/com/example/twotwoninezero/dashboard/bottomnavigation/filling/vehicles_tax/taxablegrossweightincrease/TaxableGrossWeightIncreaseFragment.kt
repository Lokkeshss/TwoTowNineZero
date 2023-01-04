package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.taxablegrossweightincrease

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxableGrossWeightAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxableVehicleInfoAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_taxable_gross_weight_increase.*
import kotlinx.android.synthetic.main.fragment_taxable_vehicle_information.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class TaxableGrossWeightIncreaseFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mTaxableGrossWeightAdapter: TaxableGrossWeightAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mDeleteTGWIncreaseByIdResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getTGWIncreaseByFilingId(filingId)
                customDialog?.dismiss()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetTGWIncreaseByFilingIdResponse.observe(this, Observer {
            mTaxableGrossWeightAdapter=
                TaxableGrossWeightAdapter(it){ businessId,previousWeight,changedWeight,requestType->

                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(businessId, filingId)

                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(
                          TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToAddNewTaxableGrossWeightIncreaseFragment(businessId, filingId,previousWeight,changedWeight))
                    }
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            taxableGroxxWeightRecyclerView?.layoutManager = mLayoutManager
            taxableGroxxWeightRecyclerView?.adapter = mTaxableGrossWeightAdapter
        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290A1")){
                if (it.totalDueToIRS.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToFormSummaryFragment(filingId))

                    }

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
        return inflater.inflate(R.layout.fragment_taxable_gross_weight_increase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        arguments?.let {
            filingId= it.getString("filingId").toString()
        }
        progress_bar.progress=33
        progress_text.setText("2 of 6")


        mFillingViewModel.getTGWIncreaseByFilingId(filingId)

        taxableGroxxWeightAddNewVehicle.setOnClickListener {
            findNavController().navigate(
                TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToAddNewTaxableGrossWeightIncreaseFragment("",filingId,"",""))
        }

        taxableGroxxWeightNext.setOnClickListener {

            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)

        }

        taxableGroxxWeightPrevious.setOnClickListener {
            findNavController().navigate(TaxableGrossWeightIncreaseFragmentDirections.actionTaxableGrossWeightIncreaseFragmentToTaxYearAndFormFragment("","",filingId))
        }

    }


    private fun deleteOrReactiveFilingId(businessId: String, filingId: String) {
        val dialogView = layoutInflater.inflate(R.layout.delete_or_activate_by_businessid, null)

        customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)

        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val delete = dialogView.findViewById<TextView>(R.id.delete)
        val deleteText = dialogView.findViewById<TextView>(R.id.textfields)
        val deleteIcon = dialogView.findViewById<ImageView>(R.id.icons)

        cancel.setOnClickListener {

            customDialog?.dismiss()
        }

        delete.setOnClickListener {
            mFillingViewModel.deleteTGWIncreaseById(businessId,filingId)
        }
    }
}