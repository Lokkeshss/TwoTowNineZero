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
import com.example.twotwoninezero.service.GetTGWIncreaseByFilingIdResponse
import com.example.twotwoninezero.service.GetTaxableVehicleResponse

class TaxableGrossWeightAdapter(var datalist: List<GetTGWIncreaseByFilingIdResponse>, var OnMenuClick: (String, String, String, Int) -> Unit):RecyclerView.Adapter<TaxableGrossWeightAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val taxableGrossWeightVin=itemview.findViewById<View>(R.id.taxableGrossWeightVin) as TextView
        val taxableGrossWeightPreviousCategory=itemview.findViewById<View>(R.id.taxableGrossWeightPreviousCategory) as TextView
        val taxableGrossWeightChangedCategory=itemview.findViewById<View>(R.id.taxableGrossWeightChangedCategory) as TextView
        val taxableGrossWeightAmount=itemview.findViewById<View>(R.id.taxableGrossWeightAmount) as TextView
        val taxableGrossWeightLogging=itemview.findViewById<View>(R.id.taxableGrossWeightLogging) as SwitchCompat
        val taxableGrossWeightDelete=itemview.findViewById<View>(R.id.taxableGrossWeightDelete) as ImageView
        val taxableGrossWeightEdit=itemview.findViewById<View>(R.id.taxableGrossWeightEdit) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.taxable_gross_weight_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.taxableGrossWeightVin.text=data.vin
        holder.taxableGrossWeightPreviousCategory.text="$ "+data.previousWeight
        holder.taxableGrossWeightChangedCategory.text=data.changedWeight
        holder.taxableGrossWeightAmount.text=data.differenceTaxAmount
        holder.taxableGrossWeightLogging.isChecked = data.isLogging.equals("Y")


        holder.taxableGrossWeightDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.previousWeight,data.changedWeight,0)
        }
        holder.taxableGrossWeightEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),data.previousWeight,data.changedWeight,1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}