package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.TaxableWeightSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TaxableVehicleInfoAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.PriorYearSuspended.PriorYearSuspendedExemptVehiclesFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.VehiclesTaxMainMenuDirections
import com.example.twotwoninezero.service.LoadFleetListResponse
import com.example.twotwoninezero.service.SaveBulkTaxableVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_taxable_vehicle_information.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class TaxableVehicleInformationFragment : BaseFragment() {
    private var addFromFleetDialog: AlertDialog?=null
    private var mTaxableWeightList: List<TaxableWeightResponse> = ArrayList()
    private var mLoadFleetList: List<LoadFleetListResponse> = ArrayList()
    var mTaxableWeightSpinnerAdapter: TaxableWeightSpinnerAdapter?=null
    private lateinit var mFillingViewModel : FillingViewModel
    var mTaxableVehicleInfoAdapter: TaxableVehicleInfoAdapter?=null
    var mAddFromMyFleetTaxableVehicleAdapter: AddFromMyFleetTaxableVehicleAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    var allowLoadFleet:Boolean=false
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mTaxableWeightResponseList.observe(this, Observer {
            mTaxableWeightList = it
        })

        mFillingViewModel.mGetTaxableVehicleResponse.observe(this, Observer {
            mTaxableVehicleInfoAdapter=
                TaxableVehicleInfoAdapter(it){ businessId,weight,weightCategory,requestType->

                    if (requestType==0){
                        // delete
                        deleteOrReactiveFilingId(businessId, filingId)

                    }else if (requestType==1){
                        // edit
                        findNavController().navigate(
                            TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToAddNewVehicalFragment(weight,weightCategory,businessId,filingId))
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
                customDialog?.dismiss()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mLoadFleetListResponse.observe(this, Observer {
            if (it.isEmpty()){
                allowLoadFleet=false
            }else{
                allowLoadFleet=true
            }

            mLoadFleetList =it
        })

        mFillingViewModel.msaveBulkTaxableVehicleResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getTaxableVehicleByFilingId(filingId)
                mFillingViewModel.loadFleetList(filingId)
            }else{
                showToast(it.message)
            }
            addFromFleetDialog?.dismiss()
        })


        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

            if (it.filingInfo.formType.equals("2290")){
                if (it.totalDueToIRS.equals("0.00") && it.totalCreditAmt.equals("0.00") && it.totalTaxAmt.equals("0.00")){
                    // form summary
                    findNavController().navigate(TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToFormSummaryFragment(filingId))

                    }

                }
            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponseBack.observe(this, Observer {

            findNavController().navigate(TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToTaxableVehicleInformation(filingId,it.filingInfo.formType))

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

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        arguments?.let {
            filingId= it.getString("filingId").toString()
        }

        progress_bar.progress=33
        progress_text.setText("2 of 6")

        mFillingViewModel.getTaxableVehicleByFilingId(filingId)
        mFillingViewModel.loadFleetList(filingId)

        mFillingViewModel.gettaxableweight()

        taxableVehicleAddNewVechicle.setOnClickListener {
                findNavController().navigate(
                    TaxableVehicleInformationFragmentDirections.actionTaxableVehicleInformationFragmentToAddNewVehicalFragment("","","",filingId)
                )
        }

        taxableVehicleAddFromMyFleet.setOnClickListener {
                if (allowLoadFleet){
                    showAddFromFleet()
                }
        }

        taxableVehicleUploadTaxable.setOnClickListener {

        }

        taxableVehicleNext.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
        }

        taxableVehicleCancel.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponseForBack(filingId)
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

    fun showAddFromFleet(){
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)
         addFromFleetDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        val window: Window = addFromFleetDialog?.getWindow()!!
        val wlp: WindowManager.LayoutParams = window.getAttributes()

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.setAttributes(wlp)
        addFromFleetDialog?.getWindow()!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addFromFleetDialog?.setCancelable(false)

        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        val normalcancelLL = dialogView.findViewById<LinearLayout>(R.id.normalcancelLL)
        val addFromFleetLL = dialogView.findViewById<LinearLayout>(R.id.addFromFleetLL)
        val addFromFleetSubmit = dialogView.findViewById<TextView>(R.id.addFromFleetSubmit)
        val addFromFleetCancel = dialogView.findViewById<TextView>(R.id.addFromFleetCancel)
        normalcancelLL.visibility=View.GONE
        addFromFleetLL.visibility=View.VISIBLE
        title.text="Select Category"

        mAddFromMyFleetTaxableVehicleAdapter= AddFromMyFleetTaxableVehicleAdapter(mLoadFleetList){ vin, type->

            if (type.equals("weight")){
                showTaxableWeight(vin)
            }

         }

         val mLayoutManager = LinearLayoutManager(requireContext())
         custom_rv?.layoutManager = mLayoutManager
         custom_rv?.adapter = mAddFromMyFleetTaxableVehicleAdapter

        addFromFleetSubmit.setOnClickListener {
            var allowBulkSubmit=false
            mLoadFleetList.forEach {
                if (it.isCheckMe){
                    allowBulkSubmit=true
                }
            }
            if (allowBulkSubmit){
                addsaveBulkTaxableVehicle()
            }else{
                showToast("Please Select the VINs to be added for filing.")
            }
        }
        addFromFleetCancel.setOnClickListener {
            addFromFleetDialog?.dismiss()
        }

    }

    fun addsaveBulkTaxableVehicle(){
        val arrayI: ArrayList<SaveBulkTaxableVehicleRequest> = ArrayList()

        mLoadFleetList.forEach {
            if (it.isCheckMe){
                if (it.isLogging.equals("NO")||it.isLogging.equals("N")){
                    it.isLogging="N"
                }else{
                    it.isLogging="Y"
                }
                val i = SaveBulkTaxableVehicleRequest(filingId,it.isLogging,it.vin,it.weightCategory)
                arrayI.add(i)
            }
        }
        mFillingViewModel.saveBulkTaxableVehicle(filingId,arrayI)
        addFromFleetDialog?.dismiss()
    }

    private fun showTaxableWeight(vin: String) {

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

        mTaxableWeightSpinnerAdapter= TaxableWeightSpinnerAdapter(mTaxableWeightList){ weight, weightCategory->
            mLoadFleetList.forEach {
                if (it.vin.equals(vin)){
                    it.weight=weight
                    it.weightCategory=weightCategory
                }
            }
            mAddFromMyFleetTaxableVehicleAdapter?.newFilter(mLoadFleetList)
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTaxableWeightSpinnerAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}