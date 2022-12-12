package com.example.twotwoninezero.dashboard.bottomnavigation.fleet

import android.os.Bundle
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
import com.example.twotwoninezero.common.FleetBusinessnameSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.business.BusinessScreenFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.adapter.FleetListAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.model.FleetViewModel
import com.example.twotwoninezero.service.GetBusinessNameResponse
import com.example.twotwoninezero.service.GetFleetListResponse
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.delete_or_activate_by_businessid.*
import kotlinx.android.synthetic.main.fragment_fleet_screen.*

class FleetScreenFragment : BaseFragment() {
    private var customDialog: AlertDialog?=null
    private lateinit var mFleetViewModel : FleetViewModel
    override fun initViewModel() {
        mFleetViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FleetViewModel::class.java)
        setViewModel(mFleetViewModel)

        mFleetViewModel.mGetBusinessNameList.observe(this, Observer {
            mgetBusinessNameList = it
            if (mgetBusinessNameList.isEmpty()){
                addNewFleetText.setText("Add New Business")
            }else{
                addNewFleetText.setText("Add New Fleet")
            }
        })

        mFleetViewModel.mDeleteFleetResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                customDialog?.dismiss()
                if (!edt_tv_searchByBusinessNameId.isNullOrEmpty()){
                    mFleetViewModel.getfleetlist(edt_tv_searchByBusinessNameId!!)
                }
            }else{
                customDialog?.dismiss()
                showToast(it.message)
            }
        })
        mFleetViewModel.mGetFleetListResponse.observe(this, Observer {
            mGetFleetList = it
            if (mGetFleetList.isEmpty()){
                emptyFleetList?.visibility=View.VISIBLE
                fleetListRV?.visibility=View.GONE
            }else{
                emptyFleetList?.visibility=View.GONE
                fleetListRV?.visibility=View.VISIBLE
            }

            mFleetListAdapter = FleetListAdapter(mGetFleetList){ fleetId,businessName,weight, type->
                if (type==1){
                    findNavController().navigate(
                        FleetScreenFragmentDirections.actionFleetScreenFragmentToAddFleetFragment(
                            fleetId,businessName,weight
                        )
                    )
                }else{
                    deleteFleetById(fleetId)
                    //mBusinessViewModel.archiveBusinesss(businessId)
                }

            }

            val mLayoutManager = LinearLayoutManager(requireContext())
            fleetListRV?.layoutManager = mLayoutManager
            fleetListRV?.adapter = mFleetListAdapter
        })

    }

    private fun deleteFleetById(fleetId: String) {
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
            mFleetViewModel.deleteFleetlineItem(fleetId)
            customDialog?.dismiss()
        }

    }

    var mFleetBusinessnameSpinnerAdapter: FleetBusinessnameSpinnerAdapter?=null
    private var mgetBusinessNameList: List<GetBusinessNameResponse> = ArrayList()
    private var mGetFleetList: List<GetFleetListResponse> = ArrayList()
    var edt_tv_searchByBusinessNameId:String?=null
    var mFleetListAdapter: FleetListAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edt_tv_searchByBusinessName?.setFocusable(false)
        edt_tv_searchByBusinessName?.setClickable(true)

        //  mFleetViewModel.getFleetById("")
        //  mFleetViewModel.getfleetlist("")
        mFleetViewModel.getbusinessname()
        if (!edt_tv_searchByBusinessNameId.isNullOrEmpty()){
            mFleetViewModel.getfleetlist(edt_tv_searchByBusinessNameId!!)
        }

        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }

        addNewFleet?.setOnClickListener {

            if (addNewFleetText.text.toString().equals("Add New Business")){
                findNavController().navigate(
                    FleetScreenFragmentDirections.actionFleetScreenFragmentToAddNewBusinessFragment(
                        ""
                    )
                )
            }else{
                findNavController().navigate(
                    FleetScreenFragmentDirections.actionFleetScreenFragmentToAddFleetFragment("","","")
                )
            }

        }

        edt_tv_searchByBusinessName?.setOnClickListener {
            showBusinessName()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fleet_screen, container, false)

        return view
    }

    private fun showBusinessName() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select BusinessName"

        mFleetBusinessnameSpinnerAdapter= FleetBusinessnameSpinnerAdapter(mgetBusinessNameList){ businessname, id->
            edt_tv_searchByBusinessName?.setText(businessname)
            edt_tv_searchByBusinessNameId=id
            mFleetViewModel.getfleetlist(edt_tv_searchByBusinessNameId!!)
            customDialog?.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mFleetBusinessnameSpinnerAdapter

        cancel.setOnClickListener {
            edt_tv_searchByBusinessName?.setText("")
            edt_tv_searchByBusinessNameId=""
            customDialog?.dismiss()
        }
        ok.setOnClickListener {
            customDialog?.dismiss()
        }
    }

}