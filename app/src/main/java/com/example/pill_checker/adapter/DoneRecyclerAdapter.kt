package com.example.pill_checker.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.CalendarActivity2
import com.example.pill_checker.R
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.data.DateTime

//outer list: date
//inner list: done
class DoneRecyclerAdapter(private val itemsDone: List<DateTime?>) :
    RecyclerView.Adapter<DoneRecyclerAdapter.InnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_done_layout, parent, false)

        return InnerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val item: DateTime? = itemsDone[position]

        if (item == null){
            holder.doneImage.setImageResource(R.drawable.empty_drawable)
        } else {
            if (item.checked) {
                holder.doneImage.setImageResource(R.drawable.done)
            } else{
                holder.doneImage.setImageResource(R.drawable.notdone)
            }

            val toCal2 = Intent(holder.itemView.context, CalendarActivity2::class.java)

            val datetime = DateTimeManager.separateDateTimeValue(item.dtid)
            toCal2.putExtra("date", datetime.first)
            toCal2.putExtra("time", datetime.second)

            // 해당 앱인지 알림
            holder.itemView.setOnClickListener() {
                holder.itemView.context.startActivity(toCal2)
            }
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