package com.example.pill_checker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManageActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pillName = intent.getStringExtra("pillName")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        val pillText = findViewById<TextView>(R.id.reg_name)
        pillText.text = pillName


    }
}