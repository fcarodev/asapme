package com.portfolio.myapp.data.model.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel

class TaskProvider {
    val db = FirebaseFirestore.getInstance()

    fun getTasksBySprint(idSprint:String): LiveData<MutableList<TaskModel>> {
        val mutableData = MutableLiveData<MutableList<TaskModel>>()
        db.collection("task")
            .whereEqualTo("idSprint",idSprint)
            .get()
            .addOnSuccessListener { resultSprints ->
                var taskModel = mutableListOf<TaskModel>()
                taskModel = resultSprints.toObjects(TaskModel::class.java)
                Logger.i("List sprint: ${Gson().toJson(taskModel)}")
                mutableData.value = taskModel
            }
        return mutableData
    }

    fun addTask(taskModel: TaskModel): LiveData<TaskModel> {
        val mutableData = MutableLiveData<TaskModel>()
        db.collection("task")
            .add(taskModel).addOnSuccessListener {
                Logger.i("Success add task")
                taskModel.innerId = it.id
                updateTask2(taskModel)
                mutableData.value = taskModel
            }
            .addOnFailureListener {
                Logger.i("Fail add task")
            }
        return mutableData
    }

    fun updateTask(taskModel: TaskModel):LiveData<TaskModel>{
        val mutableData = MutableLiveData<TaskModel>()
        db.collection("task")
            .document(taskModel.innerId!!)
            .set(taskModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mutableData.value = taskModel
                    Logger.i("Task document Success update")
                } else {
                    Logger.i("Task document Fail update")
                }
            }
        return mutableData
    }

    fun deleteTask(idTask:TaskModel): LiveData<TaskModel>{
        val mutableData = MutableLiveData<TaskModel>()
        db.collection("task").document(idTask.innerId)
            .delete()
            .addOnSuccessListener {
                Logger.i("Success delete task")
                mutableData.value = idTask
            }
            .addOnFailureListener { e -> Logger.i("Failure delete task ${e.message}") }
        return mutableData
    }

    private fun updateTask2(taskModel: TaskModel){
        db.collection("task")
            .document(taskModel.innerId)
            .update("innerId", taskModel.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Task Success update")
                } else {
                    Logger.i("Task Fail update")
                }
            }
    }
}