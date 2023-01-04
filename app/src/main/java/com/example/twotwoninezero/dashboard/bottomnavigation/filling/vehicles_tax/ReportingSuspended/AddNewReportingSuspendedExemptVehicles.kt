package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.service.SaveCurrentSuspendRequest
import com.example.twotwoninezero.service.UpdateCurrentSuspendRequest
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_reporting_suspended_exempt_vehicles.*


class AddNewReportingSuspendedExemptVehicles : BaseFragment() {
    var reportingsuspendedLogging="N"
    var reportingsuspendedAgriculture="N"
    private lateinit var mFillingViewModel : FillingViewModel
    var id=""
    var filingId=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mSaveCurrentSuspendResponse.observe(this, Observer {
          if (it.code==200){
              showToast(it.message)
              requireActivity().onBackPressed()
          }else{
              showToast(it.message)
          }
        })

        mFillingViewModel.mUpdateCurrentSuspendResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mEditGetCurrentSuspendedByIdResponse.observe(this, Observer {

            addnewreportingsuspendedVin.setText(it.vin)
            addnewreportingsuspendedAgriculture.isChecked=it.isAgriculture.equals("Y")
            addnewreportingsuspendedLogging.isChecked=it.isLogging.equals("Y")
            reportingsuspendedLogging=it.isLogging
            reportingsuspendedAgriculture=it.isAgriculture
            addnewreportingsuspendedSubmit.setText("Update")
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
            R.layout.fragment_add_new_reporting_suspended_exempt_vehicles,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        addnewreportingsuspendedCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let {

             filingId = it.getString("filingId").toString()

            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.editGetCurrentSuspendedById(id, filingId.toString())
                addnewreportingsuspendedSubmit.setText("Update")
            }

        }

        addnewreportingsuspendedAgriculture.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                reportingsuspendedAgriculture="Y"
            }else{
                reportingsuspendedAgriculture="N"
            }

        }

        addnewreportingsuspendedLogging.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                reportingsuspendedLogging="Y"
            }else{
                reportingsuspendedLogging="N"
            }
        }

        addnewreportingsuspendedSubmit.setOnClickListener {
            if (addnewreportingsuspendedVin.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            } else if (addnewreportingsuspendedVin.text.toString().length<17){
                showToast("VIN must be at least 17 characters long.")
            }else{
                if (addnewreportingsuspendedSubmit.text.equals("Save ")){
                    val i = SaveCurrentSuspendRequest(filingId,reportingsuspendedAgriculture,reportingsuspendedLogging,addnewreportingsuspendedVin.text.toString())
                    mFillingViewModel.saveCurrentSuspended(filingId,i)
                }else{

                    val i = UpdateCurrentSuspendRequest(reportingsuspendedAgriculture,reportingsuspendedLogging,addnewreportingsuspendedVin.text.toString())
                    mFillingViewModel.updateCurrentSuspended(id,filingId,i)

                }

            }
        }
    }



}