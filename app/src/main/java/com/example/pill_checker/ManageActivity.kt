package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.repo.PillRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ManageActivity:AppCompatActivity() {
    var time: Int = 0b0000
    private lateinit var db: MainDatabase
    lateinit var pillRepo: PillRepo

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    var pid: Long? = null

    private lateinit var pillText: TextView
    private lateinit var pillImage: ImageView
    private lateinit var pillNum: Button
    private lateinit var morningClock: Button
    private lateinit var lunchClock: Button
    private lateinit var dinnerClock: Button
    private lateinit var sleepClock: Button
    private lateinit var deleteButton: ImageView

    val onColor = R.color.primary

    override fun onCreate(savedInstanceState: Bundle?) {
        pid = intent.getLongExtra("pid", -1)
        val toUpdate = Intent(this, UpdateActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        job = Job()
        coroutineContext = Dispatchers.Main+ job

        db = MainDatabase.getDatabase(applicationContext)
        pillRepo = PillRepo(db)

        //init Views
        pillText = findViewById<TextView>(R.id.reg_name)
        pillImage = findViewById<ImageView>(R.id.pill_image)
        morningClock = findViewById<Button>(R.id.morning_clock)
        lunchClock = findViewById<Button>(R.id.lunch_clock)
        dinnerClock = findViewById<Button>(R.id.dinner_clock)
        sleepClock = findViewById<Button>(R.id.sleep_clock)
        pillNum = findViewById<Button>(R.id.pill_num)
        deleteButton = findViewById<ImageView>(R.id.delete_button)


        //NAVIGATION
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }

        val editButton = findViewById<ImageView>(R.id.edit_button)
        editButton.setOnClickListener(){
            toUpdate.putExtra("pid", pid!!)
            startActivity(toUpdate)
        }
    }

    //Data Binding 과정
    override fun onResume() {
        super.onResume()

        CoroutineScope(coroutineContext).launch {
            val pill = withContext(Dispatchers.IO){
                pillRepo.getPillById(pid!!)
            }

            pillText.text = pill.name

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

            pillNum.text = when(pill.ea){
                null -> ""
                0 -> "0.5"
                1 -> "1.0"
                2 -> "1.5"
                3 -> "2.0"
                else -> "1.0"
            }
            pillImage.setImageBitmap(pill.image)

            deleteButton.setOnClickListener(){
                CoroutineScope(Dispatchers.IO).launch {
                    pillRepo.deletePill(pill)
                }
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}