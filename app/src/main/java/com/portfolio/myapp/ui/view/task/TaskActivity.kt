package com.portfolio.myapp.ui.view.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.TaskViewModel

class TaskActivity : AppCompatActivity() , TaskAdapter.TaskClickListener{

    private val viewModel by lazy { ViewModelProviders.of(this).get(TaskViewModel::class.java) }
    lateinit var recyclerViewTask: RecyclerView
    lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        adapter = TaskAdapter(this)
        recyclerViewTask = findViewById(R.id.recyclerTask)
        recyclerViewTask.layoutManager = LinearLayoutManager(this)
        recyclerViewTask.adapter = adapter

        getTasks()
    }

    private fun getTasks() {
        viewModel.getTasks(HawkManager().getActualSprint().innerId).observe(this, Observer { listSprints ->
            adapter.setListProject(listSprints)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onTaskClickListener() {
    }
}