package com.yunis.simpletodo

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.*


class SecondActivity : AppCompatActivity() {

    private lateinit var textViewDueDate: TextView
    private lateinit var description: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        description = findViewById(R.id.editTaskDescription)

        textViewDueDate = findViewById(R.id.textViewDateDue)
        findViewById<EditText>(R.id.editTaskItem).setText(intent.getStringExtra(MainActivity.ITEM_TEXT))
        findViewById<Button>(R.id.save_button).setOnClickListener{
            val intent = Intent()
            intent.putExtra(
                MainActivity.ITEM_TEXT,
                findViewById<EditText>(R.id.editTaskItem).text.toString()
            )
            intent.putExtra(
                MainActivity.ITEM_DATE,
                findViewById<TextView>(R.id.textViewDateDue).text.toString()
            )

            intent.putExtra(
                MainActivity.ITEM_POSITION,
                getIntent().extras!!.getInt(MainActivity.ITEM_POSITION)
            )
            intent.putExtra(
                MainActivity.ITEM_DESCRIPTION,
                findViewById<EditText>(R.id.editTaskDescription).text.toString()
            )
            setResult(RESULT_OK, intent)
            finish()
        }

        val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val dueDate: String =  (1+month).toString() + "/" + dayOfMonth +"/"+ year
            textViewDueDate.text = dueDate
        }

        textViewDueDate.setOnClickListener{
            val calendar = Calendar.getInstance()

            DatePickerDialog(this@SecondActivity, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }





}