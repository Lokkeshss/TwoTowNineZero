package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.HomeScreenAdapterActivate
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.HomeScreenAdapterArchive
import com.example.twotwoninezero.dashboard.bottomnavigation.home.model.HomeViewModel
import com.example.twotwoninezero.service.FilingFilterRequest
import com.example.twotwoninezero.service.HomeScreenGetFilingsByUserIdRequest
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.io.File

class HomeScreenFragment : BaseFragment() {
    private var mLayoutManager: LinearLayoutManager? = null
    private lateinit var homeViewModel: HomeViewModel
    var mHomeScreenAdapterActivate: HomeScreenAdapterActivate?=null
    var mHomeScreenAdapterArchive: HomeScreenAdapterArchive?=null
    private var customDialog: AlertDialog?=null
    var usethis:String?=null
    var ActiverequestCount=10
    var DeleterequestCount=10
    var ActiverequestOffSetCount=0
    var DeleterequestOffSetCount=0

    companion object{
        var requestType="active"
        var activeCount:Int=1
        var archiveCount:Int=1
        var HomeScreen_filingId_FormSummary=""
    }

    override fun initViewModel() {
        homeViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            HomeViewModel::class.java
        )
        setViewModel(homeViewModel)
        homeViewModel.mHomeScreenListResponseArchive.observe(this, Observer {
            if (it.isNullOrEmpty()){
                noFiling.visibility=View.VISIBLE
                homeScreenRV.visibility=View.GONE
            }else{
                noFiling.visibility=View.GONE
                homeScreenRV.visibility=View.VISIBLE
            }
                if (!it.isNullOrEmpty()){
                    if (it.size<=10){
                        mHomeScreenAdapterArchive= HomeScreenAdapterArchive(it){ filingIdorBusinessName, formtype, createdDate, filingstaus,firstusedMonth,paymentStatus,filingStatus, type->
                            if (type==0){

                            }else if (type==1){
                                deleteOrReactiveFilingId(filingIdorBusinessName,"activate")
                            }else if (type ==2){

                                findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToHomeFilingDetailsViewFragment(
                                    filingIdorBusinessName,formtype,paymentStatus,
                                    firstusedMonth,createdDate,filingstaus,filingStatus, ""
                                ))
                            }else{

                            }
                        }
                        mLayoutManager = LinearLayoutManager(requireContext())
                        homeScreenRV?.layoutManager = mLayoutManager
                        homeScreenRV?.adapter = mHomeScreenAdapterArchive
                    }else{
                        mHomeScreenAdapterArchive?.setpageNation(it)
                       // mHomeScreenAdapterArchive?.notifyItemChanged(DeleterequestCount)
                        mHomeScreenAdapterArchive?.notifyDataSetChanged()
                    }
                }


            homeScreenRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) //check for scroll down
                    {
                        val totalItemCount: Int = mLayoutManager!!.getItemCount()
                        val lastVisibleItemPosition: Int = mLayoutManager!!.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == totalItemCount - 1) {

                            println("totalItemCount "+totalItemCount)
                            println("totalItemCount "+lastVisibleItemPosition)

                           // DeleterequestCount= DeleterequestCount+10
                            DeleterequestOffSetCount+=1
                            if (requestType.equals("archive")){
                                val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                                homeViewModel.getFilingsByUserId(i,requestType)
                            }else{
                                val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                                homeViewModel.getFilingsByUserId(i,requestType)
                            }


                        }
                    }
                }
            })

        })
        homeViewModel.mHomeScreenListResponseActive.observe(this, Observer {
            if (it.isNullOrEmpty()){
                noFiling.visibility=View.VISIBLE
                homeScreenRV.visibility=View.GONE
            }else{
                noFiling.visibility=View.GONE
                homeScreenRV.visibility=View.VISIBLE
            }

            if (!it.isNullOrEmpty()){
                if (it.size<=10){
                    mHomeScreenAdapterActivate= HomeScreenAdapterActivate(it){ filindID,businessId, filingIdorBusinessName, formtype, createdDate, filingstaus,firstusedMonth,paymentStatus,filingStatus, type->
                        if (type==0){

                            deleteOrReactiveFilingId(filingIdorBusinessName,"archive")

                        }else if (type==1){
                            HomeScreen_filingId_FormSummary=filingIdorBusinessName
                            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFormSummaryFragment(filingIdorBusinessName))
                        }else if (type ==2){

                            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToHomeFilingDetailsViewFragment(
                                filingIdorBusinessName,formtype,paymentStatus,
                                firstusedMonth,createdDate,filingstaus,filingStatus,filindID
                            ))
                        }else{

                        }
                    }
                    mLayoutManager = LinearLayoutManager(requireContext())
                    homeScreenRV?.layoutManager = mLayoutManager
                    homeScreenRV?.adapter = mHomeScreenAdapterActivate
                }else{
                    mHomeScreenAdapterActivate?.setpageNation(it)
                   // mHomeScreenAdapterActivate?.notifyItemChanged(ActiverequestCount)
                    mHomeScreenAdapterActivate?.notifyDataSetChanged()
                }
            }


            homeScreenRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) //check for scroll down
                    {
                        val totalItemCount: Int = mLayoutManager!!.getItemCount()
                        val lastVisibleItemPosition: Int = mLayoutManager!!.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == totalItemCount - 1) {

                            println("totalItemCount "+totalItemCount)
                            println("totalItemCount "+lastVisibleItemPosition)
                           // ActiverequestCount = ActiverequestCount+10
                            ActiverequestOffSetCount+=1
                            if (requestType.equals("archive")){
                                val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                                homeViewModel.getFilingsByUserId(i,requestType)
                            }else{
                                val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                                homeViewModel.getFilingsByUserId(i,requestType)
                            }


                        }
                    }
                }
            })
        })

        homeViewModel.mDeleteHomeScreenFilingResponse.observe(this, Observer {
                if (it.code==200){

                    showToast(it.message)
                    customDialog?.dismiss()
                    if (requestType.equals("archive")){
                        val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                        homeViewModel.getFilingsByUserId(i,requestType)
                    }else{
                        val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                        homeViewModel.getFilingsByUserId(i,requestType)
                    }


                }else{
                    showToast(it.message)
                }
        })
        homeViewModel.mReactivateHomeScreenFilingResponse.observe(this, Observer {
                if (it.code==200){

                    showToast(it.message)
                    customDialog?.dismiss()
                    if (requestType.equals("archive")){
                        val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                        homeViewModel.getFilingsByUserId(i,requestType)
                    }else{
                        val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                        homeViewModel.getFilingsByUserId(i,requestType)
                    }
                   /* val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
                    homeViewModel.getFilingsByUserId(i,requestType)*/
                }else{
                    showToast(it.message)
                }
        })

        homeViewModel.mGetIRSRejectionDetailsResponse.observe(this, Observer {
            showToast(it.irsRejectArray.get(0).toString())
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
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            val category = it.getString("category")
            val fromDate = it.getString("fromDate")
            val isPartial = it.getString("isPartial")
            val keyword = it.getString("keyword")
            val limit = it.getString("limit")
            val listType = it.getString("listType")
            val offset = it.getString("offset")
            val toDate = it.getString("toDate")
             usethis = it.getString("usethis")

            if (usethis != null && !usethis.isNullOrEmpty() && usethis.equals("YES")) {
                val fi =  FilingFilterRequest(category.toString(),fromDate.toString(),isPartial.toString(),keyword.toString(),limit.toString(),requestType
                    ,offset.toString(),toDate.toString())
                 homeViewModel.filterHomeScreenFiling(fi,requestType)
            }else{
                if (requestType.equals("archive")){
                    val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                    homeViewModel.getFilingsByUserId(i,requestType)
                }else{
                    val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                    homeViewModel.getFilingsByUserId(i,requestType)
                }

              /*  val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
                homeViewModel.getFilingsByUserId(i,requestType)*/
            }
        }
/*        homeScreenRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) //check for scroll down
                {
                    val totalItemCount: Int = mLayoutManager!!.getItemCount()
                    val lastVisibleItemPosition: Int = mLayoutManager!!.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == totalItemCount - 1) {

                        println("totalItemCount "+totalItemCount)
                        println("totalItemCount "+lastVisibleItemPosition)


                    }
                }
            }
        })*/

        if (requestType.equals("active")){
            blue()
            archiveOrActiveText.setText("Archived Filing")
        }else{
            green()
            archiveOrActiveText.setText("Activate Filing")
        }


        addNewFiling.setOnClickListener {
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToTaxYearAndFormFragment("","",""))
        }

        filingFilter.setOnClickListener {
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFilingFilterFragment())
        }

        textView2.setOnClickListener {
            downloadFile("lokkeshfilepdf")
        }

        activeORarchievFilingList.setOnClickListener {

            if (requestType.equals("active")){
                green()
                requestType="archive"
                archiveOrActiveText.setText("Activate Filing")
            }else{
                blue()
                requestType="active"
                archiveOrActiveText.setText("Archived Filing")
            }

            if (requestType.equals("archive")){
                val i= HomeScreenGetFilingsByUserIdRequest(DeleterequestCount,requestType,DeleterequestOffSetCount)
                homeViewModel.getFilingsByUserId(i,requestType)
            }else{
                val i= HomeScreenGetFilingsByUserIdRequest(ActiverequestCount,requestType,ActiverequestOffSetCount)
                homeViewModel.getFilingsByUserId(i,requestType)
            }

/*            val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
            homeViewModel.getFilingsByUserId(i,requestType)*/
        }
    }



    private fun deleteOrReactiveFilingId(filingId: String, type:String) {
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
                homeViewModel.reactivateFiling(filingId)
            }else{
                homeViewModel.deleteFiling(filingId)
            }

        }
    }

    fun blue(){
        archiveOrActiveIcon.setImageResource(R.drawable.archivedfiling_blue)
        archiveOrActiveText.setTextColor(Color.parseColor("#007BFF"))
        activeORarchievFilingList.setBackground(getResources().getDrawable(R.drawable.businessscreen_roundborder_blue))
    }

    fun green(){
        archiveOrActiveIcon.setImageResource(R.drawable.archivedfiling_green)
        archiveOrActiveText.setTextColor(Color.parseColor("#218838"))
        activeORarchievFilingList.setBackground(getResources().getDrawable(R.drawable.businessscreen_roundborder_green))
    }

    private fun downloadFile(uploadedFileName:String){
        val filename: String? = uploadedFileName
        val file  = File(context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + filename)
        homeViewModel.downloadPDF(filename!!,file)
    }

}