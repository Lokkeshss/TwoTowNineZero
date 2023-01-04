package com.example.twotwoninezero.dashboard.bottomnavigation.filling.isr_paymentoption

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import java.util.*
import kotlin.collections.ArrayList

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
                    findNavController().navigate(IRSPaymentSubmissionFeePaymentOptionFragmentDirections.
                    actionIRSPaymentSubmissionFeePaymentOptionFragmentToIrsConfirmationFragment(it.transactionId?:"",
                        it.refId?:"",
                        it.status?:"",
                        it.amount?:"","",""))
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

                if (isOnline()) {
                    mFillingViewModel.getCountry()
                }else{
                    showToast(getString(R.string.internet_required))
                }



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

        paymentOptionPrevious.setOnClickListener {

        }

        paymentOptionSubmit.setOnClickListener {

            val cardNumber = paymentOptionCardNumber.text.toString().filter { !it.isWhitespace() }

            val i = CaptureCCPaymentRequest(paymentOptionAccountNumber.text.toString(),"",paymentOptionAccountType.text.toString(),
                paymentOptionBankName.text.toString(),paymentOptionBillingAddress.text.toString(),paymentOptionCVV.text.toString(),
                cardNumber,paymentOptionCity.text.toString(),paymentOptionCountryID,diffBillAdd,
                paymentOptionExp.text.toString(),paymentOptionFirstName.text.toString(),isCreditDetails,
                isDebitDetails,paymentOptionLastName.text.toString(),paymentOptionNameOnAccount.text.toString(),
                paymentAmount,refId,paymentOptionRoutingNumber.text.toString(),
                selectedPaymentMethod,paymentOptionStateId,paymentOptionZipCode.text.toString())

            mFillingViewModel.captureCCPayment(filingId,i)

        }
        //var str = "This is an example text".filter { !it.isWhitespace() }

        paymentOptionCountry.setOnClickListener {
            contactInfoCountry()
        }

        paymentOptionState.setOnClickListener {
            contactState()
        }


        paymentOptionExp.addTextChangedListener(object : TextWatcher {

            private var current = ""
            private val ddmmyyyy = "MMYY"
            private val cal = Calendar.getInstance()

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var working = s.toString()
                var isValid = true
                if (working.length == 2 && before == 0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                        isValid = false
                    } else {
                        working += "/"
                        paymentOptionExp.setText(working)
                        paymentOptionExp.setSelection(working.length)
                    }
                } else if (working.length == 5 && before == 0) {
                    val enteredYear = working.substring(3)
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100//getting last 2 digits of current year i.e. 2018 % 100 = 18
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        isValid = false
                    }
                } else if (working.length != 5) {
                    isValid = false
                }

                if (!isValid) {
                    paymentOptionExp.error ="mm/yy"
                } else {
                    paymentOptionExp.error = null
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })

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