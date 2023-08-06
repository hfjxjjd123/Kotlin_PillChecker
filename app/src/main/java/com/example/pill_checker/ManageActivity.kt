package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pill_checker.data.PillDetailItem

val getDBPills = listOf<PillDetailItem>(
    PillDetailItem(1, "마그네슘", R.drawable.background, listOf("아침", "점심", "저녁"), 1),
    PillDetailItem(2, "비타민C", R.drawable.pill_image, listOf("자기전"), 1),
    PillDetailItem(3, "프로틴", R.drawable.pill_image, listOf("저녁"), 3),
)

class ManageActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pid = intent.getIntExtra("pid", -1)
        val toUpdate = Intent(this, UpdateActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

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


    }
}