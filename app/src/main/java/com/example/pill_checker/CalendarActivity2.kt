package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.repo.PillCheckRepo

class CalendarActivity2:AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarRecyclerAdapter: CheckRecyclerAdapter
    private val pillCheckRepo = PillCheckRepo(MainDatabase.MainDatabase.getDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        val date: Long = intent.getLongExtra("date", -1L)
        val time: Int = intent.getIntExtra("time", -1)

        val dateDiff = DateTimeManager().getDateDiff(date)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        var text: String = ""
        val calendarTimeText = findViewById<TextView>(R.id.calendar_text_time)

        text += DateTimeManager().getDateBeforeString(dateDiff)
        text += " "
        text += DateTimeManager().getTimeString(time)

        calendarTimeText.text = text

        println("DEBUGING________________$text/$date/$time//////////////////////")

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        ///
        val checkItems = pillCheckRepo.getPillChecksByDtid(date+time)
        val alignedItems: MutableList<PillCheck> = checkItems.sortedBy { it.checked }.reversed().toMutableList()

        //doneItems 정렬된 상태로 넘겨줌
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendar_panel)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)

        calendarRecyclerAdapter = CheckRecyclerAdapter(this, alignedItems)
        calendarRecyclerView.adapter = calendarRecyclerAdapter

    }
}