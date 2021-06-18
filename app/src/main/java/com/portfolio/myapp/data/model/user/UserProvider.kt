package com.portfolio.myapp.data.model.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.utils.manager.HawkManager


class UserProvider {
    val db = FirebaseFirestore.getInstance()

    fun userLogin(mail: String, pass: String):LiveData<UserModel> {
        val mutableData = MutableLiveData<UserModel>()
        db.collection("user")
            .whereEqualTo("email", mail)
            .whereEqualTo("password", pass).get()
            .addOnCompleteListener { task ->
                val userData = UserModel()
                if (task.isSuccessful) {
                    for (user in task.result!!) {
                        userData.rut = user.getString("rut").toString()
                        userData.innerId = user.id
                        userData.email = user.getString("email").toString()
                        userData.lastName = user.getString("lastName").toString()
                        userData.name = user.getString("name").toString()
                        userData.password = user.getString("password").toString()
                        mutableData.value = userData
                        HawkManager().setUserLoggedIn(userData)
                        Logger.i("user: ${Gson().toJson(userData)}")
                    }
                    if (null == mutableData.value){
                        mutableData.value = UserModel()
                    }
                }else{
                    mutableData.value = UserModel()
                    Logger.i("Error getting user")
                }
            }
        return mutableData
    }
    fun registerUser(userModel: UserModel):LiveData<UserModel>{
        val mutableData = MutableLiveData<UserModel>()
        db.collection("user")
            .add(userModel).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userModel.innerId =  task.result!!.id
                    mutableData.value = userModel
                    HawkManager().setUserLoggedIn(userModel)
                    updateUser(userModel)
                } else {
                    mutableData.value = UserModel()
                    Logger.i("Error register user user")
                }

            }
        return mutableData
    }
    private fun updateUser(userModel: UserModel){
        db.collection("user")
            .document(userModel.innerId)
            .update("innerId", userModel.innerId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Success update")
                } else {
                    Logger.i("Fail update")
                }
            }
    }

    fun getUserByIdAndPass(userModel: UserModel):LiveData<UserModel>{
        val mutableData = MutableLiveData<UserModel>()
        db.collection("user")
            .whereEqualTo("innerId",userModel.innerId)
            .whereEqualTo("password",userModel.password)
            .get()
            .addOnSuccessListener { resultSprints ->
                var userModelInner = mutableListOf<UserModel>()
                userModelInner = resultSprints.toObjects(UserModel::class.java)
                if (userModelInner.isEmpty()){
                    mutableData.value = UserModel()
                }else {
                    mutableData.value = userModelInner[0]
                }
            }
        return mutableData
    }
    fun getUserByMailAndRut(userModel: UserModel):LiveData<UserModel>{
        val mutableData = MutableLiveData<UserModel>()
        db.collection("user")
            .whereEqualTo("email",userModel.email)
            .whereEqualTo("rut",userModel.rut)
            .get()
            .addOnSuccessListener { resultSprints ->
                var userModelInner = mutableListOf<UserModel>()
                userModelInner = resultSprints.toObjects(UserModel::class.java)
                if (userModelInner.isEmpty()){
                    mutableData.value = UserModel()
                }else {
                    mutableData.value = userModelInner[0]
                }
            }
        return mutableData
    }
     fun updateUserData(userModel: UserModel):LiveData<UserModel>{
         val mutableData = MutableLiveData<UserModel>()
        db.collection("user")
            .document(userModel.innerId)
            .set(userModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.i("Success update")
                    mutableData.value = userModel
                } else {
                    Logger.i("Fail update")
                    mutableData.value = UserModel()
                }
            }
         return mutableData
    }
}