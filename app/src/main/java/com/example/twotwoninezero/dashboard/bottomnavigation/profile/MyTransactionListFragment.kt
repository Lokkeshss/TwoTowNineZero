package com.example.twotwoninezero.dashboard.bottomnavigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.ExceededMileageVehiclesAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.exceededmileagevehicles.ExceededMileageVehiclesFragmentDirections
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.adapter.MyTransactionAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.model.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_exceeded_mileage_vehicles.*
import kotlinx.android.synthetic.main.fragment_my_transaction_list.*


class MyTransactionListFragment : BaseFragment() {
    private lateinit var mProfileViewModel : ProfileViewModel
    var mMyTransactionAdapter: MyTransactionAdapter?=null
    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
        setViewModel(mProfileViewModel)

        mProfileViewModel.mGetTransactionDetailsuserIDResponse.observe(this, Observer {
            mMyTransactionAdapter= MyTransactionAdapter(it)
            val mLayoutManager = LinearLayoutManager(requireContext())
            myTransactionRecyclerView?.layoutManager = mLayoutManager
            myTransactionRecyclerView?.adapter = mMyTransactionAdapter
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
        return inflater.inflate(R.layout.fragment_my_transaction_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProfileViewModel.getTransactionDetailsuserID()


        myTransactionDownload.setOnClickListener {

        }

        myTransactionfragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

}