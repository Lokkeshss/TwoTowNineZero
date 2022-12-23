package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.vincorrectiontaxablevehicleinformation

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.VinCorrectionTaxableVehicleAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import kotlinx.android.synthetic.main.fragment_taxable_vehicle_information2.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class VinCorrectionTaxableVehicleFragment : BaseFragment() {

    private lateinit var mFillingViewModel : FillingViewModel
    var mVinCorrectionTaxableVehicleAdapter: VinCorrectionTaxableVehicleAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mDeleteVinCorrectionByIdResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getVinCorrectionByFilingId(filingId)
                customDialog?.dismiss()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetVinCorrectionByFilingIdResponse.observe(this, Observer {
            mVinCorrectionTaxableVehicleAdapter=
                VinCorrectionTaxableVehicleAdapter(it){ businessId,Weight,requestType->

                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(businessId, filingId)

                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(VinCorrectionTaxableVehicleFragmentDirections.
                        actionVinCorrectionTaxableVehicleFragmentToAddNewVinCorrectionTaxableVehicleFragment(businessId, filingId, Weight))
                    }
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            vinCorrectionRecyclerView?.layoutManager = mLayoutManager
            vinCorrectionRecyclerView?.adapter = mVinCorrectionTaxableVehicleAdapter
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
        return inflater.inflate(R.layout.fragment_taxable_vehicle_information2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            filingId= it.getString("filingId").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")


        mFillingViewModel.getVinCorrectionByFilingId(filingId)

        vinCorrectionAddNewVechicle.setOnClickListener {
            findNavController().navigate(VinCorrectionTaxableVehicleFragmentDirections.
            actionVinCorrectionTaxableVehicleFragmentToAddNewVinCorrectionTaxableVehicleFragment("",filingId,""))
        }

        vinCorrectionNext.setOnClickListener {
            findNavController().navigate(VinCorrectionTaxableVehicleFragmentDirections.actionVinCorrectionTaxableVehicleFragmentToFormSummaryFragment(filingId))

        }


        vinCorrectionCancel.setOnClickListener {
            findNavController().navigate(VinCorrectionTaxableVehicleFragmentDirections.actionVinCorrectionTaxableVehicleFragmentToTaxYearAndFormFragment("","",filingId))
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
            mFillingViewModel.deleteVinCorrectionById(businessId,filingId)
        }
    }


}