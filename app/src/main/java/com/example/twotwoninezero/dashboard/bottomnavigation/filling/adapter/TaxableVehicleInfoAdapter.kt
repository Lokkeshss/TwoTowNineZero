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
import com.example.twotwoninezero.service.GetFleetListResponse
import com.example.twotwoninezero.service.GetTaxableVehicleResponse

class TaxableVehicleInfoAdapter(var datalist: List<GetTaxableVehicleResponse>, var OnMenuClick: (String,String,String,Int) -> Unit):RecyclerView.Adapter<TaxableVehicleInfoAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val taxableVehicleName=itemview.findViewById<View>(R.id.taxableVehicleName) as TextView
        val taxableVehicleVin=itemview.findViewById<View>(R.id.taxableVehicleVin) as TextView
        val taxableVehicletaxAmount=itemview.findViewById<View>(R.id.taxableVehicletaxAmount) as TextView
        val taxableVehicletaxableweight=itemview.findViewById<View>(R.id.taxableVehicletaxableweight) as TextView
        val taxableVehicleLogging=itemview.findViewById<View>(R.id.taxableVehicleLogging) as SwitchCompat
        val taxableVehicleDelete=itemview.findViewById<View>(R.id.taxableVehicleDelete) as ImageView
        val taxableVehicleEdit=itemview.findViewById<View>(R.id.taxableVehicleEdit) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.taxable_vehicle_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.taxableVehicleVin.text=data.vin
        holder.taxableVehicletaxAmount.text="$ "+data.taxAmount
        holder.taxableVehicletaxableweight.text=data.weight
        holder.taxableVehicleLogging.isChecked = data.isLogging.equals("Y")


        holder.taxableVehicleDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,data.weightCategory,0)
        }
        holder.taxableVehicleEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,data.weightCategory,1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}