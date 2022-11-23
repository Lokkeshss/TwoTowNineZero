package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.AllAndSearchBusinessListResponse
import com.example.twotwoninezero.service.GetBusinessTypeRequestItem
import com.example.twotwoninezero.service.GetCountryItem
import com.example.twotwoninezero.service.GetLowMileageByFilingIdResponse

class LowMileageVehicleAdapter (var datalist: List<GetLowMileageByFilingIdResponse>, var OnMenuClick: (String,String, Int) -> Unit):
    RecyclerView.Adapter<LowMileageVehicleAdapter.ViewHolder>() {

    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var lowMileageVehicleVin=itemView.findViewById<View>(R.id.lowMileageVehicleVin) as TextView
        var lowMileageVehicleGrossWeight=itemView.findViewById<View>(R.id.lowMileageVehicleGrossWeight) as TextView
        var lowMileageVehicleFirstUsedMonth=itemView.findViewById<View>(R.id.lowMileageVehicleFirstUsedMonth) as TextView
        var lowMileageVehicleAmount=itemView.findViewById<View>(R.id.lowMileageVehicleAmount) as TextView
        var lowMileageVehicleLoggin=itemView.findViewById<View>(R.id.lowMileageVehicleLoggin) as SwitchCompat
        var lowMileageVehicleDelete=itemView.findViewById<View>(R.id.lowMileageVehicleDelete) as ImageView
        var lowMileageVehicleEdit=itemView.findViewById<View>(R.id.lowMileageVehicleEdit) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lowmileage_vehicle_lineitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=datalist[position]
        holder.lowMileageVehicleVin.setText(data.vin)
        holder.lowMileageVehicleGrossWeight.setText(data.weight)
        holder.lowMileageVehicleFirstUsedMonth.setText(data.firstUsedMonth)
        holder.lowMileageVehicleAmount.setText("$ "+data.creditAmount)
        holder.lowMileageVehicleLoggin.isChecked=data.isLogging.equals("Y")

        holder.lowMileageVehicleDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,0)
        }

        holder.lowMileageVehicleEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,1)
        }
     /*   holder.lineitem.setText(datalist[position].businessName)
        holder.lineitem.setOnClickListener {
            OnMenuClick.invoke(datalist[position].businessName,datalist[position].id)
        }*/
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}