package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.sprint.SprintProvider
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.data.model.user.UserProvider

class SprintViewModel: ViewModel() {

    val repo = SprintProvider()
    fun getSprints(idProject: String): LiveData<MutableList<SprintModel>> {
        val mutableData = MutableLiveData<MutableList<SprintModel>>()
        repo.getSprintsByProject(idProject).observeForever{ listSprints ->
            mutableData.value = listSprints
        }
        return mutableData
    }

    fun addSprint(sprintModel: SprintModel): LiveData<SprintModel>{
        val mutableData = MutableLiveData<SprintModel>()
        repo.createSprint(sprintModel).observeForever{ sprintResult ->
            mutableData.value = sprintResult
        }
        return mutableData
    }

    fun updateSprint(sprintModel: SprintModel): LiveData<SprintModel>{
        val mutableData = MutableLiveData<SprintModel>()
        repo.updateSprint(sprintModel).observeForever{ sprintResult ->
            mutableData.value = sprintResult
        }
        return mutableData
    }
    fun updateSprintActive(sprintModel: SprintModel): LiveData<SprintModel>{
        val mutableData = MutableLiveData<SprintModel>()
        repo.updateSprintActive(sprintModel).observeForever{ sprintResult ->
            mutableData.value = sprintResult
        }
        return mutableData
    }
    fun deleteSprint(sprintModel: SprintModel): LiveData<SprintModel>{
        val mutableData = MutableLiveData<SprintModel>()
        repo.deleteSprint(sprintModel).observeForever{ sprintResult ->
            mutableData.value = sprintResult
        }
        return mutableData
    }
}