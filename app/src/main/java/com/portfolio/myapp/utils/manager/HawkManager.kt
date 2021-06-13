package com.portfolio.myapp.utils.manager

import com.orhanobut.hawk.Hawk
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.utils.constant.USER
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.utils.constant.CURRENT_PROJECT
import com.portfolio.myapp.utils.constant.CURRENT_SPRINT

class HawkManager {

    fun removeAll(){
        Hawk.deleteAll()
    }

    fun getUserLoggedIn(): UserModel {
        if(Hawk.contains(USER)){
            return Hawk.get(USER)
        }else  {
            return UserModel("0","sample@sample.com","sample name","sample lastname")
        }
    }

    fun setUserLoggedIn(userModel: UserModel){
        Hawk.put(USER,userModel)
    }

    fun setCurrentProject(project:ProjectModel){
        Hawk.put(CURRENT_PROJECT,project)
    }
    fun getCurrentProject():ProjectModel{
        return if(Hawk.contains(CURRENT_PROJECT)){
            Hawk.get(CURRENT_PROJECT)
        }else  {
            ProjectModel()
        }
    }

    fun setActualSprint(sprint:SprintModel){
        Hawk.put(CURRENT_SPRINT,sprint)
    }
    fun getActualSprint():SprintModel{
        return if(Hawk.contains(CURRENT_SPRINT)){
            Hawk.get(CURRENT_SPRINT)
        }else  {
            SprintModel()
        }
    }
}