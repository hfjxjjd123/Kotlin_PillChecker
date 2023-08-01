package com.example.pill_checker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R

class PillInnerRecyclerAdapter(private val times: List<String>): RecyclerView.Adapter<PillInnerRecyclerAdapter.InnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pill_inner_layout, parent, false)
        return InnerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        println("/////////////////////////${times[position]}//////////////////////////////")
        val indexTime = times[position]

        // Set up the inner item layout here based on the chosen time
        holder.timeTextView.text = indexTime
    }

    override fun getItemCount(): Int {
        return times.size
    }

    // InnerViewHolder class definition
    class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.time_text)
    }

}