package com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.HomeScreenListResponse

class HomeScreenAdapterArchive (var datalist: List<HomeScreenListResponse>, var OnMenuClick: (String, String, String, String,String,String,String, Int) -> Unit):
    RecyclerView.Adapter<HomeScreenAdapterArchive.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val homeScreenBusinessName=itemview.findViewById<View>(R.id.homeScreenBusinessName) as TextView
        val homeScreenFormType=itemview.findViewById<View>(R.id.homeScreenFormType) as TextView
        val filing_activate=itemview.findViewById<View>(R.id.filing_activate) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homescreen_lineitem_delete,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.homeScreenBusinessName.text=data.businessName
        holder.homeScreenFormType.text=data.formType

        holder.itemView.setOnClickListener {
            OnMenuClick.invoke(data.businessName,data.formType,data.createdDate,data.filingStatus,data.filingMonth,data.paymentStatus,data.filingStatus,2)
        }

        holder.filing_activate.setOnClickListener {
            OnMenuClick.invoke(data.filingId.toString(),data.formType,data.createdDate,data.filingStatus,data.filingMonth,data.paymentStatus,data.filingStatus,1)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun setpageNation(it: List<HomeScreenListResponse>) {
        datalist=it
    }


}