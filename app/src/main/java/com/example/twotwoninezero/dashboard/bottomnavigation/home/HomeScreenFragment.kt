package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.home.model.DashBoardViewModel

class HomeScreenFragment : BaseFragment() {
    private lateinit var mDashBoardViewModel: DashBoardViewModel

    override fun initViewModel() {
        mDashBoardViewModel = ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(
            DashBoardViewModel::class.java
        )
        setViewModel(mDashBoardViewModel)
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


}