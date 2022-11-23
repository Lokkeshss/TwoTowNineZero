package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.*

class SoldDestroyedAdapter(var datalist: List<GetSoldDestroyedByFilingIdResponse>, var OnMenuClick: (String,String, Int) -> Unit):
    RecyclerView.Adapter<SoldDestroyedAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val soldDestroyedVin=itemview.findViewById<View>(R.id.soldDestroyedVin) as TextView
        val soldDestroyedGrossWeight=itemview.findViewById<View>(R.id.soldDestroyedGrossWeight) as TextView
        val soldDestroyedfirstusedMonth=itemview.findViewById<View>(R.id.soldDestroyedfirstusedMonth) as TextView
        val soldDestroyedsoldDestroyed=itemview.findViewById<View>(R.id.soldDestroyedsoldDestroyed) as TextView
        val soldDestroyedtype=itemview.findViewById<View>(R.id.soldDestroyedtype) as TextView
        val soldDestroyedLoggin=itemview.findViewById<View>(R.id.soldDestroyedLoggin) as SwitchCompat
        val soldDestroyedAmount=itemview.findViewById<View>(R.id.soldDestroyedAmount) as TextView
        val soldDestroyedDelete=itemview.findViewById<View>(R.id.soldDestroyedDelete) as ImageView
        val soldDestroyedEdit=itemview.findViewById<View>(R.id.soldDestroyedEdit) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sold_destroyed_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.soldDestroyedVin.text=data.vin
        holder.soldDestroyedGrossWeight.text=data.weight
        holder.soldDestroyedfirstusedMonth.text=data.firstUsedMonth
        holder.soldDestroyedsoldDestroyed.text=data.soldDestroyedDate
        holder.soldDestroyedtype.text=data.lossType
        holder.soldDestroyedAmount.text="$ "+data.creditAmount

        holder.soldDestroyedLoggin.isChecked = data.isLogging.equals("Y")

        holder.soldDestroyedDelete.setOnClickListener {
            OnMenuClick.invoke(data.weight,data.id.toString(),0)
        }
        holder.soldDestroyedEdit.setOnClickListener {
            OnMenuClick.invoke(data.weight,data.id.toString(),1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}