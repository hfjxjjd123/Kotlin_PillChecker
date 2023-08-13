package com.example.pill_checker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.CalendarActivity2
import com.example.pill_checker.R
import com.example.pill_checker.data.DoneItem

class CheckRecyclerAdapter(private val items: List<DoneItem>) :
    RecyclerView.Adapter<CheckRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_check_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DoneItem = items[position]

        if (item.done == "O") {
            holder.checkBox.isChecked = true
        } else{
            holder.checkBox.isChecked = false
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // InnerViewHolder class definition
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.check_box_done)
    }

}