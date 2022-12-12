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

class VinCorrectionTaxableVehicleAdapter(var datalist: List<GetVinCorrectionByFilingIdResponse>, var OnMenuClick: (String, String, Int) -> Unit):RecyclerView.Adapter<VinCorrectionTaxableVehicleAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val vinCorrectionPreviousVin=itemview.findViewById<View>(R.id.vinCorrectionPreviousVin) as TextView
        val vinCorrectionCorrectVin=itemview.findViewById<View>(R.id.vinCorrectionCorrectVin) as TextView
        val vinCorrectionweight=itemview.findViewById<View>(R.id.vinCorrectionweight) as TextView
        val vinCorrectiontype=itemview.findViewById<View>(R.id.vinCorrectiontype) as TextView
        val vinCorrectionLogging=itemview.findViewById<View>(R.id.vinCorrectionLogging) as SwitchCompat
        val vinCorrectionDelete=itemview.findViewById<View>(R.id.vinCorrectionDelete) as ImageView
        val vinCorrectionEdit=itemview.findViewById<View>(R.id.vinCorrectionEdit) as ImageView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vin_correction_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.vinCorrectionPreviousVin.text=data.previousVin
        holder.vinCorrectionCorrectVin.text=data.correctVin
        holder.vinCorrectionweight.text=data.weight
        holder.vinCorrectiontype.text=data.vinType
        holder.vinCorrectionLogging.isChecked = data.isLogging.equals("Y")


        holder.vinCorrectionDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,0)
        }
        holder.vinCorrectionEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.weight,1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}