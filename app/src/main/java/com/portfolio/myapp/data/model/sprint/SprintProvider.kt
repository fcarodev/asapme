package com.portfolio.myapp.data.model.sprint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.task.TaskModel


class SprintProvider {
    val db = FirebaseFirestore.getInstance()

    fun getSprintsByProject(idProject: String): LiveData<MutableList<SprintModel>> {
        val mutableData = MutableLiveData<MutableList<SprintModel>>()
        db.collection("sprint")
            .whereEqualTo("idProject", idProject)
            .get()
            .addOnSuccessListener { resultSprints ->
                var sprintModel = mutableListOf<SprintModel>()
                sprintModel = resultSprints.toObjects(SprintModel::class.java)
                Logger.i("List sprint: ${Gson().toJson(sprintModel)}")
                mutableData.value = sprintModel
            }

        return mutableData
    }
     fun createSprint(sprintModel: SprintModel): LiveData<SprintModel> {
        val mutableData = MutableLiveData<SprintModel>()
        db.collection("sprint")
            .add(sprintModel).addOnSuccessListener {
                Logger.i("Success add Sprint")
                sprintModel.innerId = it.id
                updateSprint2(sprintModel)
                mutableData.value = sprintModel
            }
            .addOnFailureListener {
                Logger.i("Fail add Sprint")
            }
        return mutableData
    }

    fun updateSprintActive(sprint: SprintModel): LiveData<SprintModel> {
        val mutableData = MutableLiveData<SprintModel>()
        db.collection("sprint")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Logger.i(document.id + " => " + document.data)
                        document.reference.update("active", false)
                    }

                    db.collection("sprint")
                        .document(sprint.innerId)
                        .set(sprint)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                mutableData.value = sprint
                                Logger.i("Sprint document Success update")
                            } else {
                                Logger.i("Sprint document Fail update")
                            }
                        }

                } else {
                    Logger.i("Error getting documents: ", task.exception)
                }
            }
        return mutableData
    }
     fun updateSprint(sprint: SprintModel): LiveData<SprintModel> {
         val mutableData = MutableLiveData<SprintModel>()
         db.collection("sprint")
            .document(sprint.innerId)
            .set(sprint)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mutableData.value = sprint
                    Logger.i("Sprint document Success update")
                } else {
                    Logger.i("Sprint document Fail update")
                }
            }
         return mutableData
    }
    private fun updateSprint2(sprint: SprintModel){
        db.collection("sprint")
            .document(sprint.innerId)
            .update("innerId", sprint.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Sprint Success update")
                } else {
                    Logger.i("Sprint Fail update")
                }
            }
    }

    fun deleteSprint(idSprint:SprintModel): LiveData<SprintModel>{
            val mutableData = MutableLiveData<SprintModel>()
            db.collection("sprint").document(idSprint.innerId)
                .delete()
                .addOnSuccessListener {
                    Logger.i("Success delete sprint")
                    mutableData.value = idSprint
                }
                .addOnFailureListener { e -> Logger.i("Failure delete task ${e.message}") }
            return mutableData
    }
}
