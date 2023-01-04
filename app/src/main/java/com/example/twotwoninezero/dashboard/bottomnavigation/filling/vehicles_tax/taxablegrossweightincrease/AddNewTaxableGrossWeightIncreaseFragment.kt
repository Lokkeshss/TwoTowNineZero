package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.taxablegrossweightincrease

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
import com.example.twotwoninezero.service.SaveUpdateTGWIncreaseRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_add_new_taxable_gross_weight_increase.*
import kotlinx.android.synthetic.main.fragment_add_new_vehical.*


class AddNewTaxableGrossWeightIncreaseFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel
    var isLogged:String="N"
    var addTaxableGrossWeightChangedWeightId:String=""
    var addTaxableGrossWeightpreviousWeightId:String=""
    var previousCategoryweight:String=""
    var changedCategoryweight:String=""
    var id:String=""
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    var filingId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })

        mFillingViewModel.mGetTGWIncreaseByIdResponse.observe(this, Observer {
            addTaxableGrossWeightVIN.setText(it.vin)
            addTaxableGrossWeightpreviousWeight.setText(previousCategoryweight)
            addTaxableGrossWeightChangedWeight.setText(changedCategoryweight)
            addTaxableGrossWeightpreviousWeightId=it.previousCategory
            addTaxableGrossWeightChangedWeightId=it.changedCategory
            isLogged=it.isLogging
        })

        mFillingViewModel.mSaveTGWIncreaseResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mUpdateTGWIncreaseResponse.observe(this, Observer {
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
        return inflater.inflate(
            R.layout.fragment_add_new_taxable_gross_weight_increase,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        addTaxableGrossWeightCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        arguments?.let {

             filingId = it.getString("filingId").toString()
            val previousCategoryweightloc = it.getString("previousweight").toString()
            val changedCategoryweightloc = it.getString("changedweight").toString()
            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                previousCategoryweight=previousCategoryweightloc
                changedCategoryweight= changedCategoryweightloc
                mFillingViewModel.getTGWIncreaseById(id,filingId)
                addTaxableGrossWeightSubmit.setText("Update")
            }

        }

        mFillingViewModel.gettaxableweight()


        addTaxableGrossWeightpreviousWeight.isFocusable=false
        addTaxableGrossWeightpreviousWeight.isClickable=true

        addTaxableGrossWeightChangedWeight.isFocusable=false
        addTaxableGrossWeightChangedWeight.isClickable=true

        addTaxableGrossWeightChangedWeight.setOnClickListener {
            showChangedTaxableWeight()
        }

        addTaxableGrossWeightpreviousWeight.setOnClickListener {
            showPreviousTaxableWeight()
        }

        addTaxableGrossWeightLogin.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isLogged="Y"
            }else{
                isLogged="N"
            }
        }
        addTaxableGrossWeightSubmit.setOnClickListener {

            if (addTaxableGrossWeightSubmit.text.equals("Save ")){
                val i = SaveUpdateTGWIncreaseRequest(addTaxableGrossWeightChangedWeightId,isLogged,addTaxableGrossWeightpreviousWeightId,addTaxableGrossWeightVIN.text.toString(),
                    filingId)
                mFillingViewModel.saveTGWIncrease(filingId,i)
            }else{
                val i = SaveUpdateTGWIncreaseRequest(addTaxableGrossWeightChangedWeightId,isLogged,addTaxableGrossWeightpreviousWeightId,addTaxableGrossWeightVIN.text.toString(),
                    filingId)
               mFillingViewModel.updateTGWIncrease(id,filingId,i)
            }

        }

        addTaxableGrossWeightCancel.setOnClickListener {
            requireActivity().onBackPressed()
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
            addTaxableGrossWeightChangedWeight?.setText(weight)
            addTaxableGrossWeightChangedWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            addTaxableGrossWeightChangedWeight?.setText("")
            addTaxableGrossWeightChangedWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }


    private fun showPreviousTaxableWeight() {

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
            addTaxableGrossWeightpreviousWeight?.setText(weight)
            addTaxableGrossWeightpreviousWeightId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            addTaxableGrossWeightpreviousWeight?.setText("")
            addTaxableGrossWeightpreviousWeightId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }



}