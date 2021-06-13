package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.sprint.SprintProvider
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.data.model.task.TaskProvider

class TaskViewModel:ViewModel() {

    val repo = TaskProvider()

    fun getTasks(idSprint: String): LiveData<MutableList<TaskModel>> {
        val mutableData = MutableLiveData<MutableList<TaskModel>>()
        repo.getTasksBySprint(idSprint).observeForever{ listTasks ->
            mutableData.value = listTasks
        }
        return mutableData
    }
}