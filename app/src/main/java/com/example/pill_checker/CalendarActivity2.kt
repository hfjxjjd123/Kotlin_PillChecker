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
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CalendarActivity2 : AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var calendarRecyclerAdapter: CheckRecyclerAdapter
    private lateinit var db: MainDatabase
    private lateinit var pillCheckRepo: PillCheckRepo

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        val date: Long = intent.getLongExtra("date", -1L)
        val time: Int = intent.getIntExtra("time", -1)

        job = Job()
        coroutineContext = Dispatchers.Main+ job

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendar_panel)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this@CalendarActivity2)

        db = MainDatabase.getDatabase(applicationContext)
        pillCheckRepo = PillCheckRepo(db)

        CoroutineScope(coroutineContext).launch {
            val ioScope = CoroutineScope(Dispatchers.IO).coroutineContext
            val dateDiff = DateTimeManager.getDateDiff(date)

            var text: String = ""
            val calendarTimeText = findViewById<TextView>(R.id.calendar_text_time)

            text += DateTimeManager.getDateBeforeString(dateDiff)
            text += " "
            text += DateTimeManager.getTimeString(time)

            calendarTimeText.text = text

            val backArrow = findViewById<ImageButton>(R.id.back_arrow)
            backArrow.setOnClickListener() {
                finish()
            }

            val checkItems = withContext(ioScope) {
                pillCheckRepo.getPillChecksByDtid(date.shl(4) + time)

            }
            val alignedItems: MutableList<PillCheck> =
                checkItems.sortedBy{ it.checked }.reversed().toMutableList()

            calendarRecyclerAdapter = CheckRecyclerAdapter(this@CalendarActivity2, coroutineContext, alignedItems)
            calendarRecyclerView.adapter = calendarRecyclerAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}