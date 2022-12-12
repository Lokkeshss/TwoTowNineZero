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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter.TypeOfLossAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.form_summary.filingformviewmodel.FIllingFormSummaryViewModel
import com.example.twotwoninezero.service.SavePaymentOptionRequest
import kotlinx.android.synthetic.main.fragment_add_new_sold_destroyedor_stolen_vehicle.*
import kotlinx.android.synthetic.main.fragment_i_r_s_payment_options.*
import kotlinx.android.synthetic.main.progress_bar_view.*


class IRSPaymentOptionsFragment : BaseFragment() {
    var filingId:String=""
    var formTYpe:String=""
    var paymentMode:String=""
    var paymentId:String=""
    var TypeOfAccount = listOf<String>("Savings","Checking")
    private lateinit var mFillingViewModel : FIllingFormSummaryViewModel
    var mTypeOfLossAdapter: TypeOfLossAdapter?=null
    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FIllingFormSummaryViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mPaymentOptionMethodResponse.observe(this, Observer {

            irsPaymentOptionEFWCheckBox.isChecked=it.paymentMode.equals("Direct debit")
            irsPaymentOptionCreditCardCheckbox.isChecked=it.paymentMode.equals("EFTPS")
            irsPaymentOptionEFWTaxPaymentCheckbox.isChecked=it.paymentMode.equals("Credit card")

            paymentMode=it.paymentMode
            paymentId=it.id.toString()

            if (it.paymentMode.equals("Direct debit")){
                irsPaymentOptionEFWChild.visibility=View.VISIBLE
                irsPaymentOptionCreditCardChild.visibility=View.GONE
                irsPaymentOptionEFTTaypaymentChild.visibility=View.GONE

                irsPaymentOptionBankName.setText(it.bankName)
                irsPaymentOptionAccountType.setText(it.acctType)
                irsPaymentOptionAccountNumber.setText(it.acctNumber)
                irsPaymentOptionConfirmBankAccount.setText(it.retypebankAccNo)
                irsPaymentOptionRoutingTransitNumber.setText(it.routingTransitNo)
            }else if (it.paymentMode.equals("EFTPS")){
                irsPaymentOptionEFWChild.visibility=View.GONE
                irsPaymentOptionCreditCardChild.visibility=View.VISIBLE
                irsPaymentOptionEFTTaypaymentChild.visibility=View.GONE
            }else if (it.paymentMode.equals("Credit card")){
                irsPaymentOptionEFWChild.visibility=View.GONE
                irsPaymentOptionCreditCardChild.visibility=View.GONE
                irsPaymentOptionEFTTaypaymentChild.visibility=View.VISIBLE
            }

        })

        mFillingViewModel.mSavePaymentOptionResponse.observe(this, Observer {
            if (it.code==200){
                findNavController().navigate(IRSPaymentOptionsFragmentDirections.actionIRSPaymentOptionsFragmentToFormSummaryFragment(filingId))
                showToast(it.message)
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetSummaryDetailsByFilingIdResponse.observe(this, Observer {

           /* if (it.irsPayment!!.equals("{}")){

            }else{

            }*/
            formTYpe =it.filingInfo.formType

            if (!it.irsPayment!!.paymentMode.isNullOrEmpty()){
                mFillingViewModel.getPaymentOptionByFilingId(filingId)
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
        return inflater.inflate(R.layout.fragment_i_r_s_payment_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            filingId = it.getString("filingId").toString()

            if (filingId != null && filingId.isNotEmpty()) {

                mFillingViewModel.GetSummaryDetailsByFilingIdResponse(filingId)

            }

        }

        progress_bar.progress=49
        progress_text.setText("3 of 6")

        irsPaymentOptionAccountType.isFocusable=false
        irsPaymentOptionAccountType.isClickable=true

        irsPaymentOptionAccountType.setOnClickListener {
            showAdapterList()
        }

        irsPaymentOptionEFWCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        paymentMode="Direct debit"
                        irsPaymentOptionCreditCardCheckbox.isChecked=false
                        irsPaymentOptionEFWTaxPaymentCheckbox.isChecked=false

                        irsPaymentOptionEFWChild.visibility=View.VISIBLE
                        irsPaymentOptionCreditCardChild.visibility=View.GONE
                        irsPaymentOptionEFTTaypaymentChild.visibility=View.GONE
                    }
        }

        irsPaymentOptionCreditCardCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                paymentMode="EFTPS"
                irsPaymentOptionEFWCheckBox.isChecked=false
                irsPaymentOptionEFWTaxPaymentCheckbox.isChecked=false

                irsPaymentOptionEFWChild.visibility=View.GONE
                irsPaymentOptionCreditCardChild.visibility=View.VISIBLE
                irsPaymentOptionEFTTaypaymentChild.visibility=View.GONE
            }
        }

        irsPaymentOptionEFWTaxPaymentCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                paymentMode="Credit card"
                irsPaymentOptionEFWCheckBox.isChecked=false
                irsPaymentOptionCreditCardCheckbox.isChecked=false

                irsPaymentOptionEFWChild.visibility=View.GONE
                irsPaymentOptionCreditCardChild.visibility=View.GONE
                irsPaymentOptionEFTTaypaymentChild.visibility=View.VISIBLE

            }
        }


