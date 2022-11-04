package com.example.twotwoninezero.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.GetCountryItem

class CountryListSpinnerAdapter (var datalist: List<GetCountryItem>, var OnMenuClick: (String, String) -> Unit):
    RecyclerView.Adapter<CountryListSpinnerAdapter.ViewHolder>() {

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var lineitem=itemView.findViewById<View>(R.id.lineitem) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nosearch_spinner_custom, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lineitem.setText(datalist[position].countryName)
        holder.lineitem.setOnClickListener {
            OnMenuClick.invoke(datalist[position].countryName,datalist[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}