package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.PriorYearSuspended

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.PriorYearSuspendedAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ReportingSuspendedExemptVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended.ReportingSuspendedExemptVehicleFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.lowmileagevehicles.LowMileageVehicleFragmentDirections
import kotlinx.android.synthetic.main.fragment_prior_year_suspended_exempt_vehicles.*
import kotlinx.android.synthetic.main.fragment_reporting_suspended_exempt_vehicle.*
import kotlinx.android.synthetic.main.progress_bar_view.*
import kotlin.math.max


class PriorYearSuspendedExemptVehiclesFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mPriorYearSuspendedAdapter: PriorYearSuspendedAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)



        mFillingViewModel.mGetPriorSuspendedByFilingIdResponse.observe(this, Observer {
            println("itttttttttttttttt    "+it.size)
            mPriorYearSuspendedAdapter =
                PriorYearSuspendedAdapter(it){ id,requestType->
                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(id, filingId)
                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(
                            PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToAddNewPriorYearSuspendedExemptVehicles(id,filingId)
                        )
                    }

                }
            val mLayoutManager = LinearLayoutManager(requireContext())
            PriorYearSuspendedRecyclerView?.layoutManager = mLayoutManager
            PriorYearSuspendedRecyclerView?.adapter = mPriorYearSuspendedAdapter

        })

        mFillingViewModel.mDeletePriorSuspendedResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getPriorSuspendedByFilingId(filingId)
            }else{
                showToast(it.message)
            }

            customDialog?.dismiss()
        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290")){
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToFormSummaryFragment(filingId))

                    }

                }
            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponseBack.observe(this, Observer {

            findNavController().navigate(PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToTaxableVehicleInformation(filingId,it.filingInfo.formType))

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
            R.layout.fragment_prior_year_suspended_exempt_vehicles,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            filingId= it.getString("filingId").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")


        mFillingViewModel.getPriorSuspendedByFilingId(filingId)

        PriorYearSuspendedAddNewVechicle.setOnClickListener {
                findNavController().navigate(
                    PriorYearSuspendedExemptVehiclesFragmentDirections.actionPriorYearSuspendedExemptVehiclesFragmentToAddNewPriorYearSuspendedExemptVehicles("",filingId))
        }

        PriorYearSuspendedNext.setOnClickListener {

            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
        }

        PriorYearSuspendedCancel.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponseForBack(filingId)

        }

    }



    private fun deleteOrReactiveFilingId(id: String, filingId: String) {
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
            mFillingViewModel.deletePriorSuspendedById(id,filingId)
        }
    }



}