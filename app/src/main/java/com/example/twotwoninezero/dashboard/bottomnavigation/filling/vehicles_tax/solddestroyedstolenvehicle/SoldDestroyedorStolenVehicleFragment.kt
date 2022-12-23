package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.solddestroyedstolenvehicle

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ReportingSuspendedExemptVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.SoldDestroyedAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended.ReportingSuspendedExemptVehicleFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.vincorrectiontaxablevehicleinformation.VinCorrectionTaxableVehicleFragmentDirections
import kotlinx.android.synthetic.main.fragment_reporting_suspended_exempt_vehicle.*
import kotlinx.android.synthetic.main.fragment_sold_destroyedor_stolen_vehicle.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class SoldDestroyedorStolenVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel: FillingViewModel
    var mSoldDestroyedAdapter: SoldDestroyedAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(viewModelStore,defaultViewModelProviderFactory).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSoldDestroyedByFilingIdResponse.observe(this, Observer {
            mSoldDestroyedAdapter=SoldDestroyedAdapter(it){ weight,id,requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(
                        SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToAddNewSoldDestroyedorStolenVehicle(weight,id,
                            filingId
                        )
                    )
                }
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            soldDestoryedStolenRecyclerView?.layoutManager = mLayoutManager
            soldDestoryedStolenRecyclerView?.adapter = mSoldDestroyedAdapter
        })

        mFillingViewModel.mDeleteSoldDestroyedResponse.observe(this, Observer {

            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getSoldDestroyedByFilingId(filingId)
            }else{
                showToast(it.message)
            }
            customDialog?.dismiss()
        })


        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290")){
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToFormSummaryFragment(filingId))

                    }

                }
            }else if (it.filingInfo.formType.equals("8849S6")){

                findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToFormSummaryFragment(filingId))

            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponseBack.observe(this, Observer {

            findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToTaxableVehicleInformation(filingId,it.filingInfo.formType))

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
        return inflater.inflate(R.layout.fragment_sold_destroyedor_stolen_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            filingId= it.getString("filingId").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")


        mFillingViewModel.getSoldDestroyedByFilingId(filingId)

        soldDestoryedStolenAddNewVechicle.setOnClickListener {
            findNavController().navigate(
                SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToAddNewSoldDestroyedorStolenVehicle("","",filingId))
        }

        soldDestoryedStolenNext.setOnClickListener {

            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
        }

        soldDestoryedStolenCancel.setOnClickListener {
           // findNavController().navigate(SoldDestroyedorStolenVehicleFragmentDirections.ac)
            mFillingViewModel.GetSummaryDetailsByFilingIdResponseForBack(filingId)
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
            mFillingViewModel.deleteSoldDestroyedById(businessId,filingId)
        }
    }


}