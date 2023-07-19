package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep
import java.time.Duration

class LoginActivity2:AppCompatActivity() {

    override fun getIntent(): Intent {
        return Intent(this, LoginActivity3::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        var isMorningSet: Boolean = false
        var isLunchSet: Boolean = false
        var isDinnerSet: Boolean = false
        var isSleepSet: Boolean = false

        fun navigate(): Boolean{
            if(isMorningSet && isLunchSet && isDinnerSet && isSleepSet){
                startActivity(intent)
            }
            return true
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        //morning
        val morningClock: Button = findViewById(R.id.morning_clock)

        morningClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_morning_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                morningClock.text = item.title
                isMorningSet = true;
                navigate()
            }

            popupMenu.show()
        }

        //lunch
        val lunchClock: Button = findViewById(R.id.lunch_clock)

        lunchClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_lunch_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                lunchClock.text = item.title
                isLunchSet=true
                navigate()
            }

            popupMenu.show()
        }

        //dinner
        val dinnerClock: Button = findViewById(R.id.dinner_clock)

        dinnerClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_dinner_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                dinnerClock.text = item.title
                isDinnerSet=true
                navigate()
            }

            popupMenu.show()
        }

        //sleep
        val sleepClock: Button = findViewById(R.id.sleep_clock)

        sleepClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_sleep_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                sleepClock.text = item.title
                isSleepSet=true
                navigate()
            }

            popupMenu.show()
        }




    }


}