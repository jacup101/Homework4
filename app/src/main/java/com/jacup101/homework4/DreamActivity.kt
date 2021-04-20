package com.jacup101.homework4

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe



class DreamActivity : AppCompatActivity(){


    private val dreamViewModel : DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    lateinit var title : TextView
    lateinit var content : TextView
    lateinit var interpretation: TextView
    lateinit var feel : TextView

    lateinit var update: Button
    lateinit var delete: Button

    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        title = findViewById(R.id.textView_dreamTitleContent)
        content = findViewById(R.id.textView_dreamDescriptionContent)
        interpretation = findViewById(R.id.textView_dreamInterpretationContent)
        feel = findViewById(R.id.textView_dreamFeelingContent)

        id = intent.getLongExtra("id",0)
        Log.d("dream_id", "" + id)
        var dreamLiveData = dreamViewModel.getDreamById(id)
        dreamLiveData.observe(this, Observer {
            dream -> dream?.let{
                title.text = dream.title
                content.text = dream.content
                interpretation.text = dream.reflection
                feel.text = dream.emotion
            }
        })

        update = findViewById(R.id.button_dreamUpdate)
        update.setOnClickListener {
            val intent = Intent(this@DreamActivity,UpdateActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        delete = findViewById(R.id.button_dreamDelete)
        delete.setOnClickListener {
            alertDelete()
        }




    }

    private fun alertDelete() {
        var stringArray = arrayOf("Yes", "No")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete?")
        builder.setPositiveButton("Yes") { dialog, which ->
            dreamViewModel.deleteDreamByID(id)
            finish()
        }

        builder.setNegativeButton("No") { dialog, which ->
            Log.d("alert","no")
        }
        builder.show()



    }
}