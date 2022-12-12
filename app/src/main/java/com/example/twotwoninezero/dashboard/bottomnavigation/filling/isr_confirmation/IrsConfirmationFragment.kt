package com.example.twotwoninezero.dashboard.bottomnavigation.filling.isr_confirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import kotlinx.android.synthetic.main.progress_bar_view.*


class IrsConfirmationFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_irs_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress_bar.progress=100
        progress_text.setText("6 of 6")

    }

}