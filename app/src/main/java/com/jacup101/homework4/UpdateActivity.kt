package com.jacup101.homework4

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class UpdateActivity : AppCompatActivity(),  AdapterView.OnItemSelectedListener {

    lateinit var title : EditText
    lateinit var description : EditText
    lateinit var interpretation : EditText
    lateinit var feel : Spinner
    lateinit var button: Button

    var id : Long = -1
    private val dreamViewModel : DreamViewModel by viewModels {
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    private var feelString : String = "fear"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        title = findViewById(R.id.editText_updateTitle)
        description = findViewById(R.id.editText_updateDescription)
        interpretation = findViewById(R.id.editText_updateInterpretation)
        feel = findViewById(R.id.spinner_updateSpinner)
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

        id = intent.getLongExtra("id",0)
        var dreamLiveData = dreamViewModel.getDreamById(id)
        dreamLiveData.observe(this, Observer {
            dream -> dream?.let{
            title.setText(dream.title)
            description.setText(dream.content)
            interpretation.setText(dream.reflection)
            feel.setSelection(determinePos(dream.emotion))
            feelString = dream.emotion
        }
        })




        button = findViewById(R.id.button_updateSave)

        button.setOnClickListener {
            if(checkNotEmpty()) {
                dreamViewModel.updateDream(id,title.text.toString(),description.text.toString(),interpretation.text.toString(),feelString)
                finish()
            } else {
                makeToast("Missing Field!")
            }
        }
    }
    fun makeToast(text : String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
    private fun determinePos(type : String) : Int {
        if(type == "fear") return 0
        if(type == "panic") return 1
        if(type == "loss of self") return 2
        if(type == "grief") return 3
        if(type == "freedom") return 4
        if(type == "love") return 5
        if(type == "joy") return 6
        if(type == "vulnerability") return 7
        if(type == "confused") return 8
        if(type == "sad") return 9
        return -1
    }
    fun checkNotEmpty() : Boolean {
        return !(TextUtils.isEmpty(title.text) || TextUtils.isEmpty(description.text) || TextUtils.isEmpty(interpretation.text))


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