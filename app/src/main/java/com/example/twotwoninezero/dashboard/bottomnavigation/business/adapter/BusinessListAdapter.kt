package com.example.twotwoninezero.dashboard.bottomnavigation.business.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse

class BusinessListAdapter(var datalist: List<AllAndSearchBusinessListResponse>, var OnMenuClick: (String,String, Int) -> Unit):RecyclerView.Adapter<BusinessListAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val businessName=itemview.findViewById<View>(R.id.businessName) as TextView
        val business_type=itemview.findViewById<View>(R.id.business_type)as TextView
        val business_ein=itemview.findViewById<View>(R.id.business_ein)as TextView
        val business_phone=itemview.findViewById<View>(R.id.business_phone)as TextView
        val business_addnewfilling=itemview.findViewById<View>(R.id.business_addnewfilling)as TextView
        val businessDelete=itemview.findViewById<View>(R.id.businessDelete)as ImageView
        val businessEdit=itemview.findViewById<View>(R.id.businessEdit) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.business_list_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.businessName.text=data.businessName
        holder.business_type.text=data.businessTypeName
        holder.business_ein.text=data.ein.replaceRange(2,2,"-")
        val first= data.phone.replaceRange(3,3,"-")
        val second= first.replaceRange(7,7,"-")
        holder.business_phone.text=second
        holder.businessDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.businessName,0)
        }
        holder.businessEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.businessName,1)
        }
        holder.business_addnewfilling.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.businessName,2)
        }
    }

    override fun getItemCount(): Int {
       return datalist.size
    }
}