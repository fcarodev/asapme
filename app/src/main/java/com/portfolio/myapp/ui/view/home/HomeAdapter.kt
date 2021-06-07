package com.portfolio.myapp.ui.view.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import kotlinx.android.synthetic.main.item_row_home.view.*

class HomeAdapter(private val context:Context): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    private var projectList = mutableListOf<ProjectModel>() //retorna un ArrayList

     fun setListProject(projectModels:MutableList<ProjectModel>){
       projectList = projectModels
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_row_home,parent,false)
        return HomeViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
       val projectModel: ProjectModel = projectList[position]
        holder.bindView(projectModel)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    /**
     * Al ser inner class, cuando la instancia del padre (HomeAdapter) deje de existir,
     * la instancia de HomeViewHolder tambien dejara de existir
     */
    inner class HomeViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        fun bindView(projectModel: ProjectModel){
            itemView.imgProjectHome.clipToOutline = true
            Glide.with(context).load(projectModel.imgUrl)
                //.apply(RequestOptions().circleCrop())
            .into(itemView.imgProjectHome)

            itemView.txtDescProjectHome.text = projectModel.description
            itemView.txtDescProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            itemView.txtNameProjectHome.text = projectModel.name
            itemView.txtNameProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            itemView.txtSprintProjectHome.text = projectModel.currentSprint
            itemView.txtSprintProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            itemView.txtPercentHome.text = projectModel.progress + "%"
            itemView.txtPercentHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            itemView.progressBarProjectHome.progress = projectModel.progress.toInt()
            itemView.cardViewProjectHome.getBackground().setTint(Color.parseColor(projectModel.colorBackground.toString()))
        }
    }
}