package com.example.twotwoninezero.dashboard.bottomnavigation.profile.adapter

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

class MyTransactionAdapter(var datalist: List<GetTransactionDetailsuserIDResponse>):RecyclerView.Adapter<MyTransactionAdapter.MyHolder>() {
    class MyHolder(itemview:View):RecyclerView.ViewHolder(itemview) {

        val myTransactionBusinessName=itemview.findViewById<View>(R.id.myTransactionBusinessName) as TextView
        val myTransactionDateofPayment=itemview.findViewById<View>(R.id.myTransactionDateofPayment) as TextView
        val myTransactionFormType=itemview.findViewById<View>(R.id.myTransactionFormType) as TextView
        val myTransactionTransactionID=itemview.findViewById<View>(R.id.myTransactionTransactionID) as TextView
        val myTransactionVoucherNo=itemview.findViewById<View>(R.id.myTransactionVoucherNo) as TextView
        val myTransactionPaymentStatus=itemview.findViewById<View>(R.id.myTransactionPaymentStatus) as TextView
        val myTransactionAmount=itemview.findViewById<View>(R.id.myTransactionAmount) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_transaction_lineitem,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.myTransactionBusinessName.text=data.bussinessName
        holder.myTransactionDateofPayment.text=data.paymentDate
        holder.myTransactionFormType.text=data.formType
        holder.myTransactionTransactionID.text=data.transactionId
        holder.myTransactionVoucherNo.text=data.voucherNo
        holder.myTransactionPaymentStatus.text=data.status
        holder.myTransactionAmount.text="$ "+data.amount
    }

    override fun getItemCount(): Int {
       return datalist.size
    }


}