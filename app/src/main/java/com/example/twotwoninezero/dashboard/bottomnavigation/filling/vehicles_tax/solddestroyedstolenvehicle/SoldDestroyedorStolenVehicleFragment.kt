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
import kotlinx.android.synthetic.main.fragment_reporting_suspended_exempt_vehicle.*
import kotlinx.android.synthetic.main.fragment_sold_destroyedor_stolen_vehicle.*


class SoldDestroyedorStolenVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel: FillingViewModel
    var mSoldDestroyedAdapter: SoldDestroyedAdapter?=null
    private var customDialog: AlertDialog?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(viewModelStore,defaultViewModelProviderFactory).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSoldDestroyedByFilingIdResponse.observe(this, Observer {
            mSoldDestroyedAdapter=SoldDestroyedAdapter(it){ weight,id,requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, TaxYearAndFormFragment.filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(
                        SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToAddNewSoldDestroyedorStolenVehicle(weight,id,
                            TaxYearAndFormFragment.filingId
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
                mFillingViewModel.getSoldDestroyedByFilingId(TaxYearAndFormFragment.filingId)
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
        return inflater.inflate(R.layout.fragment_sold_destroyedor_stolen_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFillingViewModel.getSoldDestroyedByFilingId(TaxYearAndFormFragment.filingId)

        soldDestoryedStolenAddNewVechicle.setOnClickListener {
            findNavController().navigate(
                SoldDestroyedorStolenVehicleFragmentDirections.actionSoldDestroyedorStolenVehicleFragmentToAddNewSoldDestroyedorStolenVehicle("","",""))
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