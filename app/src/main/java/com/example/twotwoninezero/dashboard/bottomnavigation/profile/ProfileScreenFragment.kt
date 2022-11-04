package com.example.twotwoninezero.dashboard.bottomnavigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment

class ProfileScreenFragment : BaseFragment() {
    override fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_profile_screen, container, false)

        return view
    }


}