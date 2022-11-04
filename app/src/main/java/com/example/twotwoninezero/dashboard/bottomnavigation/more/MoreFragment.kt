package com.example.twotwoninezero.dashboard.bottomnavigation.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import android.graphics.BitmapFactory
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class MoreFragment :  BaseFragment() {

    var more_aboutus:LinearLayout?=null
    var more_contactus:LinearLayout?=null
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

        val view= inflater.inflate(R.layout.fragment_more, container, false)

        more_aboutus=view.findViewById(R.id.more_aboutus)
        more_contactus=view.findViewById(R.id.more_contactus)

        more_aboutus?.setOnClickListener {
            findNavController().navigate(
                MoreFragmentDirections.actionMorescreenFragmentToAboutUs()

            )
        }

        more_contactus?.setOnClickListener {
            findNavController().navigate(
                MoreFragmentDirections.actionMorescreenFragmentToContactUs()

            )
        }

        return view
    }




}