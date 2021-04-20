package com.jacup101.homework4

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var title : EditText
    lateinit var description : EditText
    lateinit var interpretation : EditText
    lateinit var feel : Spinner
    lateinit var button: Button

    var feelString : String = "fear"

    private val dreamViewModel : DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        title = findViewById(R.id.editText_addTitle)
        description = findViewById(R.id.editText_addDescription)
        interpretation = findViewById(R.id.editText_addInterpretation)


        feel = findViewById(R.id.spinner_addSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.emotions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            feel.adapter = adapter
        }
        feel.onItemSelectedListener = this

        button = findViewById(R.id.button_addSave)
        button.setOnClickListener {
            if(checkNotEmpty()) {
                var dream : Dream = Dream(0,title.text.toString(),description.text.toString(),interpretation.text.toString(),feelString)
                dreamViewModel.insert(dream)
                finish()
            } else {
                makeToast("Missing Field!")
            }

        }


    }


    fun checkNotEmpty() : Boolean {
        return !(TextUtils.isEmpty(title.text) || TextUtils.isEmpty(description.text) || TextUtils.isEmpty(interpretation.text))


    }


    fun makeToast(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        feelString = parent.getItemAtPosition(pos) as String
        //Log.d("new item ", feelString)

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

}