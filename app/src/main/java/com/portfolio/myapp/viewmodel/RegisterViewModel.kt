package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.data.model.user.UserProvider

class RegisterViewModel: ViewModel()  {
    val repo = UserProvider()


    fun registerUser(userModel: UserModel): LiveData<UserModel> {
        val mutableData = MutableLiveData<UserModel>()
        repo.registerUser(userModel).observeForever{ user ->
            mutableData.value = user
        }
        return mutableData
    }
}