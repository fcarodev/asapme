package com.portfolio.myapp.ui.view.projectdetail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.updateProjectStyle.UpdateProjectStyleActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.item_row_home.view.*


class ProjectDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        val project = HawkManager().getCurrentProject()
        settingViewColor(project)
        settingViewData(project)
        btnBackDetailProject.setOnClickListener { goToHome() }
        imgEditProjectStyle.setOnClickListener {
            goToStyleProject()
        }
    }

    private fun settingViewData(project: ProjectModel) {
        txtNameProjectDetail.text = project.name
        txtDescProjectDetail.text = project.description
        txtSprintProjectDetail.text = project.currentSprint
        Glide.with(this).load(project.imgUrl).placeholder(getDrawable(R.drawable.ic_project_placeholder)).into(imgProjectDetail)
        txtPercentDetail.text = "${project.progress}%"
        progressBarProjectDetail.progress = project.progress.toInt()
    }

    fun settingViewColor(project: ProjectModel) {
        materialCardView.getBackground()
            .setTint(Color.parseColor(project.colorBackground.toString()))
        txtNameProjectDetail.setTextColor(Color.parseColor(project.colorText.toString()))
        txtDescProjectDetail.setTextColor(Color.parseColor(project.colorText.toString()))
        txtSprintProjectDetail.setTextColor(Color.parseColor(project.colorText.toString()))

        btnBackDetailProject.getBackground()
            .setTint(Color.parseColor(project.colorBackground.toString()))
        imgEditProjectStyle.getBackground()
            .setTint(Color.parseColor(project.colorBackground.toString()))

        txtTitleDetailProject.setTextColor(Color.parseColor(project.colorText.toString()))
        txtPercentDetail.setTextColor(Color.parseColor(project.colorText.toString()))
        val csl = ColorStateList.valueOf(Color.parseColor(project.colorText.toString()))
        imgEditProjectStyle.iconTint = csl
        btnBackDetailProject.iconTint = csl
        imgProjectDetail.clipToOutline = true

    }

    fun goToHome() {
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    fun goToStyleProject() {
        val intent =
            Intent(
                this,
                UpdateProjectStyleActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
}