package com.portfolio.myapp.ui.view.projectdetail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.registerSprint.RegisterSprintActivity
import com.portfolio.myapp.ui.view.task.TaskActivity
import com.portfolio.myapp.ui.view.updateProjectStyle.UpdateProjectStyleActivity
import com.portfolio.myapp.utils.Utils
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import com.portfolio.myapp.viewmodel.SprintViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.alert_dialog_delete_task.view.*


class SprintActivity : AppCompatActivity(), SprintAdapter.SprintClickListener,
    BottomSheetSprint.SprintClickListener {
    private val viewModelSprint by lazy {
        ViewModelProviders.of(this).get(SprintViewModel::class.java)
    }
    private val viewModelProject by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }
    lateinit var recyclerViewDetail: RecyclerView
    lateinit var adapter: SprintAdapter
    lateinit var progressProject: ProgressBar
    var bottomSheetDetailSprint = BottomSheetSprint(this)
    var lstSprintToCalc = mutableListOf<SprintModel>()
    var actualProject = ProjectModel()
    var sprintListSort = mutableListOf<SprintModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        constraintLayoutSprint.visibility = View.INVISIBLE
        progressProject = findViewById(R.id.progressBarProjectDetail)
        adapter = SprintAdapter(this)
        recyclerViewDetail = findViewById(R.id.recyclerDetailProject)
        recyclerViewDetail.layoutManager = LinearLayoutManager(this)
        recyclerViewDetail.adapter = adapter
        showPlaceholderData()

        actualProject = HawkManager().getCurrentProject()
        window.statusBarColor = Color.parseColor(actualProject.colorBackground.toString())
        settingViewColor(actualProject)
        settingViewData(actualProject)

        btnBackDetailProject.setOnClickListener { goToHome() }
        imgEditProjectStyle.setOnClickListener { goToStyleProject() }

        getSprints()

        addSprint.setOnClickListener {
            HawkManager().setCurrentSprint(SprintModel("-1"))
            goToCreateSprint()
        }

        addSortSprint.setOnClickListener {
            sortSprints()
        }
    }

    private fun getSprints() {
        viewModelSprint.getSprints(HawkManager().getCurrentProject().innerId.toString())
            .observe(this, Observer { listSprints ->
                lstSprintToCalc = listSprints
                updateProgressProject()
                if (listSprints.isEmpty()) {
                    constraintLayoutSprint.visibility = View.INVISIBLE
                    val sprintList = mutableListOf<SprintModel>()
                    sprintList.add(SprintModel("EmptyDataSprintt"))
                    adapter.setListSprint(sprintList)
                    adapter.notifyDataSetChanged()
                } else {
                    sprintListSort = listSprints
                    constraintLayoutSprint.visibility = View.VISIBLE
                    adapter.setListSprint(listSprints)
                    adapter.notifyDataSetChanged()
                }

            })
    }

    private fun sortSprints() {
        if (addSortSprint.tag == "desc") {
            addSortSprint.tag = "asc"
            Logger.i("sortedBy ")
            sprintListSort.sortByDescending { it.actualProgress.toInt() }
        } else {
            addSortSprint.tag = "desc"
            Logger.i("sortedByDescending ")
            sprintListSort.sortBy { it.actualProgress.toInt() }
        }
        adapter.setListSprint(sprintListSort)
        adapter.notifyDataSetChanged()
    }

    private fun updateProgressProject() {
        actualProject.progress = calculateProgressProject()
        viewModelProject.updateProject(actualProject).observe(this, Observer { projectResult ->
            HawkManager().setCurrentProject(projectResult)
            updateHeaderProject(projectResult)
        })
    }

    private fun calculateProgressProject(): String {
        val totalToDivide = lstSprintToCalc.size
        var totalProgressSprint = 0
        if (totalToDivide == 0) return "0"

        for (item in lstSprintToCalc) {
            totalProgressSprint += item.actualProgress.toInt()
        }
        return Utils().calcPercent(totalToDivide * 100, totalProgressSprint)
    }

    private fun settingViewData(project: ProjectModel) {
        txtNameProjectDetail.text = project.name
        txtDescProjectDetail.text = project.description
        txtSprintProjectDetail.text = project.currentSprint
        //Glide.with(this).load(project.imgUrl).placeholder(getDrawable(R.drawable.ic_project_placeholder)).into(imgProjectDetail)
        txtPercentDetail.text = "${project.progress}%"
        progressProject.progress = project.progress.toInt()
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

    override fun onDeleteSprintClickListener(nameSprint: SprintModel) {
        bottomSheetDetailSprint.dismiss()
        buildCustomDialog()
    }

    override fun onStartSprintClickListeenr(sprintModel: SprintModel) {
        bottomSheetDetailSprint.dismiss()
        showPlaceholderData()
        sprintModel.isActive = true
        val currentProject = HawkManager().getCurrentProject()
        currentProject.currentSprint = sprintModel.name
        viewModelProject.updateProject(currentProject).observe(this, Observer { projectResult ->
            actualProject = projectResult
            HawkManager().setCurrentProject(projectResult)
            viewModelSprint.updateSprintActive(sprintModel).observe(this, Observer { sprintResult ->
                getSprints()
            })
        })
    }
    private fun showPlaceholderData(){
        val sprintList = mutableListOf<SprintModel>()
        sprintList.add(SprintModel("PlaceholderDataSprint"))
        adapter.setListSprint(sprintList)
        adapter.notifyDataSetChanged()
    }
    override fun onAddTaskClickListener(sprintModel: SprintModel) {
        HawkManager().setCurrentSprint(sprintModel)
        bottomSheetDetailSprint.dismiss()
        goToTasks()
    }

    override fun onEditSprintClickListener(sprintModel: SprintModel) {
        HawkManager().setCurrentSprint(sprintModel)
        bottomSheetDetailSprint.dismiss()
        goToCreateSprint()
    }

    fun updateHeaderProject(projectResult: ProjectModel) {
        Logger.i("updateHeaderProject: " + Gson().toJson(projectResult))
        txtPercentDetail.text = "${projectResult.progress}%"
        progressProject.progress = txtPercentDetail.text.toString().replace("%", "").toInt()
    }

    fun buildCustomDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_delete_task, null)
        mDialogView.txtTitleCustomDialog.text = "Eliminar sprint"
        mDialogView.txtSubtitleCustomDialog.text = "Al eliminar un sprint, se eliminaran todas sus tareas permanentemente."
        mDialogView.btnActionCustomDialog.text = "Eliminar sprint"
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.btnActionCustomDialog.setOnClickListener {
            showPlaceholderData()
            mAlertDialog.dismiss()
            deleteSprint(HawkManager().getCurrentSprint())
        }
    }

    fun deleteSprint(currentSprint: SprintModel) {
        viewModelSprint.deleteSprint(currentSprint).observe(this, Observer { sprintResult->
            getSprints()
        })
    }
}