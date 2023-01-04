package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.lowmileagevehicles

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.LowMileageVehicleAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.solddestroyedstolenvehicle.SoldDestroyedorStolenVehicleFragmentDirections
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_low_mileage_vehicle.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class LowMileageVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel: FillingViewModel
    var mLowMileageVehicleAdapter: LowMileageVehicleAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(viewModelStore,defaultViewModelProviderFactory).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetLowMileageByFilingIdResponse.observe(this, Observer {
            mLowMileageVehicleAdapter=LowMileageVehicleAdapter(it){id,weight,requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToAddNewLowMileageVehicleFragment(id,filingId,weight))
                }
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            lowMileageVehicleRecyclerView?.layoutManager = mLayoutManager
            lowMileageVehicleRecyclerView?.adapter = mLowMileageVehicleAdapter
        })

        mFillingViewModel.mDeleteLowMileageByIdResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getLowMileageByFilingId(filingId)
            }else{
                showToast(it.message)
            }

            customDialog?.dismiss()
        })


        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290")){
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToFormSummaryFragment(filingId))

                    }

                }
            }else if (it.filingInfo.formType.equals("8849S6")){

                findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToFormSummaryFragment(filingId))

            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponseBack.observe(this, Observer {

            findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToTaxableVehicleInformation(filingId,it.filingInfo.formType))

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
        return inflater.inflate(R.layout.fragment_low_mileage_vehicle, container, false)
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


        mFillingViewModel.getLowMileageByFilingId(filingId)

        lowMileageVehicleAddNewVechicle.setOnClickListener {
            findNavController().navigate(LowMileageVehicleFragmentDirections.actionLowMileageVehicleFragmentToAddNewLowMileageVehicleFragment("",filingId,""))
        }

        lowMileageVehicleNext.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)

        }

        lowMileageVehiclePrevious.setOnClickListener {
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
            mFillingViewModel.deleteLowMileageById(businessId,filingId)
        }
    }


}