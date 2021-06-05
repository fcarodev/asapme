package com.portfolio.myapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R

class HomeActivity : AppCompatActivity() {
    lateinit var recyclerViewHome:RecyclerView
    lateinit var adapter: HomeAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = HomeAdapter(this)
        recyclerViewHome = findViewById(R.id.recyclerHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = adapter


        //adapter.setListProject(dummyList)
        adapter.notifyDataSetChanged()


    }
}
