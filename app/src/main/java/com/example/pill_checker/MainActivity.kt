package com.example.pill_checker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val toCalendar = Intent(this, CalendarActivity1::class.java)
        val toPills = Intent(this, ReadActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarPanel: LinearLayout= findViewById(R.id.title_calender)
        calendarPanel.setOnClickListener(){
            startActivity(toCalendar)
        }

        val pillsPanel: LinearLayout= findViewById(R.id.title_pills)
        pillsPanel.setOnClickListener(){
            startActivity(toPills)
        }

    }


}