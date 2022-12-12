package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment


class DownloadExcelFormateFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_download_excel_formate, container, false)
    }

}