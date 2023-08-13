package com.example.pill_checker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.data.PillItem

var isLogin = false

class MainActivity : AppCompatActivity() {
    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

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

        val pills = listOf<PillItem>(
            PillItem(1),
            PillItem(2),
            PillItem(3),
        )

        outerRecyclerView = findViewById<RecyclerView>(R.id.recycler_pill)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PillOuterRecyclerAdapter(pills)
        outerRecyclerView.adapter = adapter



        val calendarPanel: LinearLayout= findViewById(R.id.title_calender)
        calendarPanel.setOnClickListener(){
            startActivity(toCalendar)
        }

        val pillsPanel: LinearLayout= findViewById(R.id.title_pills)
        pillsPanel.setOnClickListener(){
            startActivity(toPills)
        }

    }


    private fun isLoggedIn(): Boolean {
        return isLogin
    }

}