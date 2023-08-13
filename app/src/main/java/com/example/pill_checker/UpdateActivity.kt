package com.example.pill_checker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pill_checker.data.PillDetailItem

class UpdateActivity:AppCompatActivity() {
    var morningOn = false
    var lunchOn = false
    var dinnerOn = false
    var sleepOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val pid = intent.getIntExtra("pid", -1)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val pillText = findViewById<TextView>(R.id.reg_pill)
        pillText.text = getDBPills[pid - 1].name
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
        val pillNum: Button = findViewById<Button>(R.id.pill_num)
        val pillImage = findViewById<ImageView>(R.id.pill_image)

        //FIRST setting
        if (getDBPills[pid - 1].times.contains("아침")) {
            morningOn = true
            morningClock.setBackgroundColor(onColor)
        }
        if (getDBPills[pid - 1].times.contains("점심")) {
            lunchOn = true
            lunchClock.setBackgroundColor(onColor)
        }
        if (getDBPills[pid - 1].times.contains("저녁")) {
            dinnerOn = true
            dinnerClock.setBackgroundColor(onColor)
        }
        if (getDBPills[pid - 1].times.contains("자기전")) {
            sleepOn = true
            sleepClock.setBackgroundColor(onColor)
        }

        pillNum.text = when(getDBPills[pid-1].pillHalfNum){
            0 -> "0.5"
            1 -> "1.0"
            2 -> "1.5"
            3 -> "2.0"
            else -> "0.0"
        }

        pillImage.setImageResource(getDBPills[pid-1].imageId)

        ///////////////////////////

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