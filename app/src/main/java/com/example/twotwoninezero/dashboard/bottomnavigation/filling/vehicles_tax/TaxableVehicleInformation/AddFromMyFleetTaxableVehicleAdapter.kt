package com.example.twotwoninezero.dashboard.bottomnavigation.filling.vehicles_tax.TaxableVehicleInformation

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

class AddFromMyFleetTaxableVehicleAdapter(var datalist: List<LoadFleetListResponse>, var OnMenuClick: (String, String) -> Unit):RecyclerView.Adapter<AddFromMyFleetTaxableVehicleAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val addFromFleetTaxableVehicleVIN=itemview.findViewById<View>(R.id.addFromFleetTaxableVehicleVIN) as TextView
        val addFromFleetTaxableVehicleWeight=itemview.findViewById<View>(R.id.addFromFleetTaxableVehicleWeight) as TextView
        val addFromFleetTaxableVehicleCheckMe=itemview.findViewById<View>(R.id.addFromFleetTaxableVehicleCheckMe) as CheckBox
        val addFromFleetTaxableVehicleLogging=itemview.findViewById<View>(R.id.addFromFleetTaxableVehicleLogging) as SwitchCompat

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_from_fleet_taxablevehicle,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data= datalist.get(position)

        holder.addFromFleetTaxableVehicleVIN.text= data.vin
        holder.addFromFleetTaxableVehicleWeight.text= data.weight
        holder.addFromFleetTaxableVehicleCheckMe.isChecked=data.isCheckMe

        if (data.isLogging.equals("NO")||data.isLogging.equals("N")){
            holder.addFromFleetTaxableVehicleLogging.isChecked=false
        }else{
            holder.addFromFleetTaxableVehicleLogging.isChecked=true
        }

        holder.addFromFleetTaxableVehicleLogging.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                data.isLogging="Y"
            }else{
                data.isLogging="N"
            }
        }

        holder.addFromFleetTaxableVehicleWeight.setOnClickListener {
            OnMenuClick.invoke(data.vin,"weight")

        }

        holder.addFromFleetTaxableVehicleCheckMe.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                data.isCheckMe=true
                holder.addFromFleetTaxableVehicleCheckMe.isChecked=data.isCheckMe
            }else{
                data.isCheckMe=false
                holder.addFromFleetTaxableVehicleCheckMe.isChecked=data.isCheckMe
            }
        }
        holder.addFromFleetTaxableVehicleCheckMe.isChecked=data.isCheckMe

        if (data.isLogging.equals("NO")||data.isLogging.equals("N")){
            holder.addFromFleetTaxableVehicleLogging.isChecked=false
        }else{
            holder.addFromFleetTaxableVehicleLogging.isChecked=true
        }

    }

    override fun getItemCount(): Int {
       return datalist.size ?: 0
    }

    fun newFilter(mLoadFleetList: List<LoadFleetListResponse>) {
        datalist=mLoadFleetList
        notifyDataSetChanged()
    }


}