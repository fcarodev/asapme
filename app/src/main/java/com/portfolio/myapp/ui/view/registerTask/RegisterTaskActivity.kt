package com.portfolio.myapp.ui.view.registerTask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.ui.view.task.TaskActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_register_task.*

class RegisterTaskActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(TaskViewModel::class.java) }
    var taskModel =  TaskModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_task)
        taskModel = HawkManager().getCurrentTask()
        if(taskModel.innerId == "-1"){
            showRegisterView()
        }else{
            showUpdateView()
        }

        ViewAnimator
            .animate(btnBackAddTask)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleCreateTask)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleCreateTaskLabel)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameAddTask)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etDescAddTask)
            .translationX(2400f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .thenAnimate(btnRegisterTask)
            .alpha(0f, 1f)
            .duration(800)
            .start()

        btnBackAddTask.setOnClickListener {
            goToTaskList()
        }
        btnRegisterTask.setOnClickListener {
            if(taskModel.innerId == "-1"){
                registerTask()
            }else{
                updateTask()
            }

        }

    }

    private fun updateTask() {
        taskModel.name = nameTask.text.toString()
        taskModel.description = descTask.text.toString()
        viewModel.updateTask(taskModel).observe(this, Observer { taskResult->
            goToTaskList()
        })
    }



    private fun registerTask() {
        val taskModel = TaskModel()
        taskModel.description = descTask.text.toString()
        taskModel.idSprint = HawkManager().getCurrentSprint().innerId
        taskModel.name =  nameTask.text.toString()
        taskModel.status = false
        viewModel.createTask(taskModel).observe(this, Observer { taskResult->
            goToTaskList()
        })
    }

    private fun showUpdateView() {
        btnRegisterTask.text = "Actualizar tarea"
        txtTitleCreateTaskLabel.text = "Actualizar tarea"
        nameTask.setText(taskModel.name)
        descTask.setText(taskModel.description)
    }

    private fun showRegisterView() {
        btnRegisterTask.text = "Crear tarea"
        txtTitleCreateTaskLabel.text = "Nueva tarea"
    }
    private fun goToTaskList() {
        val intent =
            Intent(this, TaskActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
}