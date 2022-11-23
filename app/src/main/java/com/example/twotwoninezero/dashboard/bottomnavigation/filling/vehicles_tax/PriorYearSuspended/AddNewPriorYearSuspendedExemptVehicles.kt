package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.PriorYearSuspended

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.model.FillingViewModel
import com.example.twotwoninezero.dashboard.bottomnavigation.filling.taxyear_and_forms.TaxYearAndFormFragment.Companion.filingId
import com.example.twotwoninezero.service.SavePriorSuspendedRequest
import com.example.twotwoninezero.service.UpdatePriorSuspendedRequest
import kotlinx.android.synthetic.main.fragment_add_new_prior_year_suspended_exempt_vehicles.*
import kotlinx.android.synthetic.main.fragment_add_new_reporting_suspended_exempt_vehicles.*
import java.text.SimpleDateFormat
import java.util.*


class AddNewPriorYearSuspendedExemptVehicles : BaseFragment() {
    private lateinit var mFillingViewModel : FillingViewModel

    var ifsoldDate=false
    var id:String?=null
    var soldDate=""
    var soldDateViewModel=""
    var soldToWhomViewModel=""
    var isExceedMilage="N"
    var isVehicleSold="N"
    val myCalendarToDate = Calendar.getInstance()

    override fun initViewModel() {
        mFillingViewModel = ViewModelProvider(
            viewModelStore,
            defaultViewModelProviderFactory
        ).get(FillingViewModel::class.java)
        setViewModel(mFillingViewModel)

        mFillingViewModel.mSavePriorSuspendedResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })
        mFillingViewModel.mUpdatePriorSuspendedResponse.observe(this, Observer {
            if (it.code==200){
                showToast(it.message)
                requireActivity().onBackPressed()
            }else{
                showToast(it.message)
            }
        })

        mFillingViewModel.mGetPriorSuspendedByIdResponse.observe(this, Observer {
            addNewPriorYearVin.setText(it.vin)
            soldToWhomViewModel=it.soldToWhom
            addNewPriorYearSoldTransferred.setText(soldToWhomViewModel)
            soldDateViewModel=it.soldDate
            soldDate=soldDateViewModel
            addNewPriorYearDateOfTransfer.setText(soldDateViewModel)
            isExceedMilage=it.isExceededMileage
            isVehicleSold=it.isVehicleSold


            if (it.isExceededMileage.equals("N")){
                // black and block
                addNewPriorYearDateOfTransferTL.setBackgroundResource(R.color.blacklignt)
                addNewPriorYearSoldTransferredTL.setBackgroundResource(R.color.blacklignt)
                ifsoldDate=false
                addNewPriorYearSoldTransferred?.isEnabled=false
              //  addNewPriorYearSoldTransferred?.isClickable=true
            }else{
                // no black and no block
                addNewPriorYearDateOfTransferTL.setBackgroundResource(R.color.white)
                addNewPriorYearSoldTransferredTL.setBackgroundResource(R.color.white)
                ifsoldDate=true
                addNewPriorYearSoldTransferred?.isEnabled=true
               // addNewPriorYearSoldTransferred?.isClickable=false
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
            R.layout.fragment_add_new_prior_year_suspended_exempt_vehicles,
            container,
            false
        )


        //savePriorSuspended
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNewPriorYearDateOfTransfer?.isFocusable = false
        addNewPriorYearDateOfTransfer?.isClickable = true


        arguments?.let {

            id = it.getString("id").toString()

            if (id != null && !id.isNullOrEmpty()) {
                mFillingViewModel.getPriorSuspendedById(id!!, filingId.toString())
                addNewPriorYearSubmit.setText("Update")
            }

        }

        addNewPriorYearDateOfTransferTL.setBackgroundResource(R.color.blacklignt)
        addNewPriorYearSoldTransferredTL.setBackgroundResource(R.color.blacklignt)
        ifsoldDate=false
        addNewPriorYearSoldTransferred?.isEnabled=false
      //  addNewPriorYearSoldTransferred?.isClickable=true


        addNewPriorYearExceeded.setOnClickListener {
        // black and block
            addNewPriorYearDateOfTransferTL.setBackgroundResource(R.color.blacklignt)
            addNewPriorYearSoldTransferredTL.setBackgroundResource(R.color.blacklignt)
            ifsoldDate=false
            addNewPriorYearSoldTransferred?.isEnabled=false
           // addNewPriorYearSoldTransferred?.isClickable=true
            addNewPriorYearDateOfTransfer.setText("")
            addNewPriorYearSoldTransferred.setText("")
            isExceedMilage="Y"
            isVehicleSold="N"
            soldDate=""
        }

        addNewPriorYearSold.setOnClickListener {
        // no black and no block
            addNewPriorYearDateOfTransferTL.setBackgroundResource(R.color.white)
            addNewPriorYearSoldTransferredTL.setBackgroundResource(R.color.white)
            ifsoldDate=true

            addNewPriorYearSoldTransferred?.isEnabled=true
          //  addNewPriorYearSoldTransferred?.isClickable=false
            isExceedMilage="N"
            isVehicleSold="Y"
            soldDate=soldDateViewModel
            addNewPriorYearSoldTransferred.setText(soldToWhomViewModel)
            addNewPriorYearDateOfTransfer.setText(soldDateViewModel)
        }

        val toDate: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: android.widget.DatePicker?,
                year: Int,
                month: Int,
                day: Int
            ) {
                myCalendarToDate[Calendar.YEAR] = year
                myCalendarToDate[Calendar.MONTH] = month
                myCalendarToDate[Calendar.DAY_OF_MONTH] = day
                updateLabelToDate()
            }
        }

        addNewPriorYearDateOfTransfer.setOnClickListener {
            if (ifsoldDate){
                DatePickerDialog(
                    requireContext(),
                    toDate,
                    myCalendarToDate[Calendar.YEAR],
                    myCalendarToDate[Calendar.MONTH],
                    myCalendarToDate[Calendar.DAY_OF_MONTH]
                ).show()
            }

        }



        addNewPriorYearSubmit.setOnClickListener {
            if (addNewPriorYearVin.text.toString().isNullOrEmpty()){
                showToast("Enter VIN number")
            }else if (addNewPriorYearVin.text.toString().length<17){
                showToast("VIN must be at least 17 characters long.")
            }else{

                if (addNewPriorYearSubmit.text.toString().equals("Save ")){
                    val i = SavePriorSuspendedRequest(
                        filingId,isExceedMilage,
                        isVehicleSold,soldDate,
                        addNewPriorYearSoldTransferred.text.toString(),addNewPriorYearVin.text.toString())
                    mFillingViewModel.savePriorSuspended(filingId,i)
                }else{
                    val i = UpdatePriorSuspendedRequest(
                        isExceedMilage,
                        isVehicleSold,soldDate,
                        addNewPriorYearSoldTransferred.text.toString(),addNewPriorYearVin.text.toString())
                    mFillingViewModel.updatePriorSuspended(id.toString(), filingId,i)
                }

            }

        }

        addNewPriorYearCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun updateLabelToDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        addNewPriorYearDateOfTransfer?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy-dd-MM"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        soldDate=dateFormatS.format(myCalendarToDate.time)
       // filterToDateValue=dateFormatS.format(myCalendarFromDate.time)
    }


}