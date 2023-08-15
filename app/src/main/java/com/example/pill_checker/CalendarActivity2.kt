package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CheckRecyclerAdapter

class CalendarActivity2:AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarRecyclerAdapter: CheckRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val date: Int = intent.getIntExtra("date", -1)
        val time: String = intent.getStringExtra("time")?: "오류"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        var text: String = ""
        val calendarText = findViewById<TextView>(R.id.calendar_text_time)
        when(date){
            //error handling 필요
            -1 -> text += "오류"
            0 -> text += "오늘"
            1 -> text += "어제"
            else -> text = text + date.toString() + "일전"
        }
        text = text + " " + time

        val textCalendarTime = findViewById<TextView>(R.id.calendar_text_time)
        textCalendarTime.text = text

        println("DEBUGING________________$text/$date/$time//////////////////////")

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        ///
        val doneItems = listOf<PillDone>(
            PillDone(1, "마그네슘", 0, "아침", "O"),
            PillDone(2, "비타민", 0, "아침", "X"),
            PillDone(3, "프로틴", 0, "아침", "O"),
        )

        //bool로 sorted 할 수 있나? ㅋㅋ
        val alignedItems: MutableList<PillDone> = doneItems.sortedBy { it.done }.reversed().toMutableList()

        //doneItems 정렬된 상태로 넘겨줌
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendar_panel)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)

        calendarRecyclerAdapter = CheckRecyclerAdapter(alignedItems)
        calendarRecyclerView.adapter = calendarRecyclerAdapter

    }
}