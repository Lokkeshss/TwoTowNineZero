package com.example.twotwoninezero.dashboard.bottomnavigation.fleet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse
import com.example.twotwoninezero.service.GetFleetListResponse

class FleetListAdapter(var datalist: List<GetFleetListResponse>, var OnMenuClick: (String,String,String, Int) -> Unit):RecyclerView.Adapter<FleetListAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val fleetBusinessName=itemview.findViewById<View>(R.id.fleetBusinessName) as TextView
        val fleetVin=itemview.findViewById<View>(R.id.fleetVin)as TextView
        val fleettaxableweight=itemview.findViewById<View>(R.id.fleettaxableweight)as TextView
        val fleetlogginvehicle=itemview.findViewById<View>(R.id.fleetlogginvehicle)as SwitchCompat
        val fleetagriculturalvehicle=itemview.findViewById<View>(R.id.fleetagriculturalvehicle)as SwitchCompat
        val fleetDelete=itemview.findViewById<View>(R.id.fleetDelete)as ImageView
        val fleetEdit=itemview.findViewById<View>(R.id.fleetEdit) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fleetmanagement_line_item,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.fleetBusinessName.text=data.businessName
        holder.fleetVin.text=data.vin
        holder.fleettaxableweight.text=data.weight

        holder.fleetlogginvehicle.isChecked = data.isLogging.lowercase().equals("yes")
        holder.fleetagriculturalvehicle.isChecked = data.isAgriculture.lowercase().equals("yes")

        holder.fleetDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.businessName,data.weight,0)
        }
        holder.fleetEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.businessName,data.weight,1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}