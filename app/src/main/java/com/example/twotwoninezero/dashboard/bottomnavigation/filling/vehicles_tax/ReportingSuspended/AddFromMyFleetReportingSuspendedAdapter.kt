package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.ReportingSuspended

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse
import com.example.twotwoninezero.service.GetFleetListResponse
import com.example.twotwoninezero.service.GetTaxableVehicleResponse
import com.example.twotwoninezero.service.LoadFleetListResponse
import kotlin.reflect.jvm.internal.impl.util.Check

class AddFromMyFleetReportingSuspendedAdapter(var datalist: List<LoadFleetListResponse>, var OnMenuClick: (String, String) -> Unit):RecyclerView.Adapter<AddFromMyFleetReportingSuspendedAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val addFromFleetreportingVIN=itemview.findViewById<View>(R.id.addFromFleetreportingVIN) as TextView
        val addFromFleetreportingCheckMe=itemview.findViewById<View>(R.id.addFromFleetreportingCheckMe) as CheckBox
        val addFromFleetreportingAgri=itemview.findViewById<View>(R.id.addFromFleetreportingAgri) as SwitchCompat
        val addFromFleetTaxablereportingLogging=itemview.findViewById<View>(R.id.addFromFleetTaxablereportingLogging) as SwitchCompat

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_from_fleet_reportinsuspended,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data= datalist.get(position)

        holder.addFromFleetreportingVIN.text= data.vin
        holder.addFromFleetreportingCheckMe.isChecked=data.isCheckMe

        holder.addFromFleetreportingAgri.isChecked =
            !(data.isAgriculture.equals("NO")||data.isAgriculture.equals("N"))

        holder.addFromFleetTaxablereportingLogging.isChecked =
            !(data.isLogging.equals("NO")||data.isLogging.equals("N"))

        holder.addFromFleetreportingAgri.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                data.isAgriculture="Y"
            }else{
                data.isAgriculture="N"
            }
        }
        holder.addFromFleetTaxablereportingLogging.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                data.isLogging="Y"
            }else{
                data.isLogging="N"
            }
        }

        holder.addFromFleetreportingCheckMe.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                data.isCheckMe=true
                holder.addFromFleetreportingCheckMe.isChecked=data.isCheckMe
            }else{
                data.isCheckMe=false
                holder.addFromFleetreportingCheckMe.isChecked=data.isCheckMe
            }
        }

        holder.addFromFleetreportingCheckMe.isChecked=data.isCheckMe

        holder.addFromFleetreportingAgri.isChecked =
            !(data.isAgriculture.equals("NO")||data.isAgriculture.equals("N"))

        holder.addFromFleetTaxablereportingLogging.isChecked =
            !(data.isLogging.equals("NO")||data.isLogging.equals("N"))

    }

    override fun getItemCount(): Int {
       return datalist.size
    }

    fun newFilter(mLoadFleetList: List<LoadFleetListResponse>) {
        datalist=mLoadFleetList
        notifyDataSetChanged()
    }


}