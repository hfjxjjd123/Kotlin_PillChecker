package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity2:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val date: Int = intent.getIntExtra("date", -1)
        val time: Int = intent.getIntExtra("time", -1)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        var text: String = ""
        val calendarText = findViewById<TextView>(R.id.calendar_text_time)
        when(date){
            //error handling 필요
            -1 -> text += "오류"
            0 -> text += "오늘"
            1 -> text += "어제"
            else -> text = text + date.toString() + "일전"
        }
        text += " "
        when(time){
            0 -> text += "아침"
            1 -> text += "점심"
            2 -> text += "저녁"
            3 -> text += "자기전"
            else -> text += "오류"
        }

        val textCalendarTime = findViewById<TextView>(R.id.calendar_text_time)
        textCalendarTime.text = text

        println("DEBUGING________________$text/$date/$time//////////////////////")

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

    }
}