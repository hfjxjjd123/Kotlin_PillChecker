package com.example.pill_checker

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.repo.PillRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UpdateActivity : AppCompatActivity() {
    var time: Int = 0b0000
    private lateinit var db: MainDatabase
    lateinit var pillRepo: PillRepo

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val pid = intent.getLongExtra("pid", -1)

        db = MainDatabase.getDatabase(applicationContext)
        pillRepo = PillRepo(db)

        val pillText = findViewById<TextView>(R.id.reg_pill)
        val morningClock = findViewById<Button>(R.id.morning_clock)
        val lunchClock = findViewById<Button>(R.id.lunch_clock)
        val dinnerClock = findViewById<Button>(R.id.dinner_clock)
        val sleepClock = findViewById<Button>(R.id.sleep_clock)
        val pillNum: Button = findViewById<Button>(R.id.pill_num)
        val pillImage = findViewById<ImageView>(R.id.pill_image)
        val backArrow = findViewById<ImageButton>(R.id.back_arrow)

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        val registerButton = findViewById<Button>(R.id.register_button)

        job = Job()
        coroutineContext = Dispatchers.Main+ job

        CoroutineScope(coroutineContext).launch {
            val pill = withContext(Dispatchers.IO) {
                pillRepo.getPillById(pid)
            }

            backArrow.setOnClickListener() {
                finish()
            }
            cancelButton.setOnClickListener() {
                finish()
            }
            registerButton.setOnClickListener() {
                val name: String? = pillText.text.toString()
                val image: Bitmap? = pillImage.drawable.toBitmap()
                val ea: Int? = when (pillNum.text.toString()) {
                    "0.5" -> 1
                    "1.0" -> 2
                    "1.5" -> 3
                    "2.0" -> 4
                    else -> null
                }

                if (name == null || name.isEmpty()) {
                    Toast.makeText(this@UpdateActivity, "약 이름을 입력해주세요.", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (time == 0b0000) {
                    Toast.makeText(this@UpdateActivity, "시간대를 선택해주세요.", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val updatedPill: Pill = Pill(pill.pid, name, time, image, ea)
                CoroutineScope(Dispatchers.IO).launch {
                    async { pillRepo.updatePill(updatedPill, pill.times) }.await()
                }

                finish()
            }

            pillText.text = pill.name

            val onColor = ContextCompat.getColor(this@UpdateActivity, R.color.primary)
            val offColor = ContextCompat.getColor(this@UpdateActivity, R.color.primary_light)
            time = pill.times
            if (time and 0b0001 == 0b0001) {
                morningClock.setBackgroundColor(onColor)
            }
            if (time and 0b0010 == 0b0010) {
                lunchClock.setBackgroundColor(onColor)
            }
            if (time and 0b0100 == 0b0100) {
                dinnerClock.setBackgroundColor(onColor)
            }
            if (time and 0b1000 == 0b1000) {
                sleepClock.setBackgroundColor(onColor)
            }

            pillNum.text = when (pill.ea) {
                null -> ""
                0 -> "0.5"
                1 -> "1.0"
                2 -> "1.5"
                3 -> "2.0"
                else -> "1.0"
            }

            if(pill != null && pill.image != null){
                pillImage.setImageBitmap(pill.image)
            }

            morningClock.setOnClickListener() {
                time = time.xor(0b0001)
                if (time and 0b0001 == 0b0001) {
                    morningClock.setBackgroundColor(onColor)
                } else {
                    morningClock.setBackgroundColor(offColor)
                }
            }
            lunchClock.setOnClickListener() {
                time = time.xor(0b0010)
                if (time and 0b0010 == 0b0010) {
                    lunchClock.setBackgroundColor(onColor)
                } else {
                    lunchClock.setBackgroundColor(offColor)
                }
            }
            dinnerClock.setOnClickListener() {
                time = time.xor(0b0100)
                if (time and 0b0100 == 0b0100) {
                    dinnerClock.setBackgroundColor(onColor)
                } else {
                    dinnerClock.setBackgroundColor(offColor)
                }
            }
            sleepClock.setOnClickListener() {
                time = time.xor(0b1000)
                if (time and 0b1000 == 0b1000) {
                    sleepClock.setBackgroundColor(onColor)
                } else {
                    sleepClock.setBackgroundColor(offColor)
                }
            }
            pillNum.setOnClickListener { view ->
                val popupMenu = PopupMenu(
                    this@UpdateActivity,
                    view
                ) // Create a PopupMenu and pass the context and anchor view
                popupMenu.inflate(R.menu.menu_pill_num) // Inflate the menu resource

                popupMenu.setOnMenuItemClickListener { item ->
                    pillNum.text = item.title
                    true
                }
                popupMenu.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}