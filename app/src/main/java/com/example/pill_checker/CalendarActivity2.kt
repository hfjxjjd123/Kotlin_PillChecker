package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity2:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

    }
}