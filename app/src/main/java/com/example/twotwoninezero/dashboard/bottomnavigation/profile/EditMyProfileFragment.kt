package com.example.twotwoninezero.dashboard.bottomnavigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.business.model.BusinessViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.profile.model.ProfileViewModel
import com.example.twotwoninezero.service.UpdateMyAccountDetailsRequest
import kotlinx.android.synthetic.main.fragment_edit_my_profile.*


class EditMyProfileFragment : BaseFragment() {

    private lateinit var mProfileViewModel : ProfileViewModel
    override fun initViewModel() {
        mProfileViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(ProfileViewModel::class.java)
        setViewModel(mProfileViewModel)

        mProfileViewModel.mGetMyAccountDetailsResponse.observe(this, Observer {
            if (it.lastName==null){
                editProfileFullName.setText(it.firstName)
            }else{
                editProfileFullName.setText(it.firstName+" "+it.lastName)
            }

            editProfileContactNumber.setText(it.phone)
            editProfileEmailAddress.setText(it.email)
            editProfileAddress.setText(it.address)
            editProfileZipCode.setText(it.pinCode)
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
        return inflater.inflate(R.layout.fragment_edit_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProfileViewModel.getMyAccountDetails()

        editProfilefragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        editProfileEmailAddress.isEnabled=false

        editProfileUpdate.setOnClickListener {
                if (editProfileFullName.text.toString().isNullOrEmpty()){
                    showToast("Name is required.")
                }else if (editProfileContactNumber.text.toString().isNullOrEmpty()){
                    showToast("Contact Number is required.")
                }else if (editProfileContactNumber.text.toString().length<4){
                    showToast("Contact Number must be at least 4 characters long.")
                }else if (editProfileAddress.text.toString().isNullOrEmpty()){
                    showToast("Address is required.")
                }else if (editProfileAddress.text.toString().length<2){
                    showToast("Address must be at least 2 characters long.")
                }else if (editProfileZipCode.text.toString().isNullOrEmpty()){
                    showToast("Zip Code is required.")
                }else if (editProfileZipCode.text.toString().isNullOrEmpty()){
                    showToast("Zip Code must be at least 5 characters long.")
                }else{
                    val i= UpdateMyAccountDetailsRequest(editProfileAddress.text.toString(),0,"","","",
                        editProfileEmailAddress.text.toString(),editProfileEmailAddress.text.toString(),"",editProfileFullName.text.toString(),
                        "N","", "N","","","",editProfileContactNumber.text.toString(),editProfileZipCode.text.toString(),
                        "","")

                    mProfileViewModel.updateUser(i)
                }


        }

        editProfileCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}