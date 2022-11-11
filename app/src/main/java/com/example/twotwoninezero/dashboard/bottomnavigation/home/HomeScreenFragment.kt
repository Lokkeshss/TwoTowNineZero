package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.adapter.FleetListAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.HomeScreenAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.home.model.HomeViewModel
import com.example.twotwoninezero.service.FilingFilterRequest
import com.example.twotwoninezero.service.HomeScreenGetFilingsByUserIdRequest
import kotlinx.android.synthetic.main.delete_or_activate_by_businessid.*
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import kotlinx.android.synthetic.main.fragment_fleet_screen.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.text.SimpleDateFormat
import java.util.*

class HomeScreenFragment : BaseFragment() {
    private lateinit var homeViewModel: HomeViewModel
    var mHomeScreenAdapter: HomeScreenAdapter?=null
    private var customDialog: AlertDialog?=null
    var usethis:String?=null
    companion object{
        var requestType="active"
    }

    override fun initViewModel() {
        homeViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            HomeViewModel::class.java
        )
        setViewModel(homeViewModel)
        homeViewModel.mHomeScreenListResponse.observe(this, Observer {
            if (it.isNullOrEmpty()){
                noFiling.visibility=View.VISIBLE
                homeScreenRV.visibility=View.GONE
            }else{
                noFiling.visibility=View.GONE
                homeScreenRV.visibility=View.VISIBLE

                if (requestType.equals("active")){
                    if (usethis.isNullOrEmpty()){
                        requestType="archive"
                    }else{
                        requestType="active"
                    }
                   // requestType="archive"
                    archiveOrActiveText.setText("Archived Filing")
                }else{
                    if (usethis.isNullOrEmpty()){
                        requestType="active"
                    }else{
                        requestType="archive"
                    }
                   // requestType="active"
                    archiveOrActiveText.setText("Activate Filing")
                }
            }

            mHomeScreenAdapter= HomeScreenAdapter(it){ filingIdorBusinessName,formtype,createdDate,filingstaus,type->
                if (type==0){

                    deleteFilingId(filingIdorBusinessName)

                }else if (type==1){

                }else if (type ==2){
                    findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToHomeFilingDetailsViewFragment(
                        filingIdorBusinessName,formtype,"",
                        "",createdDate,filingstaus,""
                    ))
                }else{

                }
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            homeScreenRV?.layoutManager = mLayoutManager
            homeScreenRV?.adapter = mHomeScreenAdapter
        })

        homeViewModel.mDeleteHomeScreenFilingResponse.observe(this, Observer {
                if (it.code==200){

                    showToast(it.message)
                    customDialog?.dismiss()
                    val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
                    homeViewModel.getFilingsByUserId(i)
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

                val fi =  FilingFilterRequest(category.toString(),fromDate.toString(),isPartial.toString(),keyword.toString(),limit.toString(),listType.toString()
                    ,offset.toString(),toDate.toString())
                 homeViewModel.filterHomeScreenFiling(fi)
            }else{
                val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
                homeViewModel.getFilingsByUserId(i)
            }
        }


        addNewFiling.setOnClickListener {
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToTaxYearAndFormFragment("",""))
        }

        filingFilter.setOnClickListener {
            findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToFilingFilterFragment())
        }

        activeORarchievFilingList.setOnClickListener {
            val i= HomeScreenGetFilingsByUserIdRequest(10,requestType,0)
            homeViewModel.getFilingsByUserId(i)
        }
    }



    private fun deleteFilingId(filingId: String) {
        val dialogView = layoutInflater.inflate(R.layout.delete_or_activate_by_businessid, null)

        customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)

        cancel.setOnClickListener {

            customDialog?.dismiss()
        }

        delete.setOnClickListener {
            homeViewModel.deleteFiling(filingId)

        }

    }

}