package com.example.pill_checker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.repo.TimeRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivity3:AppCompatActivity() {

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {

        job = Job()
        coroutineContext = Dispatchers.Default + job

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
            if(GoogleSignIn.getLastSignedInAccount(this) == null){
                finishAffinity()
                startActivity(Intent(this, LoginActivity1::class.java))
                Toast.makeText(this, "로그인 아이디가 저장되지 않았습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Login1 화면으로 이동하기
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finishAffinity()
        }

    }



}