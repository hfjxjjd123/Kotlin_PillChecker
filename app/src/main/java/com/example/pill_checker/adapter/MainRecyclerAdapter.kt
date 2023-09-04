package com.example.pill_checker.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.MyApplication
import com.example.pill_checker.R
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.data.PillLight
import com.example.pill_checker.repo.PillCheckRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainRecyclerAdapter(
    private val context: Context,
    private val coroutineContext: CoroutineContext,
    private val items: MutableList<PillLight>
) :
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    private val indexManager: MutableList<Int> = (items.indices).toMutableList()
    var checkedCounter: Int = items.count { it.checked }

    val db = MainDatabase.getDatabase(context.applicationContext as MyApplication)
    private val pillCheckRepo = PillCheckRepo(db)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_check_layout, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PillLight = items[indexManager[position]]
        var itemEaString = " "
        if(item.ea != null){
            if(item.ea!! % 2 == 0){
                itemEaString += (item.ea!!/2).toString()
            }else{
                val itemEa: Double = item.ea!! / 2.0
                itemEaString += itemEa.toString()
            }
            itemEaString += "정"
        }

        holder.pillName.text = item.name + itemEaString

        if (item.checked) {
            holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
            holder.pillName.paintFlags = holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.checkImage.setImageResource(R.drawable.checkbox_custom)
            holder.pillName.paintFlags =
                holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        CoroutineScope(coroutineContext).launch{

            holder.pillTab.setOnClickListener {
                    if (item.checked) {
                        CoroutineScope(Dispatchers.IO).launch {
                            async {
                                pillCheckRepo.updatePillLight(
                                    pid = item.pid,
                                    tid = item.tid,
                                    checked = false
                                )
                            }.await()
                        }
                            item.checked = false
                            holder.checkImage.setImageResource(R.drawable.checkbox_custom)
                            holder.pillName.paintFlags =
                                holder.pillName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                            if (items.size - checkedCounter < indexManager.indexOf(position)) {
                                val itemToMove = items.removeAt(indexManager.indexOf(position))
                                items.add(0, itemToMove)
                                notifyItemMoved(indexManager.indexOf(position), 0)

                                val indexToMove = indexManager.removeAt(indexManager.indexOf(position))
                                indexManager.add(0, indexToMove)
                            }

                            checkedCounter -= 1
                    }else {
                        CoroutineScope(Dispatchers.IO).launch {
                            async { pillCheckRepo.updatePillLight(
                                pid = item.pid,
                                tid = item.tid,
                                checked = true)
                            }.await()
                        }

                    item.checked = true
                    holder.checkImage.setImageResource(R.drawable.checkbox_custom_checked)
                    holder.pillName.paintFlags =
                        holder.pillName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                    if (items.size - checkedCounter - 1 > indexManager.indexOf(position)) {
                        val itemToMove = items.removeAt(indexManager.indexOf(position))
                        items.add(itemToMove)
                        notifyItemMoved(indexManager.indexOf(position), items.size - 1)

                        val indexToMove = indexManager.removeAt(indexManager.indexOf(position))
                        indexManager.add(indexToMove)
                    }


                    checkedCounter += 1
                }
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