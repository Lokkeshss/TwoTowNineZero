package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended

import android.os.Bundle
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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ReportingSuspendedExemptVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.fragment_reporting_suspended_exempt_vehicle.*

class ReportingSuspendedExemptVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var mReportingSuspendedExemptVehiclesAdapterAdapter: ReportingSuspendedExemptVehiclesAdapter?=null
    private var customDialog: AlertDialog?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetCurrentSuspendedByFilingIdResponse.observe(this, Observer {
            println("itttttttttttttttt    "+it.size)
            mReportingSuspendedExemptVehiclesAdapterAdapter =
                ReportingSuspendedExemptVehiclesAdapter(it){ id,requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(
                        ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToAddNewReportingSuspendedExemptVehicles(id,filingId)
                    )
                }

            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            reportingsuspendedRecyclerView?.layoutManager = mLayoutManager
            reportingsuspendedRecyclerView?.adapter = mReportingSuspendedExemptVehiclesAdapterAdapter

        })

        mFillingViewModel.mDeleteCurrentSuspendedeById.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.GetCurrentSuspendedByFilingId(TaxYearAndFormFragment.filingId)
            }else{
               showToast(it.message)
            }

            customDialog?.dismiss()
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
            R.layout.fragment_reporting_suspended_exempt_vehicle,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFillingViewModel.GetCurrentSuspendedByFilingId(TaxYearAndFormFragment.filingId)

        reportingsuspendedAddNewVechicle.setOnClickListener {
            findNavController().navigate(
                ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToAddNewReportingSuspendedExemptVehicles("","")
            )
        }

        reportingsuspendedAddFromMyFleet.setOnClickListener {

        }

        reportingsuspendedUploadTaxable.setOnClickListener {

        }


        reportingsuspendedCancel.setOnClickListener {

        }

        reportingsuspendedNext.setOnClickListener {

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
            mFillingViewModel.deleteCurrentSuspendedeById(businessId,filingId)
        }
    }


}