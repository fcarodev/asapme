package com.portfolio.myapp.ui.view.projectdetail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.registerSprint.RegisterSprintActivity
import com.portfolio.myapp.ui.view.task.TaskActivity
import com.portfolio.myapp.ui.view.updateProjectStyle.UpdateProjectStyleActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.SprintViewModel
import kotlinx.android.synthetic.main.activity_project_detail.*


class SprintActivity : AppCompatActivity(),SprintAdapter.SprintClickListener,BottomSheetSprint.SprintClickListener {
    private val viewModel by lazy { ViewModelProviders.of(this).get(SprintViewModel::class.java) }
    lateinit var recyclerViewDetail: RecyclerView
    lateinit var adapter: SprintAdapter
    var bottomSheetDetailSprint = BottomSheetSprint(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        txtTitleSprint.visibility = View.INVISIBLE
        addSprint.visibility  = View.INVISIBLE
        adapter = SprintAdapter(this)
        recyclerViewDetail = findViewById(R.id.recyclerDetailProject)
        recyclerViewDetail.layoutManager = LinearLayoutManager(this)
        recyclerViewDetail.adapter = adapter

        val sprintList = mutableListOf<SprintModel>()
        sprintList.add(SprintModel("PlaceholderDataSprint"))
        adapter.setListSprint(sprintList)
        adapter.notifyDataSetChanged()

        val project = HawkManager().getCurrentProject()
        settingViewColor(project)
        settingViewData(project)

        btnBackDetailProject.setOnClickListener { goToHome() }
        imgEditProjectStyle.setOnClickListener { goToStyleProject() }
        getSprints(HawkManager().getCurrentProject().innerId.toString())

        addSprint.setOnClickListener {
            HawkManager().setCurrentSprint(SprintModel("-1"))
            goToCreateSprint() }
    }

    private fun getSprints(idProject:String) {
        viewModel.getSprints(idProject).observe(this, Observer { listSprints ->
            if(listSprints.isEmpty()){
                txtTitleSprint.visibility = View.INVISIBLE
                addSprint.visibility  = View.INVISIBLE

                val sprintList = mutableListOf<SprintModel>()
                sprintList.add(SprintModel("EmptyDataSprintt"))
                adapter.setListSprint(sprintList)
                adapter.notifyDataSetChanged()
            }else{
                txtTitleSprint.visibility = View.VISIBLE
                addSprint.visibility  = View.VISIBLE
                adapter.setListSprint(listSprints)
                adapter.notifyDataSetChanged()
            }

        })
    }

    private fun settingViewData(project: ProjectModel) {
        txtNameProjectDetail.text = project.name
        txtDescProjectDetail.text = project.description
        txtSprintProjectDetail.text = project.currentSprint
        //Glide.with(this).load(project.imgUrl).placeholder(getDrawable(R.drawable.ic_project_placeholder)).into(imgProjectDetail)
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
        //imgProjectDetail.clipToOutline = true

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

    override fun onSprintClickListener(sprintModel: SprintModel) {
        HawkManager().setCurrentSprint(sprintModel)
        bottomSheetDetailSprint.show(this.supportFragmentManager, "bottomSheetDetailSprint")
    }

    override fun onCreateSprintClickListener() {
        HawkManager().setCurrentSprint(SprintModel("-1"))
        goToCreateSprint()
    }

    private fun goToCreateSprint() {
        val intent =
            Intent(
                this,
                RegisterSprintActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun goToTasks() {
        val intent =
            Intent(
                this,
                TaskActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    override fun onDeleteSprintClickListener(nameSprint: String) {
        bottomSheetDetailSprint.dismiss()
    }

    override fun onStartSprintClickListeenr(sprintModel: SprintModel) {
        bottomSheetDetailSprint.dismiss()
    }

    override fun onAddTaskClickListener(sprintModel: SprintModel) {
        bottomSheetDetailSprint.dismiss()
        goToTasks()
    }

    override fun onEditSprintClickListener(sprintModel: SprintModel) {
        HawkManager().setCurrentSprint(sprintModel)
        bottomSheetDetailSprint.dismiss()
        goToCreateSprint()
    }
}