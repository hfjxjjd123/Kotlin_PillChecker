package com.example.pill_checker

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegActivity : AppCompatActivity() {
    var morningOn = false
    var lunchOn = false
    var dinnerOn = false
    var sleepOn = false

    private var isPanelShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        var isPanelShown = false

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        val registerButton = findViewById<Button>(R.id.register_button)
        cancelButton.setOnClickListener() {
            finish()
        }
        registerButton.setOnClickListener() {
            finish()
        }

        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)

        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)

        morningClock.setOnClickListener() {
            morningOn = !morningOn
            if (morningOn) {
                morningClock.setBackgroundColor(onColor)
            } else {
                morningClock.setBackgroundColor(offColor)
            }
        }
        lunchClock.setOnClickListener() {
            lunchOn = !lunchOn
            if (lunchOn) {
                lunchClock.setBackgroundColor(onColor)
            } else {
                lunchClock.setBackgroundColor(offColor)
            }
        }
        dinnerClock.setOnClickListener() {
            dinnerOn = !dinnerOn
            if (dinnerOn) {
                dinnerClock.setBackgroundColor(onColor)
            } else {
                dinnerClock.setBackgroundColor(offColor)
            }
        }
        sleepClock.setOnClickListener() {
            sleepOn = !sleepOn
            if (sleepOn) {
                sleepClock.setBackgroundColor(onColor)
            } else {
                sleepClock.setBackgroundColor(offColor)
            }
        }

        val detailTitle = findViewById<LinearLayout>(R.id.details_title)
        val detailsLayout = findViewById<LinearLayout>(R.id.details_layout)
        val toggleView = findViewById<ImageView>(R.id.toggle_button)
        detailTitle.setOnClickListener() {
            isPanelShown = !isPanelShown

            if(isPanelShown) {
                detailsLayout.visibility = View.VISIBLE
                toggleView.setImageResource(R.drawable.arrow_down_24)
            } else {
                detailsLayout.visibility = View.GONE
                toggleView.setImageResource(R.drawable.arrow_toggle_24)
            }
        }

        val pillNum: Button = findViewById<Button>(R.id.pill_num)

        pillNum.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_pill_num) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                pillNum.text = item.title
                true
            }
            popupMenu.show()
        }

    }
}