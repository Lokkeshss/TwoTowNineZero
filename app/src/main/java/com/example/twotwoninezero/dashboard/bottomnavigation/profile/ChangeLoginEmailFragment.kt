package com.example.twotwoninezero.dashboard.bottomnavigation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.model.ProfileViewModel
import com.example.twotwoninezero.loginsignup.LoginScreenActivity
import com.example.twotwoninezero.service.ChangeLoginEmailRequest
import kotlinx.android.synthetic.main.fragment_change_login_email.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_edit_my_profile.*


class ChangeLoginEmailFragment : BaseFragment() {
    private lateinit var mProfileViewModel : ProfileViewModel
    var requestName=""
    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
        setViewModel(mProfileViewModel)


        mProfileViewModel.mGetMyAccountDetailsResponse.observe(this, Observer {
           requestName=it.firstName
            changeLoginEmailRegistered.setText(it.email)
        })

        mProfileViewModel.mChangeLoginEmailResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                val i= Intent(requireActivity(), LoginScreenActivity::class.java)
                startActivity(i)
                requireActivity().finish()
            }else{
                showToast(it.message)
            }
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
        return inflater.inflate(R.layout.fragment_change_login_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProfileViewModel.getMyAccountDetails()
        changeLoginEmailRegistered.isEnabled=false


        changeLoginEmailSubmit.setOnClickListener {

        if (changeLoginEmailNew.text.toString().isNullOrEmpty()){
            showToast("New email address is required.")
        }else if (changeLoginEmailNew.text.toString().length<4){
            showToast("New email address must be at least 4 characters long.")
        }else if (changeLoginEmailConfirm.text.toString().isNullOrEmpty()){
            showToast("Confirm email address is required.")
        }else if (changeLoginEmailRegistered.text.toString().equals(changeLoginEmailConfirm.text.toString())){
            showToast("Registered Email Id and New Email Id must not be same.")
        }else if (changeLoginEmailConfirm.text.toString().length<4){
            showToast("Confirm email address must be at least 4 characters long.")
        }else{
            val i = ChangeLoginEmailRequest(changeLoginEmailConfirm.text.toString(),changeLoginEmailRegistered.text.toString(),
                changeLoginEmailNew.text.toString(),requestName)
            mProfileViewModel.changeLoginEmail(i)
        }

        }
        changeLoginEmailCancel.setOnClickListener {
          requireActivity().onBackPressed()
        }

        changeLoginEmailfragmentBack.setOnClickListener {
          requireActivity().onBackPressed()
        }



    }

}