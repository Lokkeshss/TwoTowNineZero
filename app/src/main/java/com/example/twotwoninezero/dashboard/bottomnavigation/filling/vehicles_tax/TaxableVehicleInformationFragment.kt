package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.business.model.BusinessViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.FirstUsedMonthAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxableVehicleInfoAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import kotlinx.android.synthetic.main.fragment_taxable_vehicle_information.*
import kotlinx.android.synthetic.main.taxyear_and_forms.*


class TaxableVehicleInformationFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var mTaxableVehicleInfoAdapter: TaxableVehicleInfoAdapter?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetTaxableVehicleResponse.observe(this, Observer {
            mTaxableVehicleInfoAdapter=
                TaxableVehicleInfoAdapter(it){ formtype->

                }

            val mLayoutManager = LinearLayoutManager(requireContext())
            taxableVehicleRecyclerView?.layoutManager = mLayoutManager
            taxableVehicleRecyclerView?.adapter = mTaxableVehicleInfoAdapter
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


        mFillingViewModel.getTaxableVehicleByFilingId(filingId)

        taxableVehicleAddNewVechicle.setOnClickListener {
                findNavController().navigate(
                    TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToAddNewVehicalFragment()
                )
        }


    }

}