package com.portfolio.myapp.data.model.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger

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
}