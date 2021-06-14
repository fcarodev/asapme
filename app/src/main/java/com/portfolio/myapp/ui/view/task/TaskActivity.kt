package com.portfolio.myapp.ui.view.task

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.ui.view.registerTask.RegisterTaskActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.alert_dialog_delete_task.*
import kotlinx.android.synthetic.main.alert_dialog_delete_task.view.*


class TaskActivity : AppCompatActivity(), TaskAdapter.TaskClickListener,
    BottomSheetTask.TaskClickListener {

    private val viewModel by lazy { ViewModelProviders.of(this).get(TaskViewModel::class.java) }
    var bottomSheetDetailTask = BottomSheetTask(this)

    lateinit var recyclerViewTask: RecyclerView
    lateinit var adapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        adapter = TaskAdapter(this)
        recyclerViewTask = findViewById(R.id.recyclerTask)
        recyclerViewTask.layoutManager = LinearLayoutManager(this)
        recyclerViewTask.adapter = adapter


        val taskList = mutableListOf<TaskModel>()
        taskList.add(TaskModel("PlaceholderDataTask"))
        adapter.setListProject(taskList)
        adapter.notifyDataSetChanged()
        btnBackDetailTask.setOnClickListener { goToSprints() }
        addTask.setOnClickListener {
            HawkManager().setCurrentTask(TaskModel())
            goToTaskRegister()
        }
        getTasks()
    }

    private fun goToSprints() {
        val intent =
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    private fun getTasks() {
        viewModel.getTasks(HawkManager().getCurrentSprint().innerId).observe(
            this,
            Observer { listSprints ->
                if (listSprints.isEmpty()) {
                    val taskList = mutableListOf<TaskModel>()
                    taskList.add(TaskModel("EmptyDataTask"))
                    adapter.setListProject(taskList)
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.setListProject(listSprints)
                    adapter.notifyDataSetChanged()
                }

            })
    }

    override fun onDeleteTaskClickListener(idTask: String) {
        bottomSheetDetailTask.dismiss()
        buildCustomDialog()
    }

    override fun onEditTaskClickListener(taskModel: TaskModel) {
        bottomSheetDetailTask.dismiss()
        HawkManager().setCurrentTask(taskModel)
        goToTaskRegister()
    }

    override fun onAddTaskClickListener() {
        HawkManager().setCurrentTask(TaskModel())
        goToTaskRegister()
    }

    override fun onEditStatusClickListener(taskModel: TaskModel) {
        HawkManager().setCurrentTask(taskModel)
        Logger.i("check edit")
        viewModel.updateTask(taskModel).observe(this, Observer { resultTask ->
            Logger.i("success check edit")
            getTasks()
        })
    }

    override fun onTaskClickListener(taskModel: TaskModel) {
        HawkManager().setCurrentTask(taskModel)
        Logger.i("show bottomsheet")
        bottomSheetDetailTask.show(this.supportFragmentManager, "bottomSheetDetailTask")
    }

    private fun goToTaskRegister() {
        val intent =
            Intent(this, RegisterTaskActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun buildCustomDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_delete_task, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.btnActionCustomDialog.setOnClickListener {
            mAlertDialog.dismiss()
            //Lo elimina

        }

    }

}