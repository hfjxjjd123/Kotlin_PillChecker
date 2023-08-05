package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity:AppCompatActivity() {
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
    }
}