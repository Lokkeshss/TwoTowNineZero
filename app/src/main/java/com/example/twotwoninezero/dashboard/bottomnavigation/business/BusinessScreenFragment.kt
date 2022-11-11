package com.example.twotwoninezero.dashboard.bottomnavigation.business

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.business.adapter.BusinessListAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.business.adapter.DeletedBusinessListAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.business.model.BusinessViewModel
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse
import com.example.twotwoninezero.service.SearchBusinessRequest
import java.io.File

class BusinessScreenFragment : BaseFragment() {
    private var customDialog: AlertDialog?=null
    private var allBusinessListResponse: List<AllAndSearchBusinessListResponse> = ArrayList()
    private var deletedBusinessListResponse: List<AllAndSearchBusinessListResponse> = ArrayList()
    private lateinit var mBusinessViewModel : BusinessViewModel
    var mBusinessListAdapter:BusinessListAdapter?=null
    var mDeletedBusinessListAdapter:DeletedBusinessListAdapter?=null
    var commonContactCallMain:ImageView?=null
    var notification:ImageView?=null
    var type:String?=null
    override fun initViewModel() {
        mBusinessViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(BusinessViewModel::class.java)
        setViewModel(mBusinessViewModel)

        mBusinessViewModel.mdeletedBusinessListResponse.observe(this, Observer {

            archiveOrActiveText?.setText("Active Business")
            deletedBusinessListResponse=it
            if (deletedBusinessListResponse.isNullOrEmpty()){
                myBusinessRV?.visibility=View.GONE
                emptyBusinessList?.visibility=View.VISIBLE
            }else{
                emptyBusinessList?.visibility=View.GONE
                myBusinessRV?.visibility=View.VISIBLE
                mDeletedBusinessListAdapter = DeletedBusinessListAdapter(deletedBusinessListResponse){ businessId, type->

                    deleteOrActivateByBusinessId(businessId,"activate")


                }

                val mLayoutManager = LinearLayoutManager(requireContext())
                myBusinessRV?.layoutManager = mLayoutManager
                myBusinessRV?.adapter = mDeletedBusinessListAdapter
            }

        })

        mBusinessViewModel.mAllBusinessListResponse.observe(this, Observer {

            archiveOrActiveText?.setText("Archieved Business")
            allBusinessListResponse=it
            if (allBusinessListResponse.isNullOrEmpty()){
                myBusinessRV?.visibility=View.GONE
                emptyBusinessList?.visibility=View.VISIBLE
            }else{
                emptyBusinessList?.visibility=View.GONE
                myBusinessRV?.visibility=View.VISIBLE
                mBusinessListAdapter = BusinessListAdapter(allBusinessListResponse){ businessId,businessName, type->
                        if (type==1){
                            findNavController().navigate(
                                BusinessScreenFragmentDirections.actionBusinessScreenFragmentToAddNewBusinessFragment(
                                    businessId
                                )
                            )
                        }else if (type==0){
                            deleteOrActivateByBusinessId(businessId,"delete")

                        }else if (type==2){
                            findNavController().navigate(
                                BusinessScreenFragmentDirections.actionBusinessScreenFragmentToTaxYearAndFormFragment(
                                    businessName,businessId
                                )
                            )
                        }

                }

                val mLayoutManager = LinearLayoutManager(requireContext())
                myBusinessRV?.layoutManager = mLayoutManager
                myBusinessRV?.adapter = mBusinessListAdapter
            }

        })

        mBusinessViewModel.mArchiveBusinessResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                customDialog?.dismiss()
                mBusinessViewModel.getallbusinesslist("active","0","0","active")
            }else{
                showToast(it.message)
            }
        })
        mBusinessViewModel.mReActivateBusinessResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                customDialog?.dismiss()
                mBusinessViewModel.getallbusinesslist("archive","0","0","archive")
            }else{
                showToast(it.message)
            }
        })
    }

    private fun deleteOrActivateByBusinessId(businessId: String, type:String) {
        val dialogView = layoutInflater.inflate(R.layout.delete_or_activate_by_businessid, null)

        customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val delete = dialogView.findViewById<TextView>(R.id.delete)
        val deleteText = dialogView.findViewById<TextView>(R.id.textfields)
        val deleteIcon = dialogView.findViewById<ImageView>(R.id.icons)

        if (type.equals("activate")){
            deleteText.text="Do you want to activate the filing?"
            delete.text="Activate"
            deleteIcon.setImageResource(R.drawable.rusureicon)
        }else{

        }

        cancel.setOnClickListener {

            customDialog?.dismiss()
        }

        delete.setOnClickListener {
            if (type.equals("activate")){
                mBusinessViewModel.reActiveBusinesss(businessId)
            }else{
                mBusinessViewModel.archiveBusinesss(businessId)
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    var addNewBusiness:LinearLayout?=null
    var test:TextView?=null
    var activeORarchievBusinessList:LinearLayout?=null
    var myBusinessRV:RecyclerView?=null
    var emptyBusinessList:CardView?=null
    var edt_tv_searchByEin:EditText?=null
    var edt_tv_searchByBusinessName:EditText?=null
    var searcheinbusiness:TextView?=null
    var archiveOrActiveIcon:ImageView?=null
    var archiveOrActiveText:TextView?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_business_screen, container, false)
        addNewBusiness = view.findViewById(R.id.addNewBusiness)
        test = view.findViewById(R.id.test)
        activeORarchievBusinessList = view.findViewById(R.id.activeORarchievBusinessList)
        myBusinessRV = view.findViewById(R.id.myBusinessRV)
        emptyBusinessList = view.findViewById(R.id.emptyBusinessList)
        edt_tv_searchByEin = view.findViewById(R.id.edt_tv_searchByEin)
        edt_tv_searchByBusinessName = view.findViewById(R.id.edt_tv_searchByBusinessName)
        searcheinbusiness = view.findViewById(R.id.searcheinbusiness)
        archiveOrActiveIcon = view.findViewById(R.id.archiveOrActiveIcon)
        archiveOrActiveText = view.findViewById(R.id.archiveOrActiveText)

        commonContactCallMain = view.findViewById(R.id.commonContactCallMain)
        notification = view.findViewById(R.id.notification)


        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }


        test?.setOnClickListener {
            downloadFile("twotwoninezero")
        }

        if (archiveOrActiveText?.text.toString().equals("Archieved Business")){

            mBusinessViewModel.getallbusinesslist("active","10","0","active")
        }else{
            mBusinessViewModel.getallbusinesslist("archive","10","0","archive")

        }


        searcheinbusiness?.setOnClickListener {
            if (edt_tv_searchByBusinessName?.text.toString().isNullOrEmpty() &&
                edt_tv_searchByEin?.text.toString().isNullOrEmpty()){
                if (archiveOrActiveText?.text.toString().equals("Archieved Business")){
                    type="active"
                    mBusinessViewModel.getallbusinesslist("active","10","0","active")
                }else{
                    type="archive"
                    mBusinessViewModel.getallbusinesslist("archive","10","0","archive")

                }
            }else{
                if (archiveOrActiveText?.text.toString().equals("Archieved Business")){
                    type="active"
                    val i = SearchBusinessRequest(edt_tv_searchByBusinessName?.text.toString(),edt_tv_searchByEin?.text.toString(),10,"active",0)
                    mBusinessViewModel.searchBusiness(i,"active")
                }else{
                    type="archive"
                    val i = SearchBusinessRequest(edt_tv_searchByBusinessName?.text.toString(),edt_tv_searchByEin?.text.toString(),10,"archive",0)
                    mBusinessViewModel.searchBusiness(i,"archive")

                }
            }

        }


        activeORarchievBusinessList?.setOnClickListener {

            if (archiveOrActiveText?.text.toString().equals("Archieved Business")){
                type="active"
                mBusinessViewModel.getallbusinesslist("archive","10","0","archive")
            }else{
                type="archive"
                mBusinessViewModel.getallbusinesslist("active","10","0","active")
            }

        }


        addNewBusiness?.setOnClickListener {
                findNavController().navigate(
                    BusinessScreenFragmentDirections.actionBusinessScreenFragmentToAddNewBusinessFragment(
                        ""
                    )
                )
        }
        return view
    }

    private fun contactInfoCountry() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Country"

        cancel.setOnClickListener {

            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }


    private fun downloadFile(uploadedFileName:String){
        val filename: String? = uploadedFileName
        val file  = File(context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + filename)
        mBusinessViewModel.doFileDownload(filename!!,file)
    }
}