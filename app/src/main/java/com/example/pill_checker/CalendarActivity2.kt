package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.DateTime
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.repo.DateTimeRepo
import com.example.pill_checker.repo.PillCheckRepo

class CalendarActivity2:AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarRecyclerAdapter: CheckRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val date: Long = intent.getLongExtra("date", -1L)
        val time: Int = intent.getIntExtra("time", -1)
        //TODO Date Diff로 현재와 차이 알아내는 함수 필요
        val dateDiff = 10L

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        var text: String = ""
        val calendarText = findViewById<TextView>(R.id.calendar_text_time)
        when(dateDiff){
            //error handling 필요
            -1L -> text += "오류"
            0L -> text += "오늘"
            1L -> text += "어제"
            else -> text = text + date.toString() + "일전"
        }
        when(time){
            0b0001 -> text += " 아침"
            0b0010 -> text += " 점심"
            0b0100 -> text += " 저녁"
            0b1000 -> text += " 자기전"
            else -> text += " 오류"
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
        val pillCheckRepo = PillCheckRepo(MainDatabase.MainDatabase.getDatabase(this))
        val doneItems = pillCheckRepo.getPillChecksByDtid(date+time)

        //bool로 sorted 할 수 있나? ㅋㅋ
        val alignedItems: MutableList<PillCheck> = doneItems.sortedBy { it.checked }.reversed().toMutableList()

        //doneItems 정렬된 상태로 넘겨줌
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendar_panel)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)

        calendarRecyclerAdapter = CheckRecyclerAdapter(alignedItems)
        calendarRecyclerView.adapter = calendarRecyclerAdapter

    }
}