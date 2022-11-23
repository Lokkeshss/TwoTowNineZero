package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.creditforanapproval

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.CreditForAnOverPaymentAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.SoldDestroyedAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.solddestroyedstolenvehicle.SoldDestroyedorStolenVehicleFragmentDirections
import kotlinx.android.synthetic.main.fragment_credit_for_an_overpayment_of_tax.*
import kotlinx.android.synthetic.main.fragment_sold_destroyedor_stolen_vehicle.*


class CreditForAnOverpaymentOfTaxFragment : BaseFragment() {
    private lateinit var mFillingViewModel: FillingViewModel
    var mCreditForAnOverPaymentAdapter: CreditForAnOverPaymentAdapter?=null
    private var customDialog: AlertDialog?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(viewModelStore,defaultViewModelProviderFactory).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mGetCreditOverPaymentByFilingIdResponse.observe(this, Observer {
            mCreditForAnOverPaymentAdapter= CreditForAnOverPaymentAdapter(it){id, requestType->
                if (requestType==0){
                    // delete
                    deleteOrReactiveFilingId(id, TaxYearAndFormFragment.filingId)
                }else if (requestType==1){
                    // edit
                    findNavController().navigate(
                        CreditForAnOverpaymentOfTaxFragmentDirections.actionCreditForAnOverpaymentOfTaxFragmentToAddNewCreditForAnOverpaymentOfTaxFragment(id, filingId)
                    )
                }
            }
            val mLayoutManager = LinearLayoutManager(requireContext())
            creditForAnOverpaymentRecyclerView?.layoutManager = mLayoutManager
            creditForAnOverpaymentRecyclerView?.adapter = mCreditForAnOverPaymentAdapter
        })

        mFillingViewModel.mDeleteCreditOverPaymentById.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                mFillingViewModel.getCreditOverPaymentByFilingId(TaxYearAndFormFragment.filingId)
            }else{
                showToast(it.message)
            }

            customDialog?.dismiss()
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
            R.layout.fragment_credit_for_an_overpayment_of_tax,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFillingViewModel.getCreditOverPaymentByFilingId(TaxYearAndFormFragment.filingId)

        creditForAnOverpaymentAddNewVechicle.setOnClickListener {
            findNavController().navigate(CreditForAnOverpaymentOfTaxFragmentDirections.actionCreditForAnOverpaymentOfTaxFragmentToAddNewCreditForAnOverpaymentOfTaxFragment("",""))
        }

    }

    private fun deleteOrReactiveFilingId(businessId: String, filingId: String) {
        val dialogView = layoutInflater.inflate(R.layout.delete_or_activate_by_businessid, null)

        customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog?.setCancelable(false)

        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val delete = dialogView.findViewById<TextView>(R.id.delete)
        val deleteText = dialogView.findViewById<TextView>(R.id.textfields)
        val deleteIcon = dialogView.findViewById<ImageView>(R.id.icons)

        cancel.setOnClickListener {

            customDialog?.dismiss()
        }

        delete.setOnClickListener {
            mFillingViewModel.deleteCreditOverPaymentById(businessId,filingId)
        }
    }


}