package com.portfolio.myapp.ui.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.portfolio.myapp.R
import com.portfolio.myapp.model.Project
import kotlinx.android.synthetic.main.item_row_home.view.*

class HomeAdapter(private val context:Context): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    private var projectList = mutableListOf<Project>() //retorna un ArrayList

     fun setListProject(projects:MutableList<Project>){
       projectList = projects
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_row_home,parent,false)
        return HomeViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       val project: Project = projectList[position]
        holder.bindView(project)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    /**
     * Al ser inner class, cuando la instancia del padre (HomeAdapter) deje de existir,
     * la instancia de HomeViewHolder tambien dejara de existir
     */
    inner class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        fun bindView(project: Project){
            itemView.imgProjectHome.clipToOutline = true
            Glide.with(context).load(project.imgUrl)
                //.apply(RequestOptions().circleCrop())
            .into(itemView.imgProjectHome)

            itemView.txtDescProjectHome.text = project.description
            itemView.txtDescProjectHome.setTextColor(Color.parseColor(project.colorText.toString()))

            itemView.txtNameProjectHome.text = project.name
            itemView.txtNameProjectHome.setTextColor(Color.parseColor(project.colorText.toString()))

            itemView.txtSprintProjectHome.text = project.currentSprint
            itemView.txtSprintProjectHome.setTextColor(Color.parseColor(project.colorText.toString()))

            itemView.txtPercentHome.text = project.progress.toString() + "%"
            itemView.txtPercentHome.setTextColor(Color.parseColor(project.colorText.toString()))

            itemView.progressBarProjectHome.progress = project.progress
            itemView.cardViewProjectHome.getBackground().setTint(Color.parseColor(project.colorBackground.toString()))
        }
    }

}