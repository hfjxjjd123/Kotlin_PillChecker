package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter
import com.example.pill_checker.dao.MainDatabase
import com.example.pill_checker.data.Pill
import com.example.pill_checker.repo.PillRepo

class ReadActivity:AppCompatActivity() {

    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter
    private val pillRepo = PillRepo(MainDatabase.MainDatabase.getDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        val toReg = Intent(this, RegActivity::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        //navigation
        val backArrow = findViewById<ImageButton>(R.id.back_arrow)
        backArrow.setOnClickListener(){
            finish()
        }
        val registerPill = findViewById<ImageView>(R.id.reg_pill)
        registerPill.setOnClickListener(){
            startActivity(toReg)
        }

        outerRecyclerView = findViewById<RecyclerView>(R.id.recycler_pill)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onResume() {
        super.onResume()

        val pills: List<Pill> = pillRepo.getAllPills()
        adapter = PillOuterRecyclerAdapter(pills)
        outerRecyclerView.adapter = adapter
    }
}