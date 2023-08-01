package com.example.pill_checker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.ManageActivity
import com.example.pill_checker.R
import com.example.pill_checker.data.PillItem

class PillOuterRecyclerAdapter(val items: List<PillItem>) : RecyclerView.Adapter<PillOuterRecyclerAdapter.OuterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pill_layout, parent, false)
        return OuterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val currentItem = items[position]

        // Set up the inner RecyclerView and its adapter
        val innerRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.recycler_time)
        holder.pillName.text = currentItem.name
        //TODO IMAGE ID에 맞게 매칭
        holder.pillImage.setImageResource(currentItem.imageId)

        val innerAdapter = PillInnerRecyclerAdapter(currentItem.times)
        innerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = innerAdapter
        }

        val toManage = Intent(holder.itemView.context, ManageActivity::class.java)
        // 해당 앱인지 알림
        toManage.putExtra("pillName", holder.itemView.findViewById<TextView>(R.id.pill_name).text)
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