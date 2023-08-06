package com.example.pill_checker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class UpdateActivity:AppCompatActivity() {
    var morningOn = false
    var lunchOn = false
    var dinnerOn = false
    var sleepOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val pillName = intent.getStringExtra("pillName")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val pillText = findViewById<TextView>(R.id.reg_pill)
        pillText.text = pillName
        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)

        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)

        morningClock.setOnClickListener() {
            morningOn = !morningOn
            if (morningOn) {
                morningClock.setBackgroundColor(onColor)
            } else {
                morningClock.setBackgroundColor(offColor)
            }
        }
        lunchClock.setOnClickListener() {
            lunchOn = !lunchOn
            if (lunchOn) {
                lunchClock.setBackgroundColor(onColor)
            } else {
                lunchClock.setBackgroundColor(offColor)
            }
        }
        dinnerClock.setOnClickListener() {
            dinnerOn = !dinnerOn
            if (dinnerOn) {
                dinnerClock.setBackgroundColor(onColor)
            } else {
                dinnerClock.setBackgroundColor(offColor)
            }
        }
        sleepClock.setOnClickListener() {
            sleepOn = !sleepOn
            if (sleepOn) {
                sleepClock.setBackgroundColor(onColor)
            } else {
                sleepClock.setBackgroundColor(offColor)
            }
        }

        val pillNum: Button = findViewById<Button>(R.id.pill_num)

        pillNum.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_pill_num) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                pillNum.text = item.title
                true
            }
            popupMenu.show()
        }

    }
}