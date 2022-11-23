package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.*

class PriorYearSuspendedAdapter(var datalist: List<GetPriorSuspendedByFilingIdResponse>, var OnMenuClick: (String, Int) -> Unit):RecyclerView.Adapter<PriorYearSuspendedAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val priorYearSuspendedVin=itemview.findViewById<View>(R.id.priorYearSuspendedVin) as TextView
        val priorYearSuspendedtransferdate=itemview.findViewById<View>(R.id.priorYearSuspendedtransferdate) as TextView
        val priorYearSuspendedTransferTo=itemview.findViewById<View>(R.id.priorYearSuspendedTransferTo) as TextView
        val priorYearSuspendedSold=itemview.findViewById<View>(R.id.priorYearSuspendedSold) as SwitchCompat
        val priorYearSuspendedmileageuse=itemview.findViewById<View>(R.id.priorYearSuspendedmileageuse) as SwitchCompat
        val priorYearSuspendedDelete=itemview.findViewById<View>(R.id.priorYearSuspendedDelete) as ImageView
        val priorYearSuspendedEdit=itemview.findViewById<View>(R.id.priorYearSuspendedEdit) as ImageView
        val priorYearSuspendedTransferLL=itemview.findViewById<View>(R.id.priorYearSuspendedTransferLL) as LinearLayout
        val priorYearSuspendedtransferdateLL=itemview.findViewById<View>(R.id.priorYearSuspendedtransferdateLL) as LinearLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.get_prior_suspended_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
       // holder.taxableVehicleName.text=data.businessName
        holder.priorYearSuspendedVin.text=data.vin
        if (data.soldDate.isNullOrEmpty()){
            holder.priorYearSuspendedtransferdateLL.visibility=View.GONE
        }

        if (data.soldToWhom.isNullOrEmpty()){
            holder.priorYearSuspendedTransferLL.visibility=View.GONE
        }
        holder.priorYearSuspendedtransferdate.text=data.soldDate
        holder.priorYearSuspendedTransferTo.text=data.soldToWhom
        holder.priorYearSuspendedSold.isChecked=data.isVehicleSold.equals("Y")
        holder.priorYearSuspendedmileageuse.isChecked=data.isExceededMileage.equals("Y")


        holder.priorYearSuspendedDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),0)
        }
        holder.priorYearSuspendedEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}