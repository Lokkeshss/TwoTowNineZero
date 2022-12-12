package com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.HomeScreenListResponse

class HomeScreenAdapterActivate (var datalist: List<HomeScreenListResponse>, var OnMenuClick: (String,String, String, String, String,String,String,String, Int) -> Unit):
    RecyclerView.Adapter<HomeScreenAdapterActivate.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val homeScreenBusinessName=itemview.findViewById<View>(R.id.homeScreenBusinessName) as TextView
        val homeScreenFormType=itemview.findViewById<View>(R.id.homeScreenFormType) as TextView
        val homeScreenFormStatus=itemview.findViewById<View>(R.id.homeScreenFormStatus) as TextView
        val homeScreenEdit=itemview.findViewById<View>(R.id.homeScreenEdit) as ImageView
        val homeScreenDelete=itemview.findViewById<View>(R.id.homeScreenDelete) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homescreen_lineitem_active,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.homeScreenBusinessName.text=data.businessName
        holder.homeScreenFormType.text=data.formType
        holder.homeScreenFormStatus.text=data.filingStatus
        holder.homeScreenFormStatus.setTextColor(Color.parseColor("#000000"))
        if (data.filingStatus.equals("IRS_APPROVED")){
            holder.homeScreenFormStatus.setTextColor(Color.parseColor("#28A745"))
        }
        if (data.filingStatus.equals("INCOMPLETE")){
            holder.homeScreenFormStatus.setTextColor(Color.parseColor("#0095DA"))
        }
        if (data.filingStatus.equals("IRS Rejected")){
            holder.homeScreenFormStatus.setTextColor(Color.parseColor("#DC3545"))
        }

        holder.homeScreenDelete.setOnClickListener {
            if (!data.filingStatus.equals("IRS_APPROVED")){
                OnMenuClick.invoke(data.businessId.toString(),data.filingId.toString(),data.formType,data.createdDate,data.filingStatus,data.filingMonth,data.paymentStatus,data.filingStatus,0)
            }
        }
        holder.homeScreenEdit.setOnClickListener {
            if (!data.filingStatus.equals("IRS_APPROVED")){
                OnMenuClick.invoke(data.businessId.toString(),data.filingId.toString(),data.formType,data.createdDate,data.filingStatus,data.filingMonth,data.paymentStatus,data.filingStatus,1)
            }
        }

        holder.itemView.setOnClickListener {
            OnMenuClick.invoke(data.businessId.toString(),data.businessName,data.formType,data.createdDate,data.filingStatus,data.filingMonth,data.paymentStatus,data.filingStatus,2)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun setpageNation(it: List<HomeScreenListResponse>) {
        datalist=it

    }


}