package com.example.twotwoninezero.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R

class SearchAdapter(var datalist: List<String>, var OnMenuClick: (String) -> Unit):RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        var searchtext=itemView.findViewById<View>(R.id.searchtext) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_text_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchtext.setText(datalist[position])
        holder.searchtext.setOnClickListener {
            OnMenuClick.invoke(datalist[position])
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}