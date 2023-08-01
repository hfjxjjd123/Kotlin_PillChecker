package com.example.pill_checker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class LoginActivity3:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login3)

        val switch1 = findViewById<Switch>(R.id.switch1)
        val switch2 = findViewById<Switch>(R.id.switch2)
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switch1.text = getString(R.string.login3_while)
            } else{
                switch1.text = getString(R.string.login3_once)
            }
        }
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switch2.text = getString(R.string.login3_bell)
            } else{
                switch2.text = getString(R.string.login3_nobell)
            }
        }

        val next = findViewById<Button>(R.id.start_main)
        next.setOnClickListener {
            isLogin = true
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finishAffinity()
        }

    }



}