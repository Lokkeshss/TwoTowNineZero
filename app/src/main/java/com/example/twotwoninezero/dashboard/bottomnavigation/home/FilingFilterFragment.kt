package com.example.twotwoninezero.dashboard.bottomnavigation.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.base.BaseFragment
import com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter.FilterCategoryAdapter
import com.example.twotwoninezero.dashboard.bottomnavigation.home.model.HomeViewModel
import com.example.twotwoninezero.service.FilingFilterRequest
import com.example.twotwoninezero.service.FilterCategory
import kotlinx.android.synthetic.main.fragment_filing_filter.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FilingFilterFragment : BaseFragment() {
    val myCalendarFromDate = Calendar.getInstance()
    val myCalendarToDate = Calendar.getInstance()
    var filterFromDateValue=""
    var filterToDateValue=""
    var filterSearchCategoryId=""
    var mFilterCategoryAdapter:FilterCategoryAdapter?=null
    var filterCategoryList : ArrayList<FilterCategory> = ArrayList<FilterCategory>()
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
        val view= inflater.inflate(R.layout.fragment_filing_filter, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterCategoryList.clear()
        val i = FilterCategory("Business Name","bname")
        filterCategoryList.add(i)
        val ii = FilterCategory("Business Email","bemail")
        filterCategoryList.add(ii)
        val iii = FilterCategory("Invoice Number","inumber")
        filterCategoryList.add(iii)

        fragmentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        filterFromDate?.isFocusable = false
        filterFromDate?.isClickable = true
        filterToDate?.isFocusable = false
        filterToDate?.isClickable = true
        filterSearchCategory?.isFocusable = false
        filterSearchCategory?.isClickable = true

        val date: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: android.widget.DatePicker?,
                year: Int,
                month: Int,
                day: Int
            ) {
                myCalendarFromDate[Calendar.YEAR] = year
                myCalendarFromDate[Calendar.MONTH] = month
                myCalendarFromDate[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }
        }

        filterFromDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendarFromDate[Calendar.YEAR],
                myCalendarFromDate[Calendar.MONTH],
                myCalendarFromDate[Calendar.DAY_OF_MONTH]
            ).show()
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
        filterToDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                toDate,
                myCalendarToDate[Calendar.YEAR],
                myCalendarToDate[Calendar.MONTH],
                myCalendarToDate[Calendar.DAY_OF_MONTH]
            ).show()
        }
        filterSearchCategory.setOnClickListener {
            FilterCategory()
        }

        filterSearch.setOnClickListener {

            if (filterFromDate.text.toString().isNullOrEmpty()
                &&filterToDate.text.toString().isNullOrEmpty()
                &&filterKeyWord.text.toString().isNullOrEmpty()
                &&filterSearchCategory.text.toString().isNullOrEmpty()){
                findNavController().navigate(FilingFilterFragmentDirections.actionFilingFilterFragmentToHomeScreenFragment(
                    "","","","","","","","","NO"
                ))
            }else{
                var listType=""
                if (HomeScreenFragment.requestType.equals("active")){
                      listType="archive"
                }else{
                    listType="active"
                }
                findNavController().navigate(FilingFilterFragmentDirections.actionFilingFilterFragmentToHomeScreenFragment(
                    filterSearchCategoryId,filterFromDateValue,"",filterKeyWord.text.toString(),
                    "10", listType,"0",filterToDateValue,"YES"
                ))

            }

/*
          val fi =  FilingFilterRequest(filterSearchCategoryId,filterFromDateValue,"",filterKeyWord.text.toString(),"10","active","0",filterToDateValue)
            FilingFilterRequest("","","","","","","","")
           // homeViewModel.filterHomeScreenFiling(fi)*/
        }

    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        filterFromDate?.setText(dateFormat.format(myCalendarFromDate.time))
        val myFormatS = "yyyy/MM/dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        filterFromDateValue=dateFormatS.format(myCalendarFromDate.time)
    }
    private fun updateLabelToDate() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        filterToDate?.setText(dateFormat.format(myCalendarToDate.time))
        val myFormatS = "yyyy/MM/dd"
        val dateFormatS = SimpleDateFormat(myFormatS, Locale.US)
        filterToDateValue=dateFormatS.format(myCalendarFromDate.time)
    }

    private fun FilterCategory() {
        val dialogView = layoutInflater.inflate(R.layout.spinner_dialog_custom, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
        customDialog.setCancelable(false)
        val cancel = dialogView.findViewById<TextView>(R.id.cancel)
        val ok = dialogView.findViewById<TextView>(R.id.ok)
        val custom_rv = dialogView.findViewById<RecyclerView>(R.id.custom_rv)
        val title = dialogView.findViewById<TextView>(R.id.title)
        title.text="Select Category"

        mFilterCategoryAdapter= FilterCategoryAdapter(filterCategoryList){ category, id->
            filterSearchCategory.setText(category)
            filterSearchCategoryId=id
            customDialog.dismiss()
        }

        val mLayoutManager = LinearLayoutManager(requireContext())
        custom_rv?.layoutManager = mLayoutManager
        custom_rv?.adapter = mFilterCategoryAdapter

        cancel.setOnClickListener {
            filterSearchCategory.setText("")
            filterSearchCategoryId=""
            customDialog.dismiss()
        }
        ok.setOnClickListener {

            customDialog.dismiss()
        }
    }

}