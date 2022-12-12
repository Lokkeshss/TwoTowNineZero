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
import com.example.twotwoninezero.service.ChangePasswordRequest
import kotlinx.android.synthetic.main.fragment_change_password.*


class ChangePasswordFragment : BaseFragment() {
    private lateinit var mProfileViewModel : ProfileViewModel
    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
        setViewModel(mProfileViewModel)

        mProfileViewModel.mChangePasswordResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                // need to finish and move to login screen
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
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePasswordUpdate.setOnClickListener {

            if (changePasswordCurrentPassword.text.toString().isNullOrEmpty()){
                showToast("Current password is required.")
            }else if (changePasswordCurrentPassword.text.toString().length<8){
                showToast("Current password must be at least 8 characters long.")
            }else if (changePasswordNewPassword.text.toString().isNullOrEmpty()){
                showToast("New password is required.")
            }else if (changePasswordNewPassword.text.toString().length<8){
                showToast("New password must be at least 8 characters long.")
            }else if (changePasswordConfirmPassword.text.toString().isNullOrEmpty()){
                showToast("Confirm password is required.")
            }else if (changePasswordConfirmPassword.text.toString().length<8) {
                showToast("Confirm password must be at least 8 characters long.")
            }else if (!changePasswordNewPassword.text.toString().equals(changePasswordConfirmPassword.text.toString())){
                showToast("Password Mismatch")
            }else{
                val i= ChangePasswordRequest(changePasswordConfirmPassword.text.toString(),
                    changePasswordCurrentPassword.text.toString(),
                    changePasswordNewPassword.text.toString())
                mProfileViewModel.changePassword(i)
            }
        }
        changePasswordCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        changePasswordfragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }

}