        paymentOptionSubmit.setOnClickListener {


            if (paymentMode.equals("Direct debit")){
                if (irsPaymentOptionBankName.text.toString().isNullOrEmpty()){
                    showToast("Enter Bank Name is required")
                }else if (irsPaymentOptionAccountType.text.toString().isNullOrEmpty()){
                    showToast("Select Account Type")
                }else if (irsPaymentOptionAccountNumber.text.toString().isNullOrEmpty()){
                    showToast("Bank Account Number is required")
                }else if (irsPaymentOptionConfirmBankAccount.text.toString().isNullOrEmpty()){
                    showToast("Retype Bank Account Number is required")
                }else if (irsPaymentOptionAccountNumber.text.toString().equals(irsPaymentOptionConfirmBankAccount.text.toString())){
                    showToast("Account Number and Retype Account Number should be same")
                }else if (irsPaymentOptionRoutingTransitNumber.text.toString().isNullOrEmpty()){
                    showToast("Routing Transit Number is required")
                }else if (irsPaymentOptionRoutingTransitNumber.text.toString().length<9){
                    showToast("Please enter your 9 digit Routing Transit Number")
                }else{
                    val i = SavePaymentOptionRequest(irsPaymentOptionAccountNumber.text.toString(),
                        irsPaymentOptionAccountType.text.toString(),irsPaymentOptionBankName.text.toString(),
                        "","","false",filingId,paymentId,paymentMode,
                        irsPaymentOptionConfirmBankAccount.text.toString(),irsPaymentOptionRoutingTransitNumber.text.toString())

                    mFillingViewModel.savePaymentOption(filingId,i)
                }
            }else{
                val i = SavePaymentOptionRequest(irsPaymentOptionAccountNumber.text.toString(),
                    irsPaymentOptionAccountType.text.toString(),irsPaymentOptionBankName.text.toString(),
                    "","","false",filingId,paymentId,paymentMode,
                    irsPaymentOptionConfirmBankAccount.text.toString(),irsPaymentOptionRoutingTransitNumber.text.toString())

                mFillingViewModel.savePaymentOption(filingId,i)
            }

        }

        paymentOptionCancel.setOnClickListener {

            findNavController().navigate(IRSPaymentOptionsFragmentDirections.actionIRSPaymentOptionsFragmentToTaxableVehicleInformation(filingId,formTYpe))

        }

    }

    private fun showAdapterList() {

        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)

        title.text="Account Type"

        mTypeOfLossAdapter = TypeOfLossAdapter(TypeOfAccount){ type->
            irsPaymentOptionAccountType?.setText(type)
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mTypeOfLossAdapter

        cancel.setOnClickListener {
            customDialog.dismiss()
        }
        ok.setOnClickListener {
            customDialog.dismiss()
        }
    }


}