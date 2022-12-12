package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation

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
import com.example.twotwoninezero.service.SaveTaxableVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.fragment_add_new_vehical.*

class AddNewVehicalFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    var addnewVehicalTaxableGrossWeightId=""
    var isLogging="N"
    var businessId:String?=null
    var filingId:String?=""

    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })

        mFillingViewModel.mEditTaxableVehicleByIdResponse.observe(this, Observer {
            addnewVehicalVin.setText(it.vin)
            isLogging=it.isLogging
            addnewVehicalLogging.isChecked = !it.isLogging.equals("N")

        })

        mFillingViewModel.mSaveTaxableVehicleResponse.observe(this, Observer {
           if (it.code==200){
               showToast(it.message)
               requireActivity().onBackPressed()
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
        val view = inflater.inflate(R.layout.fragment_add_new_vehical, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val weight = it.getString("weight")
            val weight_category = it.getString("weight_category")
             businessId = it.getString("id")
            filingId = it.getString("filingId").toString()

            if (businessId != null && !businessId.isNullOrEmpty()) {
                addnewVehicalTaxableGrossWeight?.setText(weight)
                addnewVehicalTaxableGrossWeightId= weight_category.toString()
                mFillingViewModel.getTaxableVehicleById(businessId!!, filingId!!)
                addnewVehicalSubmit.setText("Update")
            }

        }

        mFillingViewModel.gettaxableweight()

        addnewVehicalTaxableGrossWeight.isFocusable=false
        addnewVehicalTaxableGrossWeight.isClickable=true

        addnewVehicalTaxableGrossWeight.setOnClickListener {
            showTaxableWeight()
        }

        addnewVehicalLogging.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                isLogging = "Y"
            }else{
                isLogging = "N"
            }
        }

        addnewVehicalSubmit.setOnClickListener {
           if (addnewVehicalVin.text.toString().isNullOrEmpty()){
               showToast("Vehicle identification number is required")
           }else if (addnewVehicalVin.text.toString().length!=17){
               showToast("VIN must be at least 17 characters long.")
           }else if (addnewVehicalTaxableGrossWeight.text.toString().isNullOrEmpty()){
               showToast("Select Taxable Gross Weight")
           }else{
               if (addnewVehicalSubmit.text.toString().equals("Save ")){

                   val i = SaveTaxableVehicleRequest(filingId!!,isLogging,addnewVehicalVin.text.toString(),addnewVehicalTaxableGrossWeightId)
                   mFillingViewModel.saveTaxableVehicle(filingId!!,i)

               }else{

                   val i = SaveTaxableVehicleRequest(filingId!!,isLogging,addnewVehicalVin.text.toString(),addnewVehicalTaxableGrossWeightId)
                   mFillingViewModel.updateTaxableVehicle(businessId.toString(),filingId!!,i)

               }

           }
        }

    }

    private fun showTaxableWeight() {

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
            addnewVehicalTaxableGrossWeight?.setText(weight)
            addnewVehicalTaxableGrossWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            addnewVehicalTaxableGrossWeight?.setText("")
            addnewVehicalTaxableGrossWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}