package com.portfolio.myapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.model.Project

class HomeActivity : AppCompatActivity() {
    lateinit var recyclerViewHome:RecyclerView
    lateinit var adapter: HomeAdapter
    lateinit var topAppBar: MaterialToolbar
   // private val viewModel by lazy { ViewModelProviders.of(this).get(HomeViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = HomeAdapter(this)
        topAppBar = findViewById(R.id.topAppBarHome)
        recyclerViewHome = findViewById(R.id.recyclerHome)
        recyclerViewHome.layoutManager = LinearLayoutManager(this)
        recyclerViewHome.adapter = adapter

        var dummyList= mutableListOf<Project>()
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        dummyList.add(Project("0","Coverme","projecto movil","tablet support","https://play-lh.googleusercontent.com/PfPCUCIqMaGsiKpFB6dR9rcuDnsqTeu-rH0aDqH0iCxiHraWfel4-uJnY-JTXGi4LcQ=s180-rw","#6522ff","#ffffff",34))
        adapter.setListProject(dummyList)
        adapter.notifyDataSetChanged()

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
}
