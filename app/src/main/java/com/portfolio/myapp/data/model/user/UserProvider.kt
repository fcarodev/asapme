package com.portfolio.myapp.data.model.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.utils.manager.HawkManager


class UserProvider {
    val db = FirebaseFirestore.getInstance()

    fun userLogin(mail: String, pass: String):LiveData<UserModel> {
        val mutableData = MutableLiveData<UserModel>()
        mutableData.value = UserModel()
        db.collection("user")
            .whereEqualTo("email", mail)
            .whereEqualTo("password", pass).get()
            .addOnCompleteListener { task ->
                val userData = UserModel()
                if (task.isSuccessful) {
                    for (user in task.result!!) {
                        userData.innerId = user.id
                        userData.email = user.getString("email").toString()
                        userData.lastName = user.getString("lastName").toString()
                        userData.name = user.getString("name").toString()
                        userData.password = user.getString("password").toString()
                        mutableData.value = userData
                        HawkManager().setUserLoggedIn(userData)
                        Logger.i("user: ${Gson().toJson(userData)}")
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
                val userData = UserModel()
                if (task.isSuccessful) {
                    userModel.innerId =  task.result!!.id
                    mutableData.value = userModel
                    HawkManager().setUserLoggedIn(userData)
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

}