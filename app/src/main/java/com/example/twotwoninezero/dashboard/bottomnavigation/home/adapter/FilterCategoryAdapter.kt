package com.example.twotwoninezero.dashboard.bottomnavigation.home.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twotwoninezero.R
import com.example.twotwoninezero.dashboard.bottomnavigation.fleet.adapter.FleetListAdapter
import com.example.twotwoninezero.service.FilterCategory
import com.example.twotwoninezero.service.GetFleetListResponse
import com.example.twotwoninezero.service.HomeScreenListResponse

class FilterCategoryAdapter(var datalist: ArrayList<FilterCategory>,
    var OnMenuClick: (String, String) -> Unit
):
    RecyclerView.Adapter<FilterCategoryAdapter.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val searchtext=itemview.findViewById<View>(R.id.searchtext) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_text_view,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val data=datalist[position]
        holder.searchtext.text=data.name
        holder.searchtext.setOnClickListener {
                OnMenuClick.invoke(data.name,data.id)
        }

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}