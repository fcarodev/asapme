package com.portfolio.myapp.utils.manager

import com.orhanobut.hawk.Hawk
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.utils.constant.CURRENT_USER
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.utils.constant.CURRENT_PROJECT
import com.portfolio.myapp.utils.constant.CURRENT_SPRINT
import com.portfolio.myapp.utils.constant.CURRENT_TASK

class HawkManager {

    fun removeAll(){
        Hawk.deleteAll()
    }

    fun getUserLoggedIn(): UserModel {
        if(Hawk.contains(CURRENT_USER)){
            return Hawk.get(CURRENT_USER)
        }else  {
            return UserModel("0","sample@sample.com","sample name","sample lastname")
        }
    }

    fun setUserLoggedIn(userModel: UserModel){
        Hawk.put(CURRENT_USER,userModel)
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

    fun setCurrentSprint(sprint:SprintModel){
        Hawk.put(CURRENT_SPRINT,sprint)
    }
    fun getCurrentSprint():SprintModel{
        return if(Hawk.contains(CURRENT_SPRINT)){
            Hawk.get(CURRENT_SPRINT)
        }else  {
            SprintModel()
        }
    }

    fun getCurrentTask(): TaskModel{
        return if(Hawk.contains(CURRENT_TASK)){
            Hawk.get(CURRENT_TASK)
        }else  {
            TaskModel()
        }

    }
    fun setCurrentTask(task:TaskModel){
        Hawk.put(CURRENT_TASK,task)
    }
}