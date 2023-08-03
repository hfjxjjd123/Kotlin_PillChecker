package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CalendarRecyclerAdapter
import com.example.pill_checker.adapter.DoneRecyclerAdapter
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.data.DoneItem
import com.example.pill_checker.data.PillItem

class CalendarActivity1:AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var adapter: CalendarRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar1)

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        //TODO Data DB에서 얻어와야함
        val dataOfList = listOf<List<DoneItem>>(
            listOf<DoneItem>(
                DoneItem("O"),
                DoneItem("N"),
                DoneItem("X"),
                DoneItem("N"),
            ),
            listOf<DoneItem>(
                DoneItem("O"),
                DoneItem("N"),
                DoneItem("X"),
                DoneItem("O"),
            ),
            listOf<DoneItem>(
                DoneItem("O"),
                DoneItem("N"),
                DoneItem("X"),
                DoneItem("O"),
            ),
            listOf<DoneItem>(
                DoneItem("O"),
                DoneItem("N"),
                DoneItem("X"),
                DoneItem("X"),
            ),
            listOf<DoneItem>(
                DoneItem("N"),
                DoneItem("N"),
                DoneItem("X"),
                DoneItem("N"),
            ),
            listOf<DoneItem>(
                DoneItem("N"),
                DoneItem("N"),
                DoneItem("O"),
                DoneItem("N"),
            ),

            )
        val dataOfStat = 0b1011

        val filteredItems = mutableListOf<List<DoneItem>>()
            for (item in dataOfList){
                val filteredItem: List<DoneItem?> = item.filterIndexed() { index, _ ->
                    val indexStats = _indexToStats(index)
                    (dataOfStat and indexStats) == indexStats
                }
                filteredItems.add(filteredItem as List<DoneItem>)
            }

        println(filteredItems)
        calendarRecyclerView = findViewById<RecyclerView>(R.id.check_recycler_view)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalendarRecyclerAdapter(filteredItems)
        calendarRecyclerView.adapter = adapter

    }

    private fun _indexToStats(index: Int): Int{
        return (0b0001 shl (3-index))
    }
}