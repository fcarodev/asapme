package com.portfolio.myapp.ui.view.registerProject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.activity_register_project.*
import kotlinx.android.synthetic.main.activity_register_project.textView6

class RegisterProjectActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_project)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnBackAddProject.setOnClickListener { goToHome() }
        btnRegisterProject.setOnClickListener { createEmptyProject() }

        ViewAnimator
            .animate(btnBackAddProject)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleCreateProject)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameAddProject)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etDescAddProject)
            .translationX(2400f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etSprintAddProject)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)

            .thenAnimate(btnRegisterProject)
            .alpha(0f, 1f)
            .duration(800)
            .start()

    }

    private fun createEmptyProject() {
        val projectModel = ProjectModel()
        projectModel.name = nameProject.text.toString()
        projectModel.description = descProject.text.toString()
        //projectModel.currentSprint = sprintProject.text.toString()
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
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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