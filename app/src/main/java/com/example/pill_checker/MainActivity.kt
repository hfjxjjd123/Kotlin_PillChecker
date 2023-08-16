package com.example.pill_checker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.CheckRecyclerAdapter
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.dao.DateTimeManager
import com.example.pill_checker.data.Pill
import com.example.pill_checker.data.PillCheck
import com.example.pill_checker.repo.DateTimeRepo
import com.example.pill_checker.repo.PillCheckRepo
import com.example.pill_checker.repo.PillRepo
import com.example.pill_checker.repo.TimeRepo

var isLogin = false

class MainActivity : AppCompatActivity() {
    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter
    private lateinit var checkRecyclerView: RecyclerView
    private lateinit var checkAdapter: CheckRecyclerAdapter

    val app = application as MyApplication
    private val db = app.database
    private val pillCheckRepo = PillCheckRepo(db)
    private val pillRepo = PillRepo(db)
    private val timeRepo = TimeRepo(db)
    private val dateTimeRepo = DateTimeRepo(db)


    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO Data Injection to Test
        timeRepo.initialTime()

        pillRepo.createPill(Pill(pid = 0, name = "마그네슘", times = 0b0111, ea = 2, image = null))
        pillRepo.createPill(Pill(pid = 1, name = "비타민C", times = 0b1000, ea = 2, image = R.drawable.pill_image.toDrawable().toBitmap()))
        pillRepo.createPill(Pill(pid = 2, name = "프로틴", times = 0b0100, ea = 2, image = R.drawable.push_notification.toDrawable().toBitmap()))

        val dtidNow = DateTimeManager().getDateTimeValueNow()
        val minusBit = 0b10000
        pillCheckRepo.createNextPillChecks(dtidNow)
        pillCheckRepo.createNextPillChecks(dtidNow - minusBit*1)
        pillCheckRepo.createNextPillChecks(dtidNow - minusBit*2)
        pillCheckRepo.createNextPillChecks(dtidNow - minusBit*3)
        pillCheckRepo.createNextPillChecks(dtidNow - minusBit*4)
        pillCheckRepo.createNextPillChecks(dtidNow - minusBit*5)

        //TODO TestCode end

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
        val consideredDtid: Long? = timeRepo.veryNextDtid(dtidNow)

        val checkedPill: List<PillCheck> = if(consideredDtid != null) {
            pillCheckRepo.getPillChecksByDtid(consideredDtid)
        }else{
            listOf<PillCheck>()
        }

        //TODO checkedPill이 Empty한 상황 핸들링하기

        val alignedItems: MutableList<PillCheck> = checkedPill.sortedBy { it.checked }.reversed().toMutableList()
        checkAdapter = CheckRecyclerAdapter(this, alignedItems)
        checkRecyclerView.adapter = checkAdapter

        val pills = pillRepo.getAllPills()
        adapter = PillOuterRecyclerAdapter(pills)
        outerRecyclerView.adapter = adapter

    }


    private fun isLoggedIn(): Boolean {
        return isLogin
    }


}