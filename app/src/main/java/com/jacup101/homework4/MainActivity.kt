package com.jacup101.homework4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var button : Button
    lateinit var recyclerView : RecyclerView

    private val dreamViewModel : DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        button = findViewById(R.id.button_addDream)
        button.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity,AddActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView_main)
        val adapter = DreamListAdapter(this)
        recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)


        dreamViewModel.allDreams.observe(this, Observer {
            dreams -> dreams?.let{
                adapter.submitList(it)
            }
        })



    }
}