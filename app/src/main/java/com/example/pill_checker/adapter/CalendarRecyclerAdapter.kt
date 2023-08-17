package com.example.pill_checker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R
import com.example.pill_checker.data.DateTime

class CalendarRecyclerAdapter(private val items: List<List<DateTime?>>) :
    RecyclerView.Adapter<CalendarRecyclerAdapter.OuterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OuterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_layout, parent, false)
        val parentHeight = parent.height
        val itemHeight = parentHeight / 6

        itemView.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            itemHeight
        )

        return OuterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        //position 0 = 옛날것.
        val currentItem:List<DateTime?> = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // OuterViewHolder class definition
    class OuterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.recycler_done)

        fun bind(data: List<DateTime?>) {
            val innerAdapter = DoneRecyclerAdapter(data)
            innerRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = innerAdapter
            }
        }

    }

}