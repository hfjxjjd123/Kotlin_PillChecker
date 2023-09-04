package com.example.pill_checker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.MainRecyclerAdapter
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.dao.*
import com.example.pill_checker.repo.*
import com.example.pill_checker.data.PillLight
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var mainAdapter: MainRecyclerAdapter

    private lateinit var db: MainDatabase
    private lateinit var pillCheckRepo: PillCheckRepo
    private lateinit var pillRepo: PillRepo
    private lateinit var timeRepo: TimeRepo
    private lateinit var dateTimeRepo: DateTimeRepo

    lateinit var job: Job
    lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        db = MainDatabase.getDatabase(applicationContext)
        pillCheckRepo = PillCheckRepo(db)
        pillRepo = PillRepo(db)
        timeRepo = TimeRepo(db)
        dateTimeRepo = DateTimeRepo(db)

        job = Job()
        coroutineContext = Dispatchers.Main + job

        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            val signInIntent = Intent(this, LoginActivity1::class.java)
            signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(signInIntent)
            finish()
            //INILTIAL
//            CoroutineScope(Dispatchers.IO).launch {
//                var dtidNow = DateTimeManager.getDateTimeValueNow()
//
//                pillCheckRepo.createNextPillChecks(
//                    dtidNow.shr(4).shl(4).or(0b0100)
//                )
//                pillCheckRepo.createNextPillChecks(
//                    dtidNow.shr(4).shl(4).or(0b1000)
//                )
//            }
//
//
//                for (i in 0..4) {
//                    dtidNow -= 0b10000
//                    pillCheckRepo.createNextPillChecks(
//                        dtidNow.shr(4).shl(4).or(0b0001)
//                    )
//                    pillCheckRepo.createNextPillChecks(
//                        dtidNow.shr(4).shl(4).or(0b0010)
//                    )
//                    pillCheckRepo.createNextPillChecks(
//                        dtidNow.shr(4).shl(4).or(0b0100)
//                    )
//                    pillCheckRepo.createNextPillChecks(
//                        dtidNow.shr(4).shl(4).or(0b1000)
//                    )
//                }
//
//            }
        }

        setAlarm()

        val toCalendar = Intent(this, CalendarActivity1::class.java)
        val toPills = Intent(this, ReadActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecyclerView = findViewById<RecyclerView>(R.id.calendar_done_list)
        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        outerRecyclerView = findViewById<RecyclerView>(R.id.recycler_pill)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)

        val calendarPanel: LinearLayout = findViewById(R.id.title_calender)
        calendarPanel.setOnClickListener() {
            startActivity(toCalendar)
        }

        val pillsPanel: LinearLayout = findViewById(R.id.title_pills)
        pillsPanel.setOnClickListener() {
            startActivity(toPills)
        }

    }

    override fun onResume() {
        super.onResume()

        val dtidNow = DateTimeManager.getDateTimeValueNow()
        CoroutineScope(coroutineContext).launch {
            val ioScope = CoroutineScope(Dispatchers.IO).coroutineContext

            val consideredDtid: Long? =
                withContext(ioScope) {
                    timeRepo.lastDtid(dtidNow)
                }

            val lightPills: List<PillLight> = if (consideredDtid != null) {
                withContext(ioScope) {
                    pillCheckRepo.getPillLightsByTid(consideredDtid.and(0b1111).toInt())
                }
            } else {
                //TODO lightPills이 Empty한 상황 핸들링 구체화하기
                listOf<PillLight>(
                    PillLight(
                        pid = -1,
                        name = "새로운 약을 등록해주세요",
                        checked = false,
                        tid = 0
                    )
                )
            }

            val alignedItems: MutableList<PillLight> =
                lightPills.sortedBy { it.checked }.toMutableList()
            mainAdapter = MainRecyclerAdapter(this@MainActivity, coroutineContext, alignedItems)
            mainRecyclerView.adapter = mainAdapter

            val pills = withContext(ioScope) {
                pillRepo.getAllPills()
            }
            adapter = PillOuterRecyclerAdapter(pills)
            outerRecyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun setAlarm(){
        val morningIntent = Intent(this, MorningAlarmReceiver::class.java)
        morningIntent.action = "ALARM_MORNING"
        val lunchIntent = Intent(this, LunchAlarmReceiver::class.java)
        lunchIntent.action = "ALARM_LUNCH"
        val dinnerIntent = Intent(this, DinnerAlarmReceiver::class.java)
        dinnerIntent.action = "ALARM_DINNER"
        val sleepIntent = Intent(this, SleepAlarmReceiver::class.java)
        sleepIntent.action = "ALARM_SLEEP"

        val morningPendingIntent = PendingIntent.getBroadcast(this, 0, morningIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val lunchPendingIntent = PendingIntent.getBroadcast(this, 1, lunchIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val dinnerPendingIntent = PendingIntent.getBroadcast(this, 2, dinnerIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        val sleepPendingIntent = PendingIntent.getBroadcast(this, 3, sleepIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, CalendarManager.getMorningCalendar().timeInMillis, AlarmManager.INTERVAL_DAY, morningPendingIntent)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, CalendarManager.getLunchCalendar().timeInMillis, AlarmManager.INTERVAL_DAY, lunchPendingIntent)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, CalendarManager.getDinnerCalendar().timeInMillis, AlarmManager.INTERVAL_DAY, dinnerPendingIntent)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, CalendarManager.getSleepCalendar().timeInMillis, AlarmManager.INTERVAL_DAY, sleepPendingIntent)
    }


}