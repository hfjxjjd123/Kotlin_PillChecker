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

class CheckRecyclerAdapter(private val items: MutableList<PillDone>) :
    RecyclerView.Adapter<CheckRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_check_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val indexManager: MutableList<Int> = (0..items.size-1).toMutableList()
        val item: PillDone = items[indexManager[position]]


        holder.pillName.text = item.name

        if (item.done == "O") {
            holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
            holder.pillName.paintFlags = holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else{
            holder.checkImage.setImageResource(R.drawable.checkbox_custom)
            holder.pillName.paintFlags =
                holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        //TODO 데이터베이스 변화를 먼저하기, 변화 후 위치 맨 끝으로 보내기
        holder.pillTab.setOnClickListener {
            if (item.done == "O") {
                item.done = "X"
                holder.checkImage.setImageResource(R.drawable.checkbox_custom)
                holder.pillName.paintFlags =
                    holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                val itemToMove = items.removeAt(indexManager.indexOf(position))
                items.add(0, itemToMove)
                notifyItemMoved(indexManager.indexOf(position), 0)

                val  indexToMove = indexManager.removeAt(position)
                indexManager.add(0, indexToMove)
                println(indexManager)


                println(items[0].name)
                println(items[1].name)
                println(items[2].name)
                println("///////////////")


            } else {
                println("NOT TO DONE")
                println(item.done)
                println(items[0].name)
                println(items[1].name)
                println(items[2].name)

                item.done = "O"
                holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
                holder.pillName.paintFlags = holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                println(items)
                val itemToMove = items.removeAt(indexManager.indexOf(position))

                items.add(itemToMove)
                println(indexManager)
                notifyItemMoved(indexManager[position], items.size - 1)

                val  indexToMove = indexManager.removeAt(indexManager.indexOf(position))
                indexManager.add(indexToMove)
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