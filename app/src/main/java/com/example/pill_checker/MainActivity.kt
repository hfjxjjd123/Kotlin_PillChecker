package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.repo.DateTimeRepo
import com.example.pill_checker.repo.PillCheckRepo
import com.example.pill_checker.repo.PillRepo
import com.example.pill_checker.repo.TimeRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

var isLogin = true

class MainActivity : AppCompatActivity() {
    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter
    private lateinit var checkRecyclerView: RecyclerView
    private lateinit var checkAdapter: CheckRecyclerAdapter

    private lateinit var db: MainDatabase
    private lateinit var pillCheckRepo: PillCheckRepo
    private lateinit var pillRepo: PillRepo
    private lateinit var timeRepo: TimeRepo
    private lateinit var dateTimeRepo: DateTimeRepo

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        db = MainDatabase.getDatabase(applicationContext)
        pillCheckRepo = PillCheckRepo(db)
        pillRepo = PillRepo(db)
        timeRepo = TimeRepo(db)
        dateTimeRepo = DateTimeRepo(db)

        job = Job()
        coroutineContext = Dispatchers.Main + job

        //TODO Login 로직 구현
        if (!isLoggedIn()) {
            val signInIntent = Intent(this, LoginActivity1::class.java)
            signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(signInIntent)
            finish()
        }

        val toCalendar = Intent(this, CalendarActivity1::class.java)
        val toPills = Intent(this, ReadActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val show_last: ImageView = findViewById(R.id.to_last_time)
        val show_next: ImageView = findViewById(R.id.to_next_time)
        show_last.setOnClickListener(){
            //Panel Data Fetching
        }
        show_next.setOnClickListener(){
            //Panel Data Fetching
        }

        checkRecyclerView = findViewById<RecyclerView>(R.id.calendar_done_list)
        checkRecyclerView.layoutManager = LinearLayoutManager(this)

        outerRecyclerView = findViewById<RecyclerView>(R.id.recycler_pill)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)

        val calendarPanel: LinearLayout= findViewById(R.id.title_calender)
        calendarPanel.setOnClickListener(){
            startActivity(toCalendar)
        }

        val pillsPanel: LinearLayout= findViewById(R.id.title_pills)
        pillsPanel.setOnClickListener(){
            startActivity(toPills)
        }

    }

    override fun onResume() {
        super.onResume()

        val dtidNow = DateTimeManager().getDateTimeValueNow()
        CoroutineScope(coroutineContext).launch {
            val ioScope = CoroutineScope(Dispatchers.IO).coroutineContext

            val consideredDtid: Long? =
                withContext(ioScope) {
                    timeRepo.veryNextDtid(dtidNow)
                }

            val checkedPill: List<PillCheck> = if (consideredDtid != null) {
                withContext(ioScope) {
                    pillCheckRepo.getPillChecksByDtid(consideredDtid)
                }
            } else {
                listOf<PillCheck>()
            }

            //TODO checkedPill이 Empty한 상황 핸들링하기

            val alignedItems: MutableList<PillCheck> = checkedPill.sortedBy { it.checked }.reversed().toMutableList()
            checkAdapter = CheckRecyclerAdapter(this@MainActivity, alignedItems)
            checkRecyclerView.adapter = checkAdapter

            val pills = withContext(ioScope) {
                pillRepo.getAllPills()

            }
            adapter = PillOuterRecyclerAdapter(pills)
            outerRecyclerView.adapter = adapter
        }
    }


    private fun isLoggedIn(): Boolean {
        return isLogin
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}