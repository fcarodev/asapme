package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.sprint.SprintProvider
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.data.model.task.TaskProvider

class TaskViewModel : ViewModel() {

    val repo = TaskProvider()

    fun getTasks(idSprint: String): LiveData<MutableList<TaskModel>> {
        val mutableData = MutableLiveData<MutableList<TaskModel>>()
        repo.getTasksBySprint(idSprint).observeForever { listTasks ->
            mutableData.value = listTasks
        }
        return mutableData
    }

    fun createTask(taskModel: TaskModel): LiveData<TaskModel> {
        val mutableData = MutableLiveData<TaskModel>()
        repo.addTask(taskModel).observeForever { taskResult ->
            mutableData.value = taskResult
        }
        return mutableData
    }
    fun updateTask(taskModel: TaskModel): LiveData<TaskModel>{
        val mutableData = MutableLiveData<TaskModel>()
        repo.updateTask(taskModel).observeForever { taskResult ->
            mutableData.value = taskResult
        }
        return mutableData
    }
    fun deleteTask(taskModel: TaskModel): LiveData<TaskModel>{
        val mutableData = MutableLiveData<TaskModel>()
        repo.deleteTask(taskModel).observeForever { taskResult ->
            mutableData.value = taskResult
        }
        return mutableData
    }
}