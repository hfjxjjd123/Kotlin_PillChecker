package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CalendarRecyclerAdapter
import com.example.pill_checker.adapter.CategoryRecyclerAdapter
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.DateTime
import com.example.pill_checker.repo.DateTimeRepo
import com.example.pill_checker.repo.PillCheckRepo
import kotlinx.coroutines.*
import java.time.LocalDateTime

class CalendarActivity1 : AppCompatActivity() {
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var adapter: CalendarRecyclerAdapter
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryRecyclerAdapter
    private val timeCategory: List<String> = listOf<String>("아침", "점심", "저녁", "자기전")
    private lateinit var db: MainDatabase
    private lateinit var dateTimeRepo: DateTimeRepo

    lateinit var job: Job
    private val coroutineContext = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar1)

        job = Job()

        db = MainDatabase.getDatabase(applicationContext)
        dateTimeRepo = DateTimeRepo(db)

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener() {
            finish()
        }
        val toAlarm = Intent(this, AlarmSettingActivity::class.java)
        val notificationButton = findViewById<ImageButton>(R.id.notification_button)
        notificationButton.setOnClickListener() {
            startActivity(toAlarm)
        }

        calendarRecyclerView = findViewById<RecyclerView>(R.id.check_recycler_view)
        calendarRecyclerView.layoutManager = LinearLayoutManager(this)

        categoryRecyclerView = findViewById<RecyclerView>(R.id.category_recycler_view)
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(coroutineContext).launch{
            val itemListByDate: Pair<List<List<DateTime?>>, List<Boolean>> = withContext(Dispatchers.IO) {
                dateTimeRepo.getAllDateTimes(DateTimeManager().getDateValue(LocalDateTime.now()))
            }

            val filteredCategory: List<String> = timeCategory.filterIndexed() { index, _ ->
                itemListByDate.second[index]
            }

            adapter = CalendarRecyclerAdapter(itemListByDate.first)
            calendarRecyclerView.adapter = adapter

            categoryAdapter = CategoryRecyclerAdapter(filteredCategory)
            categoryRecyclerView.adapter = categoryAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}