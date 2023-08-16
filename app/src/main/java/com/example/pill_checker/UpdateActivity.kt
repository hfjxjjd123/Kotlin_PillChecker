package com.example.pill_checker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.repo.PillRepo

//TODO Update DB 로직 필요

class UpdateActivity:AppCompatActivity() {
    var time: Int = 0b0000
    val pillRepo = PillRepo(MainDatabase.MainDatabase.getDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        val pid = intent.getLongExtra("pid", -1)
        val pill = pillRepo.getPillById(pid)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val pillText = findViewById<TextView>(R.id.reg_pill)
        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)
        val pillNum: Button = findViewById<Button>(R.id.pill_num)
        val pillImage = findViewById<ImageView>(R.id.pill_image)

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){

            finish()
        }



        pillText.text = pill.name

        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)
        time = pill.times
        if (time and 0b0001 == 0b0001) {
            morningClock.setBackgroundColor(onColor)
        }
        if (time and 0b0010 == 0b0010) {
            lunchClock.setBackgroundColor(onColor)
        }
        if (time and 0b0100 == 0b0100) {
            dinnerClock.setBackgroundColor(onColor)
        }
        if (time and 0b1000 == 0b1000) {
            sleepClock.setBackgroundColor(onColor)
        }

        pillNum.text = when(pill.ea){
            null -> ""
            0 -> "0.5"
            1 -> "1.0"
            2 -> "1.5"
            3 -> "2.0"
            else -> "1.0"
        }

        pillImage.setImageBitmap(pill.image)

        morningClock.setOnClickListener() {
            time = time.xor(0b0001)
            if (time and 0b0001 == 0b0001) {
                morningClock.setBackgroundColor(onColor)
            } else {
                morningClock.setBackgroundColor(offColor)
            }
        }
        lunchClock.setOnClickListener() {
            time = time.xor(0b0010)
            if (time and 0b0010 == 0b0010) {
                lunchClock.setBackgroundColor(onColor)
            } else {
                lunchClock.setBackgroundColor(offColor)
            }
        }
        dinnerClock.setOnClickListener() {
            time = time.xor(0b0100)
            if (time and 0b0100 == 0b0100) {
                dinnerClock.setBackgroundColor(onColor)
            } else {
                dinnerClock.setBackgroundColor(offColor)
            }
        }
        sleepClock.setOnClickListener() {
            time = time.xor(0b1000)
            if (time and 0b1000 == 0b1000) {
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