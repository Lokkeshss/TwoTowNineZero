package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxableVehicleInfoAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import kotlinx.android.synthetic.main.fragment_taxable_vehicle_information.*


class TaxableVehicleInformationFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mTaxableVehicleInfoAdapter: TaxableVehicleInfoAdapter?=null
    private var customDialog: AlertDialog?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetTaxableVehicleResponse.observe(this, Observer {
            mTaxableVehicleInfoAdapter=
                TaxableVehicleInfoAdapter(it){ businessId,weight,weightCategory,requestType->

                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(businessId, filingId)
                    mFillingViewModel.deleteTaxableVehicleById(businessId, filingId)
                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(
                            TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToAddNewVehicalFragment(weight,weightCategory,businessId))
                    }
                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            taxableVehicleRecyclerView?.layoutManager = mLayoutManager
            taxableVehicleRecyclerView?.adapter = mTaxableVehicleInfoAdapter
        })

        mFillingViewModel.mDeleteTaxableVehicleResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getTaxableVehicleByFilingId(filingId)
            }else{
                showToast(it.message)
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
        val view =  inflater.inflate(R.layout.fragment_taxable_vehicle_information, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mFillingViewModel.getTaxableVehicleByFilingId(TaxYearAndFormFragment.filingId)

        taxableVehicleAddNewVechicle.setOnClickListener {
                findNavController().navigate(
                    TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToAddNewVehicalFragment("","","")
                )
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
                mFillingViewModel.deleteTaxableVehicleById(businessId,filingId)
        }
    }

}