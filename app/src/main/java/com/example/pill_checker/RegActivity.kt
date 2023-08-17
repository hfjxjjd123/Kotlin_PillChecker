package com.example.pill_checker

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.repo.PillRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegActivity : AppCompatActivity() {
    var time: Int = 0b0000

    private var isPanelShown = false
    private lateinit var db: MainDatabase
    private lateinit var pillRepo: PillRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        db = MainDatabase.getDatabase(applicationContext)
        pillRepo = PillRepo(db)

        var isPanelShown = false
        val textField = findViewById<EditText>(R.id.editText)

        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)

        val detailTitle = findViewById<LinearLayout>(R.id.details_title)
        val detailsLayout = findViewById<LinearLayout>(R.id.details_layout)
        val toggleView = findViewById<ImageView>(R.id.toggle_button)
        val pillNum: Button = findViewById<Button>(R.id.pill_num)
        val pillImage: ImageView = findViewById<ImageView>(R.id.pill_image)


        val onColor = ContextCompat.getColor(this, R.color.primary)
        val offColor = ContextCompat.getColor(this, R.color.primary_light)

        morningClock.setOnClickListener() {
            time = time.xor(0b0001)
            if (time.and(0b0001) == 0b0001) {
                morningClock.setBackgroundColor(onColor)
            } else {
                morningClock.setBackgroundColor(offColor)
            }
        }
        lunchClock.setOnClickListener() {
            time = time.xor(0b0010)
            if (time.and(0b0010) == 0b0010) {
                lunchClock.setBackgroundColor(onColor)
            } else {
                lunchClock.setBackgroundColor(offColor)
            }
        }
        dinnerClock.setOnClickListener() {
            time = time.xor(0b0100)
            if (time.and(0b0100) == 0b0100) {
                dinnerClock.setBackgroundColor(onColor)
            } else {
                dinnerClock.setBackgroundColor(offColor)
            }
        }
        sleepClock.setOnClickListener() {
            time = time.xor(0b1000)
            if (time.and(0b1000) == 0b1000) {
                sleepClock.setBackgroundColor(onColor)
            } else {
                sleepClock.setBackgroundColor(offColor)
            }
        }


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



        pillNum.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view) // Create a PopupMenu and pass the context and anchor view
            popupMenu.inflate(R.menu.menu_pill_num) // Inflate the menu resource

            popupMenu.setOnMenuItemClickListener { item ->
                pillNum.text = item.title
                true
            }
            popupMenu.show()
        }

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        val registerButton = findViewById<Button>(R.id.register_button)
        cancelButton.setOnClickListener() {
            finish()
        }
        registerButton.setOnClickListener() {
            // DB 등록 총체
            val name: String = textField.text.toString()
            val image: Bitmap? = pillImage.drawable.toBitmap()
            val ea: Int? = when(pillNum.text.toString()){
                "0.5" -> 1
                "1.0" -> 2
                "1.5" -> 3
                "2.0" -> 4
                else -> null
            }

            if (name == null || name.isEmpty()) {
                Toast.makeText(this, "약 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (time == 0b0000) {
                Toast.makeText(this, "시간대를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pill: Pill = Pill(1L, name, time, image, ea)
            CoroutineScope(Dispatchers.Default).launch {
                withContext(Dispatchers.IO) {
                    pillRepo.createPill(
                        pill
                    )
                }
            }

            finish()
        }



    }
}