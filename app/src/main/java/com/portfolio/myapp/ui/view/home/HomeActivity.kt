package com.portfolio.myapp.ui.view.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.ui.view.registerProject.RegisterProjectActivity
import com.portfolio.myapp.ui.view.splash.SplashActivity
import com.portfolio.myapp.ui.view.updateUser.ChangePasswordActivity
import com.portfolio.myapp.ui.view.updateUser.UpdateUserActivity
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomSheetProfile.ProfileClickListener,
    HomeAdapter.ProjectClickListener {
    lateinit var recyclerViewHome: RecyclerView
    lateinit var adapter: HomeAdapter
    lateinit var topAppBar: MaterialToolbar
    private val viewModel by lazy { ViewModelProviders.of(this).get(ProjectViewModel::class.java) }
    var projectListSort = mutableListOf<ProjectModel>()
    var bottomSheetRegisterUser = BottomSheetProfile(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        adapter = HomeAdapter(this, this)
        topAppBar = findViewById(R.id.topAppBarHome)
        recyclerViewHome = findViewById(R.id.recyclerHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = adapter

        val projectList = mutableListOf<ProjectModel>()
        projectList.add(ProjectModel(ITEM_PLACEHOLDER))
        adapter.setListProject(projectList)
        adapter.notifyDataSetChanged()

        getAllProjectByUser()

        addProject.setOnClickListener { goToRegisterProject() }
        swipeRefreshAudit.setOnRefreshListener { getAllProjectByUser() }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    Logger.i("setOnMenuItemClickListener")
                    true

                }
                else -> false
            }
        }

        imgUser.setOnClickListener {
            if(!bottomSheetRegisterUser.isAdded){
                bottomSheetRegisterUser.show(this.supportFragmentManager, "bottomSheetRegisterUser")

            }
        }

        addSortProject.setOnClickListener {
            sortProjects()
        }

    }

    private fun sortProjects() {
        if (addSortProject.tag == "desc") {
            addSortProject.tag = "asc"
            Logger.i("sortedBy ")
            projectListSort.sortByDescending{it.progress}
        }else{
            addSortProject.tag = "desc"
            Logger.i("sortedByDescending ")
            projectListSort.sortBy { it.progress}
        }
        adapter.setListProject(projectListSort)
        adapter.notifyDataSetChanged()
    }

    fun getAllProjectByUser() {
        val projectListEmpty1 = mutableListOf<ProjectModel>()
        projectListEmpty1.add(ProjectModel(ITEM_PLACEHOLDER))
        showDataList(projectListEmpty1)
        val id = HawkManager().getUserLoggedIn().innerId
        viewModel.getAllProjectByUser(id).observe(this, Observer { projectList ->
            if (projectList.isEmpty()) {
                val projectListEmpty2 = mutableListOf<ProjectModel>()
                projectListEmpty2.add(ProjectModel(ITEM_EMPTY_DATA))
                showDataList(projectListEmpty2)
            } else {
                projectListSort = projectList
                showDataList(projectList)
            }
        })
    }

    fun showDataList(projectList: MutableList<ProjectModel>) {
        Logger.i(Gson().toJson(projectList))
        adapter.setListProject(projectList)
        adapter.notifyDataSetChanged()
        if (swipeRefreshAudit.isRefreshing) {
            swipeRefreshAudit.isRefreshing = false
        }
    }

    override fun onChangePasswordClickListener() {
        bottomSheetRegisterUser.dismiss()
        goToChangePassword()
    }

    override fun onChangeProfileClickListener() {
        bottomSheetRegisterUser.dismiss()
        goToUpdateUser()

    }

    override fun onLogoutClickListener() {
        bottomSheetRegisterUser.dismiss()
        HawkManager().removeAll()
        goToSplash()
    }

    override fun onProjectClickListener(innerId: String) {
        goToDetailProject()
    }

    override fun onCreateProjectClickListener() {
        goToRegisterProject()
    }

    fun goToRegisterProject() {
        val intent =
            Intent(
                this,
                RegisterProjectActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun goToSplash() {
        val intent =
            Intent(this, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    fun goToDetailProject() {
        val intent =
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun goToUpdateUser() {
        val intent =
            Intent(this, UpdateUserActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun goToChangePassword() {
        val intent =
            Intent(this, ChangePasswordActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
}
