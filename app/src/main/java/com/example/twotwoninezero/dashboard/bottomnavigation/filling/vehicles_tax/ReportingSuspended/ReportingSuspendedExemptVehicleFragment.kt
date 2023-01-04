package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended

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
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ReportingSuspendedExemptVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.PriorYearSuspended.PriorYearSuspendedExemptVehiclesFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.AddFromMyFleetTaxableVehicleAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation.TaxableVehicleInformationFragmentDirections
import com.example.twotwoninezero.service.LoadFleetListResponse
import com.example.twotwoninezero.service.SaveBulkCurrentSuspendedRequest
import com.example.twotwoninezero.service.SaveBulkTaxableVehicleRequest
import com.example.twotwoninezero.service.TaxableWeightResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.fragment_reporting_suspended_exempt_vehicle.*
import kotlinx.android.synthetic.main.progress_bar_view.*

class ReportingSuspendedExemptVehicleFragment : BaseFragment() {
    private var addFromFleetDialog: AlertDialog?=null
    private lateinit var mFillingViewModel : FillingViewModel
    private var mLoadFleetList: List<LoadFleetListResponse> = ArrayList()
    var mReportingSuspendedExemptVehiclesAdapterAdapter: ReportingSuspendedExemptVehiclesAdapter?=null
    var mAddFromMyFleetReportingSuspendedAdapter: AddFromMyFleetReportingSuspendedAdapter?=null
    private var customDialog: AlertDialog?=null
    var filingId:String=""
    var allowLoadFleet:Boolean=false
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetCurrentSuspendedByFilingIdResponse.observe(this, Observer {
            println("itttttttttttttttt    "+it.size)
            mReportingSuspendedExemptVehiclesAdapterAdapter =
                ReportingSuspendedExemptVehiclesAdapter(it){ id,requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(
                        ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToAddNewReportingSuspendedExemptVehicles(id,filingId)
                    )
                }

            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            reportingsuspendedRecyclerView?.layoutManager = mLayoutManager
            reportingsuspendedRecyclerView?.adapter = mReportingSuspendedExemptVehiclesAdapterAdapter

        })

        mFillingViewModel.mDeleteCurrentSuspendedeById.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.GetCurrentSuspendedByFilingId(filingId)
            }else{
               showToast(it.message)
            }

            customDialog?.dismiss()
        })


        mFillingViewModel.mLoadFleetListResponse.observe(this, Observer {
            if (it.isEmpty()){
                allowLoadFleet=false
            }else{
                allowLoadFleet=true
            }

            mLoadFleetList =it
        })

        mFillingViewModel.msaveBulkReportingSuspendedResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.GetCurrentSuspendedByFilingId(filingId)
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
                    findNavController().navigate(ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToFormSummaryFragment(filingId))

                }else{

                    if (it.irsPayment!!.paymentMode.isNullOrEmpty()){
                        // irs payment option
                        findNavController().navigate(ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToIRSPaymentOptionsFragment(filingId))
                    }else{

                        // form summary
                        findNavController().navigate(ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToFormSummaryFragment(filingId))

                    }

                }
            }

        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponseBack.observe(this, Observer {

            findNavController().navigate(ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToTaxableVehicleInformation(filingId,it.filingInfo.formType))

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
            R.layout.fragment_reporting_suspended_exempt_vehicle,
            container,
            false
        )
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


        mFillingViewModel.GetCurrentSuspendedByFilingId(filingId)
        mFillingViewModel.loadFleetList(filingId)

        reportingsuspendedAddNewVechicle.setOnClickListener {
            findNavController().navigate(
                ReportingSuspendedExemptVehicleFragmentDirections.actionReportingSuspendedExemptVehicleFragmentToAddNewReportingSuspendedExemptVehicles("",filingId)
            )
        }

        reportingsuspendedAddFromMyFleet.setOnClickListener {
                if (allowLoadFleet){
                    showAddFromFleet()
                }
        }

        reportingsuspendedUploadTaxable.setOnClickListener {

        }


        reportingsuspendedCancel.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponseForBack(filingId)

        }

        reportingsuspendedNext.setOnClickListener {
            mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
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
            mFillingViewModel.deleteCurrentSuspendedeById(businessId,filingId)
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

        mAddFromMyFleetReportingSuspendedAdapter= AddFromMyFleetReportingSuspendedAdapter(mLoadFleetList){ vin, type->


        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mAddFromMyFleetReportingSuspendedAdapter

        addFromFleetSubmit.setOnClickListener {
            var allowBulkSubmit=false
            mLoadFleetList.forEach {
                if (it.isCheckMe){
                    allowBulkSubmit=true
                }
            }
            if (allowBulkSubmit){
                addsaveBulkReportSuspend()
            }else{
                showToast("Please Select the VINs to be added for filing.")
            }
        }
        addFromFleetCancel.setOnClickListener {
            addFromFleetDialog?.dismiss()
        }

    }

    private fun addsaveBulkReportSuspend() {

        val arrayI: ArrayList<SaveBulkCurrentSuspendedRequest> = ArrayList()

        mLoadFleetList.forEach {
            if (it.isCheckMe){
                if (it.isLogging.equals("NO")||it.isLogging.equals("N")){
                    it.isLogging="N"
                }else{
                    it.isLogging="Y"
                }
                if (it.isAgriculture.equals("NO")||it.isAgriculture.equals("N")){
                    it.isAgriculture="N"
                }else{
                    it.isAgriculture="Y"
                }
                val i = SaveBulkCurrentSuspendedRequest(filingId,it.isAgriculture,it.isLogging,it.vin)
                arrayI.add(i)
            }
        }
        mFillingViewModel.saveBulkCurrentSuspended(filingId,arrayI)
        addFromFleetDialog?.dismiss()

    }

}