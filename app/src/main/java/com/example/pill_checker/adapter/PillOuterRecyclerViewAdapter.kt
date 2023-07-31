package com.example.pill_checker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R
import com.example.pill_checker.data.PillOuterRecyclerItem

class PillOuterRecyclerViewAdapter(private val items: List<PillOuterRecyclerItem>) : RecyclerView.Adapter<PillOuterRecyclerViewAdapter.OuterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pill_layout, parent, false)
        return OuterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        val currentItem = items[position]

        // Set up the inner RecyclerView and its adapter
        val innerRecyclerView = holder.itemView.findViewById<RecyclerView>(R.id.innerRecyclerView)
        val innerAdapter = PillInnerRecyclerAdapter(currentItem.times)
        innerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = innerAdapter
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // OuterViewHolder class definition
    class OuterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}