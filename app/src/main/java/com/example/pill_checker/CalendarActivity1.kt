package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity1:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar1)

        val backArrow = findViewById<ImageButton>(R.id.back_to_main)
        backArrow.setOnClickListener(){
            finish()
        }

    }
}