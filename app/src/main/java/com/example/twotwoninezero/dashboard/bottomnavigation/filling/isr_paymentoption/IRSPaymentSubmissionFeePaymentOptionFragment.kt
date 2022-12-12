package com.example.twotwoninezero.dashboard.bottomnavigation.filling.isr_paymentoption

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.common.CountryListSpinnerAdapter
import com.example.twotwoninezero.common.StateListSpinnerAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import com.example.twotwoninezero.service.CaptureCCPaymentRequest
import com.example.twotwoninezero.service.GetCountryItem
import com.example.twotwoninezero.service.GetStateReponse
import kotlinx.android.synthetic.main.fragment_add_new_business.*
import kotlinx.android.synthetic.main.fragment_i_r_s_payment_submission_fee_payment_option.*

class IRSPaymentSubmissionFeePaymentOptionFragment : BaseFragment() {
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var filingId:String=""
    var diffBillAdd:Boolean=false
    var isCreditDetails:Boolean=true
    var isDebitDetails:Boolean=false
    var paymentAmount:Double=0.0
    var refId:String=""
    var paymentOptionCountryID:String=""
    var paymentOptionStateId:String=""
    var selectedPaymentMethod:String=""
    var mGetStateReponse:List<GetStateReponse> = ArrayList()
    var mgetCountryAdapterList:List<GetCountryItem> = ArrayList()
    var mCountryListSpinnerAdapter: CountryListSpinnerAdapter?=null
    var mStateListSpinnerAdapter: StateListSpinnerAdapter?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)


        mFillingViewModel.mGetCountry.observe(this, Observer {
            mgetCountryAdapterList=it
        })

        mFillingViewModel.mGetStateReponse.observe(this, Observer {
            mGetStateReponse=it

        })

        mFillingViewModel.mGetPaymentDetResponse.observe(this, Observer {
            paymentAmount=it.amount
            refId=it.refId
            paymentOptionSubmit.setText("Pay $"+it.amount)
        })

        mFillingViewModel.mCaptureCCPaymentResponse.observe(this, Observer {
                if (it.status.equals("success")){
                    showToast(it.resDescription)
                }else{
                    showToast(it.resDescription)
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
            R.layout.fragment_i_r_s_payment_submission_fee_payment_option,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {
                mFillingViewModel.getPaymentDet(filingId)
                mFillingViewModel.getCountry()

               // mFillingViewModel.getCountryState()
            }
        }

        paymentOptionRG.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId==R.id.paymentOptionCreditCardRB){
                isCreditDetails=true
                isDebitDetails=false
                selectedPaymentMethod="creditcard"
                creditCardView.visibility=View.VISIBLE
                bankAccountDetails.visibility=View.GONE
                clearText()
            }else{
                isCreditDetails=false
                isDebitDetails=true
                selectedPaymentMethod="debitCard"
                creditCardView.visibility=View.GONE
                bankAccountDetails.visibility=View.VISIBLE
                clearText()
            }

        }

        paymentOptionBillingAddressChekBox.setOnCheckedChangeListener { buttonView, isChecked ->
            diffBillAdd = isChecked
            if (isChecked){
                paymentOptionViewAddress.visibility=View.VISIBLE
            }else{
                paymentOptionViewAddress.visibility=View.GONE
            }
        }
111
        paymentOptionPrevious.setOnClickListener {

        }

        paymentOptionSubmit.setOnClickListener {

            val i = CaptureCCPaymentRequest(paymentOptionAccountNumber.text.toString(),"",paymentOptionAccountType.text.toString(),
                paymentOptionBankName.text.toString(),paymentOptionBillingAddress.text.toString(),paymentOptionCVV.text.toString(),
                paymentOptionCardNumber.text.toString(),paymentOptionCity.text.toString(),paymentOptionCountryID,diffBillAdd,
                paymentOptionExp.text.toString(),paymentOptionFirstName.text.toString(),isCreditDetails,
                isDebitDetails,paymentOptionLastName.text.toString(),paymentOptionNameOnAccount.text.toString(),
                paymentAmount,refId,paymentOptionRoutingNumber.text.toString(),
                selectedPaymentMethod,paymentOptionStateId,paymentOptionZipCode.text.toString())

            mFillingViewModel.captureCCPayment(filingId,i)

        }

        paymentOptionCountry.setOnClickListener {
            contactInfoCountry()
        }

        paymentOptionState.setOnClickListener {
            contactState()
        }

    }

    fun clearText(){
        paymentOptionCardNumber.setText("")
        paymentOptionExp.setText("")
        paymentOptionCVV.setText("")
        paymentOptionBankName.setText("")
        paymentOptionNameOnAccount.setText("")
        paymentOptionAccountNumber.setText("")
        paymentOptionAccountType.setText("")
        paymentOptionRoutingNumber.setText("")
    }

    private fun contactInfoCountry() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Country"

        mCountryListSpinnerAdapter= CountryListSpinnerAdapter(mgetCountryAdapterList){ country, id->
            paymentOptionCountry?.setText(country)
            paymentOptionCountryID=id
            paymentOptionState?.setText("")
            paymentOptionStateId=""

            mFillingViewModel.getCountryState(paymentOptionCountryID.toString())
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mCountryListSpinnerAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {

            customDialog.dismiss()
        }
    }

    private fun contactState(){
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select State"
        mStateListSpinnerAdapter= StateListSpinnerAdapter(mGetStateReponse){ country, id->
            paymentOptionState?.setText(country)
            paymentOptionStateId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mStateListSpinnerAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }

}