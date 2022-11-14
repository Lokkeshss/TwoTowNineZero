package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

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
import com.example.twotwoninezero.service.GetCurrentSuspendedByFilingIdResponse
import com.example.twotwoninezero.service.GetFleetListResponse
import com.example.twotwoninezero.service.GetTaxableVehicleResponse

class ReportingSuspendedExemptVehiclesAdapter(var datalist: List<GetCurrentSuspendedByFilingIdResponse>, var OnMenuClick: (String, Int) -> Unit):RecyclerView.Adapter<ReportingSuspendedExemptVehiclesAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val reportingSuspendedVin=itemview.findViewById<View>(R.id.reportingSuspendedVin) as TextView
        val reportingSuspendedAgricultureVehicle=itemview.findViewById<View>(R.id.reportingSuspendedAgricultureVehicle) as SwitchCompat
        val reportingSuspendedLogging=itemview.findViewById<View>(R.id.reportingSuspendedLogging) as SwitchCompat
        val reportingSuspendedDelete=itemview.findViewById<View>(R.id.reportingSuspendedDelete) as ImageView
        val reportingSuspendedEdit=itemview.findViewById<View>(R.id.reportingSuspendedEdit) as ImageView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reporting_suspended_adapter,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.reportingSuspendedVin.text=data.vin

        holder.reportingSuspendedAgricultureVehicle.isChecked=data.isAgriculture.equals("Y")
        holder.reportingSuspendedLogging.isChecked=data.isLogging.equals("Y")


        holder.reportingSuspendedDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),0)
        }
        holder.reportingSuspendedEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}