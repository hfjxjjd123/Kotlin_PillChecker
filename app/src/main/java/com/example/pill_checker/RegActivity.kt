package com.example.pill_checker

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        val registerButton = findViewById<Button>(R.id.register_button)
        cancelButton.setOnClickListener() {
            finish()
        }
        registerButton.setOnClickListener() {
            finish()
        }
    }
}