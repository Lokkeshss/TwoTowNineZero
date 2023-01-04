package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.exceededmileagevehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.service.SaveUpdateExceededMileageRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_exceeded_mileage_vehicles.*


class AddNewExceededMileageVehiclesFragment : BaseFragment() {

    var isLogged:String="N"
    var exceededMileageVechicleWeightId:String=""
    var mweight:String=""
    var id:String=""
    var filingId:String=""
    private lateinit var mFillingViewModel : FillingViewModel
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })

        mFillingViewModel.mUpdateExceededMileageResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mSaveExceededMileageResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetExceededMileageByIdResponse.observe(this, Observer {
            exceededMileageVechicleVIN.setText(it.vin)
            exceededMileageVechicleWeight.setText(mweight)
            exceededMileageVechicleWeightId=it.weightCategory
            exceededMileageVechicleLogging.isChecked=it.isLogging.equals("Y")
            isLogged=it.isLogging
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
            R.layout.fragment_add_new_exceeded_mileage_vehicles,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        exceededMileageVechicleCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let {

             filingId = it.getString("filingId").toString()
            val weight = it.getString("weight").toString()
            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mweight=weight
                mFillingViewModel.getExceededMileageById(id,filingId)
                exceededMileageVechicleSubmit.setText("Update")
            }

        }

        mFillingViewModel.gettaxableweight()

        exceededMileageVechicleWeight.isFocusable=false
        exceededMileageVechicleWeight.isClickable=true

        exceededMileageVechicleWeight.setOnClickListener {
            showChangedTaxableWeight()
        }

        exceededMileageVechicleLogging.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                isLogged="Y"
            }else{
                isLogged="N"
            }
        }

        exceededMileageVechicleSubmit.setOnClickListener {
            if (exceededMileageVechicleVIN.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            }else if (exceededMileageVechicleVIN.text.toString().length<17){
                showToast("VIN must be at least 17 characters long.")
            }else if (exceededMileageVechicleWeightId.isNullOrEmpty()){
                showToast("Select Taxable Gross Weight (in lbs)")
            }else{
                if (exceededMileageVechicleSubmit.text.toString().equals("Save ")){
                    val i = SaveUpdateExceededMileageRequest(filingId,isLogged,exceededMileageVechicleVIN.text.toString(),exceededMileageVechicleWeightId)
                    mFillingViewModel.saveExceededMileage(filingId,i)
                }else{
                    val i = SaveUpdateExceededMileageRequest(filingId,isLogged,exceededMileageVechicleVIN.text.toString(),exceededMileageVechicleWeightId)
                    mFillingViewModel.updateExceededMileage(id,filingId,i)
                }

            }

        }
    }

    private fun showChangedTaxableWeight() {

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Weight"

        mTaxableWeightSpinnerAdapter= TaxableWeightSpinnerAdapter(mTaxableWeightList){ weight, id->
            exceededMileageVechicleWeight?.setText(weight)
            exceededMileageVechicleWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            exceededMileageVechicleWeight?.setText("")
            exceededMileageVechicleWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}