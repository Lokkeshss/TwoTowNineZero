package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import kotlinx.android.synthetic.main.vehicles_tax_main_menu.*

class VehiclesTaxMainMenu : BaseFragment() {
    override fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.vehicles_tax_main_menu, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taxableVehicleInformation.setOnClickListener {
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToTaxableVehicleInformationFragment()
            )
        }

        reportingsuspended.setOnClickListener {
            findNavController().navigate(
                VehiclesTaxMainMenuDirections.actionTaxableVehicleInformationToReportingSuspendedExemptVehicleFragment()
            )
        }
        priorYearSuspended.setOnClickListener {

        }
        soldDestroyedStolenVehicle.setOnClickListener {

        }
        lowMileageVehicle.setOnClickListener {

        }
    }

}