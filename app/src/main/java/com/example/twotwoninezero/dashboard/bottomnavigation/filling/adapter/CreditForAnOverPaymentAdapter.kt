package com.example.twotwoninezero.dashboard.bottomnavigation.filling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.service.*

class CreditForAnOverPaymentAdapter(var datalist: List<GetCreditOverPaymentByFilingIdResponse>, var OnMenuClick: (String, Int) -> Unit):
    RecyclerView.Adapter<CreditForAnOverPaymentAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val creditForPaymentVin=itemview.findViewById<View>(R.id.creditForPaymentVin) as TextView
        val creditForPaymentDateOfTax=itemview.findViewById<View>(R.id.creditForPaymentDateOfTax) as TextView
        val creditForPaymentAmount=itemview.findViewById<View>(R.id.creditForPaymentAmount) as TextView
        val creditForPaymentDelete=itemview.findViewById<View>(R.id.creditForPaymentDelete) as ImageView
        val creditForPaymentEdit=itemview.findViewById<View>(R.id.creditForPaymentEdit) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_for_an_over_payment_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.creditForPaymentVin.text=data.vin
        holder.creditForPaymentDateOfTax.text=data.paymentDate
        holder.creditForPaymentAmount.text="$ "+data.amountOfClaim

        holder.creditForPaymentDelete.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),0)
        }
        holder.creditForPaymentEdit.setOnClickListener {
            OnMenuClick.invoke(data.id.toString(),1)
        }

    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}