package com.yunis.simpletodo

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.yunis.simpletodo.R
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var inputTask: TextView
    private lateinit var taskItems: MutableList<TaskList>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter

    companion object {
        const val ITEM_TEXT = "item_text"
        const val ITEM_POSITION = "item_position"
        const val ITEM_DESCRIPTION = "item_description"
        const val ITEM_DATE = "item_date"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        taskItems = mutableListOf()

        val addButton: Button = findViewById(R.id.add_button)
        val clearButton: Button = findViewById(R.id.clear_button)

        //Function where when the user long press on an item, it will be removed.
        val onLongClickListener: TaskAdapter.OnLongClickListener = object : TaskAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //Delete the item
                taskItems.removeAt(position)
                //Notify adapter
                adapter.notifyItemRemoved(position)
                //Display Item was removed
                Toast.makeText(applicationContext, "Item was removed", Toast.LENGTH_SHORT).show()
            }
        }

        val onClickListener: TaskAdapter.OnClickListener = object : TaskAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                Log.d("MainActivity", "Single Click at position $position")
                val i = Intent(this@MainActivity, SecondActivity::class.java)
                i.putExtra(ITEM_TEXT, taskItems[position].taskName.toString())
                i.putExtra(ITEM_DATE, taskItems[position].taskDate)
                i.putExtra(ITEM_DESCRIPTION, taskItems[position].taskDescription.toString())
                i.putExtra(ITEM_POSITION, position)
                startForResult.launch(i)
            }
        }
        adapter = TaskAdapter(taskItems, onLongClickListener, onClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        addButton.setOnClickListener {
            inputTask = findViewById(R.id.TaskInput)
            taskItems.add(TaskList(inputTask.text.toString(),"",""))


            adapter.notifyDataSetChanged()

        }


        clearButton.setOnClickListener {
            taskItems.clear()
            adapter.notifyDataSetChanged()

        }

        val itemDecoration: ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)


    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val itemText: String? = intent?.getStringExtra(ITEM_TEXT)
            val itemDate: String? = intent?.getStringExtra(ITEM_DATE)
            val itemDescription: String? = intent?.getStringExtra(ITEM_DESCRIPTION)
            val position: Int? = intent?.extras?.getInt(ITEM_POSITION)

            for (i in 0 until taskItems.size){
                if (itemText != null) {
                    taskItems[i].taskName = itemText.toString()
                    if (position != null) {
                        adapter.notifyItemChanged(position)
                    }

                }
                if (itemDate != null) {
                    taskItems[i].taskDate  = itemDate
                    if (position != null) {
                        adapter.notifyItemChanged(position)
                    }

                }
                if (itemDescription != null) {
                    taskItems[i].taskDescription  = itemDescription
                    if (position != null) {
                        adapter.notifyItemChanged(position)
                    }

                }
            }




            Toast.makeText(applicationContext, "Item updated successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult")
        }
    }
}




