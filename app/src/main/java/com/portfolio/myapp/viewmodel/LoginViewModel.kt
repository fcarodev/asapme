package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.user.UserProvider
import com.portfolio.myapp.data.model.user.UserModel

class LoginViewModel: ViewModel() {
    val repo = UserProvider()

    fun login(mail:String,pass:String):LiveData<UserModel>{
        val mutableData = MutableLiveData<UserModel>()
        repo.userLogin(mail,pass).observeForever{ user ->
            mutableData.value = user
        }
        return mutableData
    }

    fun registerUser(userModel: UserModel):LiveData<UserModel>{
        val mutableData = MutableLiveData<UserModel>()
       repo.registerUser(userModel).observeForever{ user ->
            mutableData.value = user
        }
        return mutableData
    }

}