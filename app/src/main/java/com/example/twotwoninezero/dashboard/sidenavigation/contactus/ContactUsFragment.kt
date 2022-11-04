package com.example.twotwoninezero.dashboard.sidenavigation.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment

class ContactUsFragment : BaseFragment() {

    override fun initViewModel() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var contactUsEnglishCall:LinearLayout?=null
    var contactUsSpanishCall:LinearLayout?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.contact_us, container, false)
        contactUsEnglishCall= view.findViewById(R.id.contactUsEnglishCall)
        contactUsSpanishCall= view.findViewById(R.id.contactUsSpanishCall)

        contactUsEnglishCall?.setOnClickListener {
            callFunction("8680003843")
        }
        contactUsSpanishCall?.setOnClickListener {
            callFunction("8680003843")
        }

        return view
    }


}