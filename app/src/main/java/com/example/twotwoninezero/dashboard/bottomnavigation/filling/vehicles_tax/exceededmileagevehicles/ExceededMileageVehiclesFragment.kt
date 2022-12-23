package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.exceededmileagevehicles

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ExceededMileageVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import kotlinx.android.synthetic.main.fragment_exceeded_mileage_vehicles.*
import kotlinx.android.synthetic.main.fragment_taxable_gross_weight_increase.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class ExceededMileageVehiclesFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mExceededMileageVehiclesAdapter: ExceededMileageVehiclesAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mDeleteExceededMileageByIdResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getExceededMileageByFilingId(filingId)
                customDialog?.dismiss()
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mGetExceededMileageByFilingIdResponse.observe(this, Observer {
            mExceededMileageVehiclesAdapter=
                ExceededMileageVehiclesAdapter(it){ businessId,Weight,requestType->

                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(businessId, filingId)

                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(
                           ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToAddNewExceededMileageVehiclesFragment(businessId, Weight, filingId))
                    }
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            exceededMileageVehiclesRecyclerView?.layoutManager = mLayoutManager
            exceededMileageVehiclesRecyclerView?.adapter = mExceededMileageVehiclesAdapter
        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290A2")){
                if (it.totalDueToIRS.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToFormSummaryFragment(filingId))

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
        return inflater.inflate(R.layout.fragment_exceeded_mileage_vehicles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            filingId= it.getString("filingId").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")


        mFillingViewModel.getExceededMileageByFilingId(filingId)

        exceededMileageVehiclesAddNew.setOnClickListener {
           findNavController().navigate(ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToAddNewExceededMileageVehiclesFragment("","",filingId))
        }


        exceededMileageVehiclesNext.setOnClickListener {

            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
        }

        exceededMileageVehiclesPrevious.setOnClickListener {

            findNavController().navigate(ExceededMileageVehiclesFragmentDirections.actionExceededMileageVehiclesFragmentToTaxYearAndFormFragment("","",filingId))
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
            mFillingViewModel.deleteExceededMileageById(businessId,filingId)
        }
    }

}