package com.portfolio.myapp.ui.view.home

import android.content.Intent
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
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.projectdetail.ProjectDetailActivity
import com.portfolio.myapp.ui.view.registerProject.RegisterProjectActivity
import com.portfolio.myapp.ui.view.splash.SplashActivity
import com.portfolio.myapp.ui.view.updateUser.ChangePasswordActivity
import com.portfolio.myapp.ui.view.updateUser.UpdateUserActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() , BottomSheetProfile.ProfileClickListener,HomeAdapter.ProjectClickListener{
    lateinit var recyclerViewHome:RecyclerView
    lateinit var adapter: HomeAdapter
    lateinit var topAppBar: MaterialToolbar
    private val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    var bottomSheetRegisterUser = BottomSheetProfile(UserModel(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = HomeAdapter(this,this)
        topAppBar = findViewById(R.id.topAppBarHome)
        recyclerViewHome = findViewById(R.id.recyclerHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = adapter

        getAllProjectByUser()

        swipeRefreshAudit.setOnRefreshListener {
            getAllProjectByUser()
        }

        btnAddProjectEmpty.setOnClickListener {
            goToRegisterProject()
        }
        btnAddProject.setOnClickListener{
            goToRegisterProject()
        }
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

             bottomSheetRegisterUser.show(
                 this.supportFragmentManager,
                "bottomSheetRegisterUser"
              )
        }

    }
    fun getAllProjectByUser(){
        val id = HawkManager().getUserLoggedIn().innerId
        viewModel.getAllProjectByUser(id).observe(this, Observer { projectList ->
            if(projectList.isEmpty()){
                showEmptyList()
            }else{
                showDataList(projectList)
            }

        })
    }

    fun showEmptyList(){
        recyclerViewHome.visibility = View.GONE
        itemEmpty.visibility = View.VISIBLE
        btnAddProject.visibility = View.GONE
    }
    fun showDataList(projectList:MutableList<ProjectModel>){
        Logger.i(Gson().toJson(projectList))
        itemEmpty.visibility = View.GONE
        btnAddProject.visibility = View.VISIBLE
        recyclerViewHome.visibility = View.VISIBLE
        adapter.setListProject(projectList)
        adapter.notifyDataSetChanged()
        if(swipeRefreshAudit.isRefreshing){
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
    fun goToRegisterProject(){
        val intent =
            Intent(this, RegisterProjectActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    fun goToSplash(){
        val intent =
            Intent(this, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    fun goToDetailProject(){
        val intent =
            Intent(this, ProjectDetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    fun goToUpdateUser(){
        val intent =
            Intent(this, UpdateUserActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    fun goToChangePassword(){
        val intent =
            Intent(this, ChangePasswordActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
}
