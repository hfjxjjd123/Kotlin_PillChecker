package com.example.pill_checker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.R
import com.example.pill_checker.data.DoneItem

//outer list: date
//inner list: done
class DoneRecyclerAdapter(private val itemsDone: List<DoneItem>): RecyclerView.Adapter<DoneRecyclerAdapter.InnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_done_layout, parent, false)
        return InnerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val indexDone: DoneItem = itemsDone[position]

        if (indexDone.done == "O"){
            holder.doneImage.setImageResource(R.drawable.done)
        }
        else if(indexDone.done == "X"){
            holder.doneImage.setImageResource(R.drawable.notdone)
        }else{
            holder.doneImage.setImageResource(R.drawable.background)
        }

    }

    override fun getItemCount(): Int {
        return itemsDone.size
    }

    // InnerViewHolder class definition
    class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doneImage: ImageView = itemView.findViewById<ImageView>(R.id.done_image)
    }

}