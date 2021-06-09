package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.project.ProjectProvider
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.user.UserModel

class HomeViewModel: ViewModel() {
    val repo = ProjectProvider()

    fun getAllProjectByUser(idUser:String):LiveData<MutableList<ProjectModel>>{
        val mutableData = MutableLiveData<MutableList<ProjectModel>>()
        repo.getProjectOfUser(idUser).observeForever{ projectList ->
            mutableData.value = projectList
        }
        return mutableData
    }

}