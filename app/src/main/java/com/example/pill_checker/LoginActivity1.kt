package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class LoginActivity1:AppCompatActivity() {

    override fun getIntent(): Intent {
        return Intent(this, LoginActivity2::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login1)
        val googleLogin: Button = findViewById(R.id.google_login)
        val appleLogin: Button = findViewById(R.id.apple_login)

        googleLogin.setOnClickListener{
            startActivity(intent)
        }
    }




}