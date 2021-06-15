package com.portfolio.myapp.ui.view.home

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.utils.Utils
import com.portfolio.myapp.utils.manager.HawkManager
import kotlinx.android.synthetic.main.item_row_empty_data_home.view.*
import kotlinx.android.synthetic.main.item_row_home.view.*

class HomeAdapter(private val context: Context, var itemClickListener: ProjectClickListener) :
    RecyclerView.Adapter<HomeViewHolder<*>>() {


    private var projectList = mutableListOf<ProjectModel>() //retorna un ArrayList

    fun setListProject(projectModels: MutableList<ProjectModel>) {
        projectList = projectModels
    }

    override fun getItemViewType(position: Int): Int {
        if (projectList[0].name == "EmptyDataProject") {
            return 0
        }
        if (projectList[0].name == "PlaceholderDataProject") {
            return 1
        }
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder<*> {
        return when (viewType) {
            0 -> {
                ProjectEmptyData(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_row_empty_data_home, parent, false)
                )
            }
            1 -> {
                ProjectPlaceHolderData(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_row_placeholder_home, parent, false)
                )
            }
            2 -> {
                ProjectViewHolderData(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_row_home, parent, false)
                )
            }
            else -> throw IllegalArgumentException("Error view type")
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder<*>, position: Int) {
        when (holder) {
            is ProjectEmptyData -> holder.bindView(
                ProjectModel("EmptyDataProject"),
                itemClickListener
            )
            is ProjectPlaceHolderData -> holder.bindView(
                ProjectModel("PlaceholderDataProject"),
                itemClickListener
            )
            is ProjectViewHolderData -> holder.bindView(projectList[position], itemClickListener)
        }

    }

    override fun getItemCount(): Int {
        if (projectList[0].name.equals("EmptyDataProject")) {
            return 1
        }
        if (projectList[0].name.equals("PlaceholderDataProject")) {
            return 10
        }
        return projectList.size
    }

    /**
     * Al ser inner class, cuando la instancia del padre (HomeAdapter) deje de existir,
     * la instancia de HomeViewHolder tambien dejara de existir
     */
    inner class ProjectViewHolderData(itemView: View) : HomeViewHolder<ProjectModel>(itemView) {

        override fun bindView(projectModel: ProjectModel, itemClickListener: ProjectClickListener) {

            val progressBarHome: ProgressBar =
                itemView.findViewById(R.id.progressBarProjectHomeInit)
            itemView.imgProjectHome.clipToOutline = true
            Glide.with(context).load(projectModel.imgUrl)
                .placeholder(context.getDrawable(R.drawable.ic_project_placeholder))
                .into(itemView.imgProjectHome)
            itemView.txtDescProjectHome.text = projectModel.description
            itemView.txtDescProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            itemView.txtNameProjectHome.text = projectModel.name
            itemView.txtNameProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))

            if (projectModel.currentSprint == "-1") {
                itemView.txtSprintProjectHome.text = "Sin sprints"
            } else {
                itemView.txtSprintProjectHome.text = projectModel.currentSprint
            }
            itemView.txtSprintProjectHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))
            //Utils().startCountAnimation(itemView.txtPercentHome,projectModel.progress.toInt(),2000)
            itemView.txtPercentHome.text = "${projectModel.progress}%"
            itemView.txtPercentHome.setTextColor(Color.parseColor(projectModel.colorText.toString()))
            //progressBarHome.progress = itemView.txtPercentHome.text.toString().replace("%","").toInt()
            itemView.cardViewProjectHome.getBackground()
                .setTint(Color.parseColor(projectModel.colorBackground.toString()))

            itemView.cardViewProjectHome.setOnClickListener {
                HawkManager().setCurrentProject(projectModel)
                itemClickListener.onProjectClickListener(projectModel.innerId.toString())
            }
        }
    }

    inner class ProjectEmptyData(itemView: View) : HomeViewHolder<ProjectModel>(itemView) {
        override fun bindView(item: ProjectModel, itemClickListener: ProjectClickListener) {
            ViewAnimator
                .animate(itemView.parentEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .andAnimate(itemView.btnAddProjectEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .start()
            itemView.btnAddProjectEmpty.setOnClickListener {
                itemClickListener.onCreateProjectClickListener()
            }
        }
    }

    inner class ProjectPlaceHolderData(itemView: View) : HomeViewHolder<ProjectModel>(itemView) {
        override fun bindView(item: ProjectModel, itemClickListener: ProjectClickListener) {
            val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container)
            shimer.startShimmer()
        }

    }

    interface ProjectClickListener {
        fun onProjectClickListener(innerId: String)
        fun onCreateProjectClickListener()
    }

}