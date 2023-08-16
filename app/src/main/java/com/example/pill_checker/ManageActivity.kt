package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.repo.PillRepo

class ManageActivity:AppCompatActivity() {
    var morningOn = false
    var lunchOn = false
    var dinnerOn = false
    var sleepOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val pid = intent.getLongExtra("pid", -1)
        val toUpdate = Intent(this, UpdateActivity::class.java)

        val pillRepo = PillRepo(MainDatabase.MainDatabase.getDatabase(this))
        val pill = pillRepo.getPillById(pid)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)

        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)
        val pillNum: Button = findViewById<Button>(R.id.pill_num)
        val pillImage = findViewById<ImageView>(R.id.pill_image)

        //NAVIGATION
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }
        val deleteButton = findViewById<ImageView>(R.id.delete_button)
        deleteButton.setOnClickListener(){
            finish()
        }
        val editButton = findViewById<ImageView>(R.id.edit_button)
        editButton.setOnClickListener(){
            toUpdate.putExtra("pid", pill.pid)
            startActivity(toUpdate)
        }

        val pillText = findViewById<TextView>(R.id.reg_name)
        pillText.text = pill.name

        if (pill.times and 0b0001 == 0b0001) {
            morningOn = true
            morningClock.setBackgroundColor(onColor)
        }
        if (pill.times and 0b0010 == 0b0010) {
            lunchOn = true
            lunchClock.setBackgroundColor(onColor)
        }
        if (pill.times and 0b0100 == 0b0100) {
            dinnerOn = true
            dinnerClock.setBackgroundColor(onColor)
        }
        if (pill.times and 0b1000 == 0b1000) {
            sleepOn = true
            sleepClock.setBackgroundColor(onColor)
        }

        pillNum.text = when(pill.ea){
            null -> "1.0"
            0 -> "0.5"
            1 -> "1.0"
            2 -> "1.5"
            3 -> "2.0"
            else -> "0.0"
        }
        pillImage.setImageBitmap(pill.image)


    }
}