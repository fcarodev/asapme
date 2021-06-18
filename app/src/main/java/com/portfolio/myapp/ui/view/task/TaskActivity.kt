package com.portfolio.myapp.ui.view.task

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.ui.view.registerTask.RegisterTaskActivity
import com.portfolio.myapp.utils.Utils
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.SprintViewModel
import com.portfolio.myapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.alert_dialog_delete_task.view.*


class TaskActivity : AppCompatActivity(), TaskAdapter.TaskClickListener,
    BottomSheetTask.TaskClickListener {

    private val viewModelTask by lazy { ViewModelProviders.of(this).get(TaskViewModel::class.java) }
    private val viewModelSprint by lazy { ViewModelProviders.of(this).get(SprintViewModel::class.java) }
    var calcListTask = mutableListOf<TaskModel>()
    var bottomSheetDetailTask = BottomSheetTask(this)
    var actualSprint = SprintModel()
    lateinit var progressSprint :ProgressBar
    lateinit var recyclerViewTask: RecyclerView
    lateinit var adapter: TaskAdapter
    var taskListSort = mutableListOf<TaskModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        adapter = TaskAdapter(this)
        recyclerViewTask = findViewById(R.id.recyclerTask)
        recyclerViewTask.layoutManager = LinearLayoutManager(this)
        recyclerViewTask.adapter = adapter
        progressSprint = findViewById(R.id.progressSprintTask)
        actualSprint = HawkManager().getCurrentSprint()
        Logger.i("getCurrentSprint: " + Gson().toJson(actualSprint))

        txtSprintNameTask.text = actualSprint.name
        updateSprintHeader(actualSprint)
        constListTask.visibility = View.GONE
        showPlaceholderTask()

        btnBackDetailTask.setOnClickListener { goToSprints() }
        addTask.setOnClickListener {
            HawkManager().setCurrentTask(TaskModel())
            goToTaskRegister()
        }

        getTasks()
        addSortTask.setOnClickListener { sortTasks() }
    }
    private fun sortTasks(){
        if (addSortTask.tag == "desc") {
            addSortTask.tag = "asc"
            Logger.i("sortedBy ")
            taskListSort.sortByDescending { it.status }
        }else{
            addSortTask.tag = "desc"
            Logger.i("sortedByDescending ")
            taskListSort.sortBy { it.status}
        }
        adapter.setListTask(taskListSort)
        adapter.notifyDataSetChanged()
    }
    private fun goToSprints() {
        val intent =
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    private fun getTasks() {
        viewModelTask.getTasks(HawkManager().getCurrentSprint().innerId).observe(
            this,
            Observer { listTask ->
                calcListTask = listTask
                updateProgressSprint()
                if (listTask.isEmpty()) {
                    constListTask.visibility = View.GONE
                    val taskList = mutableListOf<TaskModel>()
                    taskList.add(TaskModel(ITEM_EMPTY_DATA))
                    adapter.setListTask(taskList)
                    adapter.notifyDataSetChanged()
                } else {
                    constListTask.visibility = View.VISIBLE
                    taskListSort = listTask
                    adapter.setListTask(listTask)
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
        viewModelTask.updateTask(taskModel).observe(this, Observer { resultTask ->
            Logger.i("success check edit")
            updateProgressSprint()
            getTasks()
        })
    }

    private fun updateProgressSprint() {
        actualSprint.actualProgress = calcSprintProgress()
        Logger.i("updateProgressSprint: " + Gson().toJson(actualSprint))
        viewModelSprint.updateSprint(actualSprint).observe(this, Observer { sprintResult->
            HawkManager().setCurrentSprint(sprintResult)
            updateSprintHeader(sprintResult)
        })
    }

    private fun calcSprintProgress(): String {
        val totalToDivide = calcListTask.size
        var totalChecked = 0
        if (totalToDivide == 0) return "0"

        for (item in calcListTask){
            if (item.status){
                totalChecked++
            }
        }
        return Utils().calcPercent(totalToDivide,totalChecked)
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
            deleteTask(HawkManager().getCurrentTask())
        }
    }

    private fun deleteTask(taskModel: TaskModel) {
        showPlaceholderTask()
        viewModelTask.deleteTask(taskModel).observe(this, Observer { resultTask->
            getTasks()
        })
    }

    private fun updateSprintHeader(sprintResult: SprintModel) {
        Logger.i("updateSprintHeader: " + Gson().toJson(sprintResult))
        txtSprintPercent.text = "${sprintResult.actualProgress}%"
        if(sprintResult.actualProgress == "0"){
            progressSprintTask.progress = 0
        }else{
            progressSprintTask.progress = sprintResult.actualProgress.toInt()
        }
    }

    fun showPlaceholderTask(){
        val taskList = mutableListOf<TaskModel>()
        taskList.add(TaskModel(ITEM_PLACEHOLDER))
        adapter.setListTask(taskList)
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        goToSprints()
    }
}