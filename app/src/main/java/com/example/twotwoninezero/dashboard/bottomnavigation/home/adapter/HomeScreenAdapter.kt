package com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.adapter.FleetListAdapter
import com.example.twotwoninezero.service.GetFleetListResponse
import com.example.twotwoninezero.service.HomeScreenListResponse

class HomeScreenAdapter (var datalist: List<HomeScreenListResponse>, var OnMenuClick: (String,String,String,String,Int) -> Unit):
    RecyclerView.Adapter<HomeScreenAdapter.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val homeScreenBusinessName=itemview.findViewById<View>(R.id.homeScreenBusinessName) as TextView
        val homeScreenFormType=itemview.findViewById<View>(R.id.homeScreenFormType) as TextView
        val homeScreenEdit=itemview.findViewById<View>(R.id.homeScreenEdit) as ImageView
        val homeScreenDelete=itemview.findViewById<View>(R.id.homeScreenDelete) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homescreen_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.homeScreenBusinessName.text=data.businessName
        holder.homeScreenFormType.text=data.formType

        holder.homeScreenDelete.setOnClickListener {
                OnMenuClick.invoke(data.filingId.toString(),data.formType,data.createdDate,data.filingStatus,0)
        }
        holder.homeScreenEdit.setOnClickListener {
            OnMenuClick.invoke(data.filingId.toString(),data.formType,data.createdDate,data.filingStatus,1)
        }

        holder.itemView.setOnClickListener {
            OnMenuClick.invoke(data.businessName,data.formType,data.createdDate,data.filingStatus,2)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }


}