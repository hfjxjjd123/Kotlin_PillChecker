package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ManageActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val pillName = intent.getStringExtra("pillName")
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
            toUpdate.putExtra("pillName", pillName)
            startActivity(toUpdate)
        }

        val pillText = findViewById<TextView>(R.id.reg_name)
        pillText.text = pillName


    }
}