package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse
import com.example.twotwoninezero.service.GetBusinessTypeRequestItem
import com.example.twotwoninezero.service.GetCountryItem

class BusinessNameListAdapter (var datalist: List<AllAndSearchBusinessListResponse>, var OnMenuClick: (String, Int) -> Unit):
    RecyclerView.Adapter<BusinessNameListAdapter.ViewHolder>() {

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var lineitem=itemView.findViewById<View>(R.id.lineitem) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nosearch_spinner_custom, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lineitem.setText(datalist[position].businessName)
        holder.lineitem.setOnClickListener {
            OnMenuClick.invoke(datalist[position].businessName,datalist[position].id)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}