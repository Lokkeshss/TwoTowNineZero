package com.example.twotwoninezero.dashboard.sidenavigation.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import kotlinx.android.synthetic.main.about_us.*
import kotlinx.android.synthetic.main.common_header_loginsignup.*

class AboutUsFragment : BaseFragment() {
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
        val view = inflater.inflate(R.layout.about_us, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutUsfragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }
    }

}