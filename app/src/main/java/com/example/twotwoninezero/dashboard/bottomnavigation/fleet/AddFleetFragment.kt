package com.example.twotwoninezero.dashboard.bottomnavigation.fleet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.FleetBusinessnameSpinnerAdapter
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.model.FleetViewModel
import com.example.twotwoninezero.service.AddNewFleetBusinessRequest
import com.example.twotwoninezero.service.GetBusinessNameResponse
import com.example.twotwoninezero.service.GetFleetByIdResponse
import com.example.twotwoninezero.service.TaxableWeightResponse
import com.google.gson.Gson


class AddFleetFragment : BaseFragment() {
    private var getFleetByIdResponse: GetFleetByIdResponse?=null
    private var mgetBusinessNameList: List<GetBusinessNameResponse> = ArrayList()
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    private lateinit var mFleetViewModel : FleetViewModel
    var mFleetBusinessnameSpinnerAdapter: FleetBusinessnameSpinnerAdapter?=null
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null

    var addfleetBusinessName:EditText?=null
    var addfleetId:String?=null
    var addfleetBusinessNameId:String?=null
    var addfleetBusinessNameArgument:String?=null
    var addfleetBusinessNameweight:String?=null
    var addFleetVinNo:EditText?=null
    var addfleetSelectWeight:EditText?=null
    var addfleetSelectWeightId:String?=null
    var fleetForLogging:Switch?=null
    var fleetAgriVehicle:Switch?=null
    var addNewFleetCancel:TextView?=null
    var addNewFleetSubmit:TextView?=null

    var commonContactCallMain: ImageView?=null
    var notification: ImageView?=null

    var fleetforLogginValue:String="no"
    var fleetAgriVehicleValue:String="no"

    override fun initViewModel() {
        mFleetViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FleetViewModel::class.java)
        setViewModel(mFleetViewModel)

        mFleetViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })
        mFleetViewModel.mGetBusinessNameList.observe(this, Observer {
            mgetBusinessNameList = it
        })
        mFleetViewModel.mGetFleetByIdResponse.observe(this, Observer {
            getFleetByIdResponse = it
            editFleetFunction(getFleetByIdResponse!!)
        })
        mFleetViewModel.mAddNewFleetResponse.observe(this, Observer {
           if (it.code==200){
               showToast(it.message)
               requireActivity().onBackPressed()
           }else{
               showToast(it.message)
           }
        })
    }

    private fun editFleetFunction(response: GetFleetByIdResponse) {
        addNewFleetSubmit?.text = "Update"
        addfleetId=response.id.toString()
        addfleetBusinessNameId=response.businessId.toString()
        fleetAgriVehicleValue=response.isAgriculture
        fleetforLogginValue=response.isLogging
        addfleetSelectWeightId=response.weightCategory
        addfleetBusinessName?.setText(addfleetBusinessNameArgument)
        addFleetVinNo?.setText(response.vin)
        addfleetSelectWeight?.setText(addfleetBusinessNameweight)

        fleetForLogging?.isChecked = response.isLogging.lowercase().equals("yes")
        fleetAgriVehicle?.isChecked = response.isAgriculture.lowercase().equals("yes")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_fleet, container, false)

        addfleetBusinessName=view.findViewById(R.id.addfleetBusinessName)
        addFleetVinNo=view.findViewById(R.id.addFleetVinNo)
        addfleetSelectWeight=view.findViewById(R.id.addfleetSelectWeight)
        fleetForLogging=view.findViewById(R.id.fleetForLogging)
        fleetAgriVehicle=view.findViewById(R.id.fleetAgriVehicle)
        addNewFleetCancel=view.findViewById(R.id.addNewFleetCancel)
        addNewFleetSubmit=view.findViewById(R.id.addNewFleetSubmit)

        commonContactCallMain = view.findViewById(R.id.commonContactCallMain)
        notification = view.findViewById(R.id.notification)

        addfleetBusinessName?.isFocusable = false
        addfleetBusinessName?.isClickable = true
        addfleetSelectWeight?.isFocusable = false
        addfleetSelectWeight?.isClickable = true

        arguments?.let {
            val id = it.getString("fleetId")
            val businessName = it.getString("businessName")
            val weight = it.getString("weight")
            if (id != null && id.isNotEmpty()) {
                addfleetBusinessNameArgument=businessName
                addfleetBusinessNameweight=weight
                mFleetViewModel.getFleetById(id)
            }
        }


        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }


        mFleetViewModel.getbusinessname()
        mFleetViewModel.gettaxableweight()

        fleetForLogging?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                fleetforLogginValue="yes"
            }else{
                fleetforLogginValue="no"
            }
        }

        fleetAgriVehicle?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                fleetAgriVehicleValue="yes"
            }else{
                fleetAgriVehicleValue="no"
            }

        }

        addfleetBusinessName?.setOnClickListener {
            showBusinessName()
        }
        addfleetSelectWeight?.setOnClickListener {
            showTaxableWeight()
        }

        addNewFleetCancel?.setOnClickListener {
           requireActivity().onBackPressed()
        }
        addNewFleetSubmit?.setOnClickListener {

            if (addNewFleetSubmit?.text.toString().equals("Save")){
                AddNewFleetSubmit("Save")
            }else{
                AddNewFleetSubmit("")
            }

        }

        return view
    }

    private fun AddNewFleetSubmit(s: String) {

        if (addfleetBusinessName?.text.toString().isNullOrEmpty()){
            showToast("Please Select Business Name")
        }else if (addFleetVinNo?.text.toString().isNullOrEmpty()){
            showToast("Please Enter VIN Number")
        }else if (addfleetSelectWeight?.text.toString().isNullOrEmpty()){
            showToast("Please Select Taxable Weight")
        }else{
            val i = AddNewFleetBusinessRequest(addfleetBusinessNameId.toString(),addfleetBusinessName?.text.toString(),"",fleetAgriVehicleValue,fleetforLogginValue,
                addfleetBusinessName?.text.toString(), addFleetVinNo?.text.toString(),addfleetSelectWeightId.toString())
            val gson = Gson()
            val jsonbranch: String = gson.toJson(i)
            println("jsonbranch business  "+jsonbranch)
            if (s.equals("Save")){
                mFleetViewModel.addNewFleetBusinessRequest(i)
            }else{
                mFleetViewModel.updateFleetlineItem(addfleetId.toString(),i)
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
            addfleetSelectWeight?.setText(weight)
            addfleetSelectWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            addfleetSelectWeight?.setText("")
            addfleetSelectWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun showBusinessName() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select BusinessName"

        mFleetBusinessnameSpinnerAdapter= FleetBusinessnameSpinnerAdapter(mgetBusinessNameList){ businessname, id->
            addfleetBusinessName?.setText(businessname)
            addfleetBusinessNameId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mFleetBusinessnameSpinnerAdapter

        cancel.setOnClickListener {
            addfleetBusinessName?.setText("")
            addfleetBusinessNameId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}