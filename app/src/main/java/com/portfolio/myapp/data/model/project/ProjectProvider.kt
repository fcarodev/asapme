package com.portfolio.myapp.data.model.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.user.UserModel


class ProjectProvider {
    val db = FirebaseFirestore.getInstance()

    fun setUserData(userModel: UserModel) {
        db.collection("user")
            .add(userModel).addOnSuccessListener {
                Logger.i("Success add user")
                val userData = UserModel()

            }
            .addOnFailureListener {
                Logger.i("Fail add user")
            }
    }

    fun getUserByMail(mail: String, pass: String): LiveData<UserModel> {
        val mutableData = MutableLiveData<UserModel>()
        FirebaseFirestore.getInstance().collection("user").whereEqualTo("email", mail)
            .whereEqualTo("password", pass).get()
            .addOnSuccessListener { resultDocument ->
                val userData = UserModel()
                for (project in resultDocument) {
                    Logger.i(project.getString("").toString())
                }
            }


        return mutableData


    }

    fun updateUserData(userModel: UserModel) {
        db.collection("user").document(userModel.innerId).update("innerId", userModel.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Success update")
                } else {
                    Logger.i("Fail update")
                }
            }

    }


    fun getProjectOfUser(idUser: String):LiveData<MutableList<ProjectModel>>  {

        val mutableData = MutableLiveData<MutableList<ProjectModel>>()
        FirebaseFirestore.getInstance()
            .collection("project")
            .whereEqualTo("userId",idUser)
            .get()
            .addOnSuccessListener { resultProject ->
                val listData = mutableListOf<ProjectModel>()
                for (project in resultProject) {
                    val colorBackground = project.getString("colorBackground").toString()
                    val colorText = project.getString("colorText").toString()
                    val currentSprint = project.getString("currentSprint").toString()
                    val description = project.getString("description").toString()
                    val imgUrl = project.getString("imgUrl").toString()
                    val name = project.getString("name").toString()
                    val progress = project.getString("progress").toString()
                    val innerId = project.id
                    val data = ProjectModel(
                        name,
                        description,
                        currentSprint,
                        imgUrl,
                        colorBackground,
                        colorText,
                        progress,
                        innerId,
                        idUser
                    )
                    Logger.i(Gson().toJson(data))
                    listData.add(data)

                }
                mutableData.value = listData
            }
        return mutableData
    }

    fun updateProject(projectModel: ProjectModel):LiveData<ProjectModel>{
        val updates = hashMapOf<String, Any>(
            "colorBackground" to projectModel.colorBackground,
            "colorText" to projectModel.colorText,
            "imgUrl" to projectModel.imgUrl
        )
        val mutableData = MutableLiveData<ProjectModel>()
        db.collection("project").document(projectModel.innerId!!).
        update(updates)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Success update")
                    mutableData.value = projectModel
                } else {
                    Logger.i("Fail update")
                }
            }
        return mutableData
    }

    fun createProject(projectModel: ProjectModel):LiveData<ProjectModel> {
        val mutableData = MutableLiveData<ProjectModel>()
        db.collection("project")
            .add(projectModel).addOnSuccessListener {
                Logger.i("Success add project")
                projectModel.innerId = it.id
                updateProject2(projectModel)
                createSprint(projectModel.innerId!!,projectModel.currentSprint)
                mutableData.value = projectModel
            }
            .addOnFailureListener {
                Logger.i("Fail add user")
            }
        return mutableData
    }

    private fun createSprint(projectId: String,sprintNAme:String) {
        val sprint = SprintModel()
        sprint.dateFinish = "10-10-10"
        sprint.dateInit = "10-10-10"
        sprint.description = "initial"
        sprint.idProject =projectId
        sprint.name = sprintNAme
        sprint.isActive = true
        db.collection("sprint")
            .add(sprint).addOnSuccessListener {
                Logger.i("Success add Sprint")
                sprint.innerId = it.id
                updateSprint2(sprint)
            }
            .addOnFailureListener {
                Logger.i("Fail add Sprint")
            }
    }
    private fun updateSprint2(sprint: SprintModel){
        db.collection("sprint")
            .document(sprint.innerId!!)
            .update("innerId", sprint.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Sprint Success update")
                } else {
                    Logger.i("Sprint Fail update")
                }
            }
    }
    private fun updateProject2(projectModel: ProjectModel){
        db.collection("project")
            .document(projectModel.innerId!!)
            .update("innerId", projectModel.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Project Success update")
                } else {
                    Logger.i("Project Fail update")
                }
            }
    }
}