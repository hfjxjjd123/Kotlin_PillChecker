package com.example.pill_checker.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R
import com.example.pill_checker.data.DoneItem

class CategoryRecyclerAdapter(private val items: List<String>) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: String = items[position]
        holder.categoryTextView.text = currentItem
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // OuterViewHolder class definition
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById<TextView>(R.id.category_text)
    }

    private fun calculateDesiredTextSize(viewWidth: Int): Float {
        // Calculate the desired text size based on the view width
        // You can adjust the formula here to achieve the desired result
        val scaleFactor = 0.5f // Adjust this value as needed
        return viewWidth * scaleFactor
    }

}