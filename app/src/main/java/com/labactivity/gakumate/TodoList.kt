package com.labactivity.gakumate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.labactivity.gakumate.databinding.ActivityTodoListBinding

class TodoList : AppCompatActivity() {

    private lateinit var binding: ActivityTodoListBinding
    private val tasksList = ArrayList<Tasks>()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.layoutManager = layoutManager

        adapter = TaskAdapter(this, tasksList)
        binding.recyclerViewTasks.adapter = adapter

        val categoryName = intent.getStringExtra("categoryName")

        if (!categoryName.isNullOrBlank()) {
            binding.txtViewCategoryTitle.text = categoryName
        }

        binding.floatingAddRecBtn.setOnClickListener {
            val intentAddNotes = Intent(this, AddNotes::class.java)
            startActivityForResult(intentAddNotes, ADD_NOTES_REQUEST)
        }

        binding.imgBtnBack.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTES_REQUEST && resultCode == RESULT_OK) {
            val newTask = data?.getSerializableExtra("newTask") as? Tasks
            if (newTask != null) {
                tasksList.add(newTask)
                tasksList.sortBy { it.date }

                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val ADD_NOTES_REQUEST = 1
    }
}
