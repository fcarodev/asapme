package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.project.ProjectProvider

class ProjectViewModel :ViewModel(){
    val repo = ProjectProvider()
    fun createProject(projectModel: ProjectModel): LiveData<ProjectModel> {
        val mutableData = MutableLiveData<ProjectModel>()
        repo.createProject(projectModel).observeForever{ project ->
            mutableData.value = project
        }
        return mutableData
    }

    fun getAllProjectByUser(idUser:String):LiveData<MutableList<ProjectModel>>{
        val mutableData = MutableLiveData<MutableList<ProjectModel>>()
        repo.getProjectOfUser(idUser).observeForever{ projectList ->
            mutableData.value = projectList
        }
        return mutableData
    }
    fun updateProject(projectModel: ProjectModel): LiveData<ProjectModel> {
        val mutableData = MutableLiveData<ProjectModel>()
        repo.updateProject(projectModel).observeForever{ project ->
            mutableData.value = project
        }
        return mutableData
    }
}