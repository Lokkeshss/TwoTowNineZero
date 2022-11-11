package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.common.BusinessTypeListSpinnerAdapter
import com.example.twotwoninezero.service.GetBusinessTypeRequestItem
import com.example.twotwoninezero.service.GetFillingFormResponse
import com.example.twotwoninezero.service.GetFillingTaxYearResponse

class TaxYearAdapter (var datalist: List<GetFillingTaxYearResponse>, var OnMenuClick: (String, String,String) -> Unit):
    RecyclerView.Adapter<TaxYearAdapter.ViewHolder>() {

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var lineitem=itemView.findViewById<View>(R.id.lineitem) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nosearch_spinner_custom, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lineitem.setText(datalist[position].displayYear)
        holder.lineitem.setOnClickListener {
            OnMenuClick.invoke(datalist[position].displayYear,datalist[position].formType,datalist[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}