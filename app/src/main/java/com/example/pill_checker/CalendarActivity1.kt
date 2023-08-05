package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CalendarRecyclerAdapter
import com.example.pill_checker.adapter.CategoryRecyclerAdapter
import com.example.pill_checker.adapter.DoneRecyclerAdapter
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.data.DoneItem
import com.example.pill_checker.data.PillItem

class CalendarActivity1:AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var adapter: CalendarRecyclerAdapter
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryRecyclerAdapter
    val timeCategory: List<String> = listOf<String>("아침", "점심", "저녁", "자기전")

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
                DoneItem("O", 0, "아침"),
                DoneItem("O", 0, "점심"),
                DoneItem("X", 0, "저녁"),
                DoneItem("N", 0, "자기전"),
            ),
            listOf<DoneItem>(
                DoneItem("O", 1, "아침"),
                DoneItem("O", 1, "점심"),
                DoneItem("X", 1, "저녁"),
                DoneItem("O", 1, "자기전"),
            ),
            listOf<DoneItem>(
                DoneItem("O", 2, "아침"),
                DoneItem("N", 2, "점심"),
                DoneItem("X", 2, "저녁"),
                DoneItem("O", 2, "자기전"),
            ),
            listOf<DoneItem>(
                DoneItem("O", 3, "아침"),
                DoneItem("N", 3, "점심"),
                DoneItem("X", 3, "저녁"),
                DoneItem("X", 3, "자기전"),
            ),

            )
        val dataOfStat = 0b1111

        val filteredItems = mutableListOf<List<DoneItem>>()
            for (item in dataOfList){
                val filteredItem: List<DoneItem> = item.filterIndexed() { index, _ ->
                    val indexStats = _indexToStats(index)
                    (dataOfStat and indexStats) == indexStats
                }
                filteredItems.add(filteredItem)
            }
        val filteredCategory: List<String> = timeCategory.filterIndexed() { index, _ ->
            val indexStats = _indexToStats(index)
            (dataOfStat and indexStats) == indexStats
        }

        calendarRecyclerView = findViewById<RecyclerView>(R.id.check_recycler_view)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalendarRecyclerAdapter(filteredItems)
        calendarRecyclerView.adapter = adapter

        categoryRecyclerView = findViewById<RecyclerView>(R.id.category_recycler_view)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryRecyclerAdapter(filteredCategory)
        categoryRecyclerView.adapter = categoryAdapter

    }

    private fun _indexToStats(index: Int): Int{
        return (0b0001 shl (3-index))
    }
}