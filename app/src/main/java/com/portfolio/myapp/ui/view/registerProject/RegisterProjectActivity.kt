package com.portfolio.myapp.ui.view.registerProject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.projectdetail.ProjectDetailActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.CreateProjectViewModel
import com.portfolio.myapp.viewmodel.StyleProjectViewModel
import kotlinx.android.synthetic.main.activity_register_project.*

class RegisterProjectActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CreateProjectViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_project)
        btnBackAddProject.setOnClickListener { goToHome() }
        btnRegisterProject.setOnClickListener { createEmptyProject() }

    }

    private fun createEmptyProject() {
        val projectModel = ProjectModel()
        projectModel.name = nameProject.text.toString()
        projectModel.description = descProject.text.toString()
        projectModel.currentSprint = sprintProject.text.toString()
        projectModel.colorBackground = "#303F9F"
        projectModel.colorText = "#ffffff"
        projectModel.userId = HawkManager().getUserLoggedIn().innerId
        projectModel.imgUrl = "https://firebasestorage.googleapis.com/v0/b/myprojectapp-fa50e.appspot.com/o/group%20(1).svg?alt=media&token=a681a40c-8b1b-4c95-98f6-1a60fd40a1df"
        viewModel.createProject(projectModel).observe(this, Observer { project ->
            if (project != null) {
                Logger.i(Gson().toJson(project))
                HawkManager().setCurrentProject(project)
                goToDetailProject()
            } else {
                Logger.i("projecto nuloz")
            }

        })
    }
    fun goToDetailProject(){
        val intent =
            Intent(this, ProjectDetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    fun goToHome() {
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
}