package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.project.ProjectProvider
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.data.model.user.UserProvider
import com.portfolio.myapp.utils.manager.HawkManager

class StyleProjectViewModel: ViewModel() {

    val repo = ProjectProvider()
    fun updateProject(projectModel: ProjectModel): LiveData<ProjectModel> {
        val mutableData = MutableLiveData<ProjectModel>()
        repo.updateProject(projectModel).observeForever{ project ->
            mutableData.value = project
        }
        return mutableData
    }




}