package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pill_checker.data.PillDetailItem

val getDBPills = listOf<PillDetailItem>(
    PillDetailItem(1, "마그네슘", R.drawable.background, listOf("아침", "점심", "저녁"), 1),
    PillDetailItem(2, "비타민C", R.drawable.pill_image, listOf("자기전"), 1),
    PillDetailItem(3, "프로틴", R.drawable.pill_image, listOf("저녁"), 3),
)

class ManageActivity:AppCompatActivity() {
    var morningOn = false
    var lunchOn = false
    var dinnerOn = false
    var sleepOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val pid = intent.getIntExtra("pid", -1)
        val toUpdate = Intent(this, UpdateActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)

        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)
        val pillNum: Button = findViewById<Button>(R.id.pill_num)

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
            toUpdate.putExtra("pid", getDBPills[pid - 1].pid)
            startActivity(toUpdate)
        }

        val pillText = findViewById<TextView>(R.id.reg_name)
        pillText.text = getDBPills[pid - 1].name

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

    }
}