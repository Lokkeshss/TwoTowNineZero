package com.example.twotwoninezero.dashboard.bottomnavigation.business.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse

class DeletedBusinessListAdapter(var datalist: List<AllAndSearchBusinessListResponse>, var OnMenuClick: (String, Int) -> Unit):RecyclerView.Adapter<DeletedBusinessListAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val businessName=itemview.findViewById<View>(R.id.businessName) as TextView
        val business_type=itemview.findViewById<View>(R.id.business_type)as TextView
        val business_ein=itemview.findViewById<View>(R.id.business_ein)as TextView
        val business_phone=itemview.findViewById<View>(R.id.business_phone)as TextView
        val business_activate=itemview.findViewById<View>(R.id.business_activate)as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.deleted_business_list_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.businessName.text=data.businessName
        holder.business_type.text=data.businessTypeName
        holder.business_ein.text=data.ein
        holder.business_phone.text=data.phone

        holder.business_activate.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),1)
        }
    }

    override fun getItemCount(): Int {
       return datalist.size
    }
}