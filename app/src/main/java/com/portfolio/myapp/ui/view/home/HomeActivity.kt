package com.portfolio.myapp.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var recyclerViewHome:RecyclerView
    lateinit var adapter: HomeAdapter
    lateinit var topAppBar: MaterialToolbar
    private val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = HomeAdapter(this)
        topAppBar = findViewById(R.id.topAppBarHome)
        recyclerViewHome = findViewById(R.id.recyclerHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = adapter

        getAllProjectByUser()

        swipeRefreshAudit.setOnRefreshListener {
            getAllProjectByUser()
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

    }
    fun getAllProjectByUser(){
        val id = HawkManager().getUserLoggedIn().innerId
        viewModel.getAllProjectByUser(id).observe(this, Observer { projectList ->
            Logger.i(Gson().toJson(projectList))
            adapter.setListProject(projectList)
            adapter.notifyDataSetChanged()
            if(swipeRefreshAudit.isRefreshing){
                swipeRefreshAudit.isRefreshing = false
            }
        })
    }

}
