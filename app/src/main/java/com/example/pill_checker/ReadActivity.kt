package com.example.pill_checker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pill_checker.adapter.PillOuterRecyclerAdapter

class ReadActivity:AppCompatActivity() {

    private lateinit var outerRecyclerView: RecyclerView
    private lateinit var adapter: PillOuterRecyclerAdapter

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


        //RecyclerView 영역
        val pills = listOf<PillItem>(
            PillItem(1),
            PillItem(2),
            PillItem(3),
        )

        outerRecyclerView = findViewById<RecyclerView>(R.id.recycler_pill)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PillOuterRecyclerAdapter(pills)
        outerRecyclerView.adapter = adapter

    }
}