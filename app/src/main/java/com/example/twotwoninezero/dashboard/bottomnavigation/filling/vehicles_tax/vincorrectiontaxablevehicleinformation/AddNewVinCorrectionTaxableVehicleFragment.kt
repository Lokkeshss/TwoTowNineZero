package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.vincorrectiontaxablevehicleinformation

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TypeOfLossAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.service.SaveUpdateVinCorrectionRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_exceeded_mileage_vehicles.*
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import kotlinx.android.synthetic.main.fragment_add_new_taxable_vehicle_information.*


class AddNewVinCorrectionTaxableVehicleFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var isLogged:String="N"
    var vinCorrectionWeightId:String=""
    var mweight:String=""
    var id:String=""
    var filingId:String=""
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var TypeOfLoss = listOf<String>("taxable","suspend")
    var mTypeOfLossAdapter: TypeOfLossAdapter?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)
        mFillingViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })

        mFillingViewModel.mSaveVinCorrectionResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mUpdateVinCorrectionResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })


        mFillingViewModel.mGetVinCorrectionByIdResponse.observe(this, Observer {
            vinCorrectionPreviousVIN.setText(it.previousVin)
            vinCorrectionCorrectVIN.setText(it.correctVin)
            vinCorrectionTypeOfCorrection.setText(it.vinType)
            vinCorrectionTaxableGrossweight.setText(mweight)
            vinCorrectionExplanationVinCorrection.setText(it.vinCorrectionExplanation)
            vinCorrectionLoggin.isChecked=it.isLogging.equals("Y")
            vinCorrectionWeightId=it.weightCategory
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
            R.layout.fragment_add_new_taxable_vehicle_information,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        vinCorrectionCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let {

            filingId = it.getString("filingId").toString()
            val weight = it.getString("weight").toString()
            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mweight=weight
                mFillingViewModel.getVinCorrectionById(id,filingId)
                vinCorrectionSubmit.setText("Update")
            }

        }

        mFillingViewModel.gettaxableweight()

        vinCorrectionTypeOfCorrection.isFocusable=false
        vinCorrectionTypeOfCorrection.isClickable=false

        vinCorrectionTaxableGrossweight.isFocusable=false
        vinCorrectionTaxableGrossweight.isClickable=false

        vinCorrectionTypeOfCorrection.setOnClickListener {
            showAdapterList()
        }

        vinCorrectionTaxableGrossweight.setOnClickListener {
            showChangedTaxableWeight()
        }

        vinCorrectionLoggin.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                isLogged="Y"
            }else{
                isLogged="N"
            }
        }

        vinCorrectionSubmit.setOnClickListener {
            if (vinCorrectionPreviousVIN.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            }else if (vinCorrectionPreviousVIN.text.toString().length<17){
                showToast("VIN must be at least 17 characters long.")
            }else if (vinCorrectionCorrectVIN.text.toString().isNullOrEmpty()){
                showToast("Vehicle identification number is required")
            }else if (vinCorrectionCorrectVIN.text.toString().length<17){
                showToast("VIN must be at least 17 characters long.")
            }else if (vinCorrectionPreviousVIN.text.toString().equals(vinCorrectionCorrectVIN.text.toString())){
                showToast("Previous VIN and Correct VIN should not be same")
            }else if (vinCorrectionCorrectVIN.text.toString().equals(vinCorrectionPreviousVIN.text.toString())){
                showToast("Previous VIN and Correct VIN should not be same")
            }else if (vinCorrectionTypeOfCorrection.text.toString().isNullOrEmpty()){
                showToast("Select Correction Type")
            }else if (vinCorrectionWeightId.isNullOrEmpty()){
                showToast("Select Taxable Gross Weight (in lbs)")
            }else if (vinCorrectionExplanationVinCorrection.text.toString().isNullOrEmpty()){
                showToast("Enter Explanation for VIN Correction")
            }else if (isLogged.isNullOrEmpty()){
                showToast("Select Is the Vehicle used for Logging")
            }else{
                if (vinCorrectionSubmit.text.toString().equals("Save ")){
                    val i = SaveUpdateVinCorrectionRequest(vinCorrectionCorrectVIN.text.toString(),vinCorrectionExplanationVinCorrection.text.toString(),
                        filingId,
                        isLogged,vinCorrectionPreviousVIN.text.toString(),
                        vinCorrectionTypeOfCorrection.text.toString(),vinCorrectionWeightId)
                    mFillingViewModel.saveVinCorrection(filingId,i)
                }else{
                    val i = SaveUpdateVinCorrectionRequest(vinCorrectionCorrectVIN.text.toString(),vinCorrectionExplanationVinCorrection.text.toString(),
                        filingId,
                        isLogged,vinCorrectionPreviousVIN.text.toString(),
                        vinCorrectionTypeOfCorrection.text.toString(),vinCorrectionWeightId)

                    mFillingViewModel.updateVinCorrection(id, filingId,i)
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
            vinCorrectionTaxableGrossweight?.setText(weight)
            vinCorrectionWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            vinCorrectionTaxableGrossweight?.setText("")
            vinCorrectionWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun showAdapterList() {

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)

        title.text="Type of VIN Correction"

        mTypeOfLossAdapter = TypeOfLossAdapter(TypeOfLoss){ type->
            vinCorrectionTypeOfCorrection?.setText(type)
            if (vinCorrectionTypeOfCorrection.text.toString().equals("suspend")){
                vinCorrectionTaxableGrossweight?.setText("")
                vinCorrectionTaxableGrossweight?.visibility=View.GONE
                vinCorrectionWeightId="-"
            }else{
                vinCorrectionTaxableGrossweight?.visibility=View.VISIBLE
            }

            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTypeOfLossAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }
}