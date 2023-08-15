package com.example.pill_checker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.ManageActivity
import com.example.pill_checker.R
import com.example.pill_checker.data.Pill


class PillOuterRecyclerAdapter(val items: List<Pill>) : RecyclerView.Adapter<PillOuterRecyclerAdapter.OuterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pill_layout, parent, false)
        return OuterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val pillDetailItem = items[position]


        // Set up the inner RecyclerView and its adapter
        val innerRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.recycler_time)
        holder.pillName.text = pillDetailItem.name
        //TODO IMAGE
        holder.pillImage.setImageBitmap(pillDetailItem.image)

        val timeList: MutableList<String> = mutableListOf()
        for (time in 0b0001..0b1000 step 0b0010){
            if(pillDetailItem.times and time == time) {
                val timeString = when(time){
                    0b0001 -> "아침"
                    0b0010 -> "점심"
                    0b0100 -> "저녁"
                    0b1000 -> "취침 전"
                    else -> "오류"
                }
                timeList.add(timeString)
            }
        }

        val innerAdapter = PillInnerRecyclerAdapter(timeList)
        innerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = innerAdapter
        }

        val toManage = Intent(holder.itemView.context, ManageActivity::class.java)
        // 해당 앱인지 알림
        toManage.putExtra("pid", pillDetailItem.pid)
        holder.itemView.setOnClickListener() {
            holder.itemView.context.startActivity(toManage)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // OuterViewHolder class definition
    class OuterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pillName: TextView = itemView.findViewById(R.id.pill_name)
        val pillImage: ImageView = itemView.findViewById(R.id.pill_image)
    }

}