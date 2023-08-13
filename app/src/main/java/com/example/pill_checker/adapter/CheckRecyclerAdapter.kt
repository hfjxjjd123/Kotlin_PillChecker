package com.example.pill_checker.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R
import com.example.pill_checker.data.PillDone

class CheckRecyclerAdapter(private val items: List<PillDone>) :
    RecyclerView.Adapter<CheckRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_check_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PillDone = items[position]

        holder.pillName.text = item.name

        if (item.done == "O") {
            holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
            holder.pillName.paintFlags = holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else{
            holder.checkImage.setImageResource(R.drawable.checkbox_custom)
            holder.pillName.paintFlags =
                holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.pillTab.setOnClickListener {
            if (item.done == "O") {
                item.done = "X"
                holder.checkImage.setImageResource(R.drawable.checkbox_custom)
                holder.pillName.paintFlags =
                    holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            } else {
                item.done = "O"
                holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
                holder.pillName.paintFlags = holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    // InnerViewHolder class definition
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pillTab: LinearLayout = itemView.findViewById<LinearLayout>(R.id.pill_done_layout)
        val checkImage: ImageView = itemView.findViewById<ImageView>(R.id.pill_done_calendar)
        val pillName: TextView = itemView.findViewById<TextView>(R.id.pill_name_calendar)
    }

}