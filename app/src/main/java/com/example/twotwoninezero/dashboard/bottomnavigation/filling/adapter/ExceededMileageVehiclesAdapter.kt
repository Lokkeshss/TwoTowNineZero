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
import com.example.twotwoninezero.service.*

class ExceededMileageVehiclesAdapter(var datalist: List<GetExceededMileageByFilingIdResponse>, var OnMenuClick: (String, String, Int) -> Unit):RecyclerView.Adapter<ExceededMileageVehiclesAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val exceededMilageVin=itemview.findViewById<View>(R.id.exceededMilageVin) as TextView
        val exceededMilageAmount=itemview.findViewById<View>(R.id.exceededMilageAmount) as TextView
        val exceededMilageweight=itemview.findViewById<View>(R.id.exceededMilageweight) as TextView
        val exceededMilageLogging=itemview.findViewById<View>(R.id.exceededMilageLogging) as SwitchCompat
        val exceededMilageDelete=itemview.findViewById<View>(R.id.exceededMilageDelete) as ImageView
        val exceededMilageEdit=itemview.findViewById<View>(R.id.exceededMilageEdit) as ImageView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exceededmileage_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.exceededMilageVin.text=data.vin
        holder.exceededMilageAmount.text="$ "+data.taxAmount
        holder.exceededMilageweight.text=data.weight
        holder.exceededMilageLogging.isChecked = data.isLogging.equals("Y")


        holder.exceededMilageDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,0)
        }
        holder.exceededMilageEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}