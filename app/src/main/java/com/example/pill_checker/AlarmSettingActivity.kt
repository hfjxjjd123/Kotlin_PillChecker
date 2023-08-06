package com.example.pill_checker

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class AlarmSettingActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)

        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        val morningClock: Button = findViewById(R.id.morning_clock)

        morningClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_morning_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                morningClock.text = item.title
                true
            }

            popupMenu.show()
        }

        val lunchClock: Button = findViewById(R.id.lunch_clock)

        lunchClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_lunch_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                lunchClock.text = item.title
                true
            }

            popupMenu.show()
        }

        val dinnerClock: Button = findViewById(R.id.dinner_clock)

        dinnerClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_dinner_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                dinnerClock.text = item.title
                true
            }

            popupMenu.show()
        }

        val sleepClock: Button = findViewById(R.id.sleep_clock)

        sleepClock.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_sleep_clock) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                sleepClock.text = item.title
                true
            }

            popupMenu.show()
        }

        val switch1 = findViewById<Switch>(R.id.times_switch)
        val switch2 = findViewById<Switch>(R.id.bell_switch)
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

    }
}