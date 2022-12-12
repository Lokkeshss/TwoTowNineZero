package com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.home.HomeScreenFragment
import kotlinx.android.synthetic.main.fragment_form_summary_business_information.*


class FormSummaryBusinessInformationFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    var businessId:String=""
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {
            businessId=it.businessInfo.id.toString()
            formSummaryBusinessName.setText(it.businessInfo.businessName)
            formSummaryBusinessEin.setText(it.businessInfo.ein)
            formSummaryBusinessType.setText(it.businessInfo.bizType)

            formSummaryBusinessAddress.setText(it.businessInfo.address1+","+","+
                    it.businessInfo.address2+","+
                    it.businessInfo.city+","+
                    it.businessInfo.stateName+","+
                    it.businessInfo.countryName+","+
                    it.businessInfo.zipCode+",")

            formSummaryBusinessPhoneNo.setText(it.businessInfo.decryptPhone)
            formSummaryBusinessEmailId.setText(it.businessInfo.decryptEmail)

            formSummaryBusinessauthName.setText(it.businessInfo.signingAuthorityName)
            formSummaryBusinessTitle.setText(it.businessInfo.signingAuthorityTitle)
            formSummaryBusinessauthPhoneNo.setText(it.businessInfo.signingAuthorityPhone)

            if (it.businessInfo.thirdpartyStatus==true){
                thridPartyDesignLL.visibility=View.VISIBLE
                formSummaryThirdPartyName.setText(it.businessInfo.thirdPartyDesigneeName)
                formSummaryPhoneNo.setText(it.businessInfo.thirdPartyDesigneePhone)
            }else{
                thridPartyDesignLL.visibility=View.GONE
                formSummaryThirdPartyName.setText("")
                formSummaryPhoneNo.setText("")
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
        return inflater.inflate(
            R.layout.fragment_form_summary_business_information,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)
            }
        }



        businessInfoEdit.setOnClickListener {
            if (businessId.isNullOrEmpty()){

            }else{
                findNavController().navigate(FormSummaryBusinessInformationFragmentDirections.actionFormSummaryBusinessInformationFragmentToAddNewBusinessFragment(
                    businessId
                ))
            }

        }
    }


}