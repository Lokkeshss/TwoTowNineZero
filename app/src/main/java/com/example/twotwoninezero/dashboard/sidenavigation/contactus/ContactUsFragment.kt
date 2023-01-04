package com.example.twotwoninezero.dashboard.sidenavigation.contactus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import kotlinx.android.synthetic.main.about_us.*
import kotlinx.android.synthetic.main.common_header_loginsignup.*
import kotlinx.android.synthetic.main.contact_us.*


class ContactUsFragment : BaseFragment() {

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
        val view = inflater.inflate(R.layout.contact_us, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactUsEnglishCall?.setOnClickListener {
            callFunction("8680003843")
        }
        contactUsSpanishCall?.setOnClickListener {
            callFunction("8680003843")
        }
        webView.getSettings().setJavaScriptEnabled(true)
        webView.setWebViewClient(WebViewClient())
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
       // webView.loadUrl("http://maps.google.com/maps?" + "saddr=43.0054446,-87.9678884" + "&daddr=42.9257104,-88.0508355")
        webView.loadUrl("https://www.google.com/maps/place/4380+Redwood+Hwy,+San+Rafael,+CA+94903,+USA/@38.016883,-122.540105,17z/data=!4m5!3m4!1s0x8085978a2e8ddfcb:0xf07edf8babb31b05!8m2!3d38.0168833!4d-122.5401047?hl=en")

        contactUsfragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        commonContactCallMain?.setOnClickListener {
            commonCallAndMailFunction()
        }
    }


}