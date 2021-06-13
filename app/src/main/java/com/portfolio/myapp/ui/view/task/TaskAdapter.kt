package com.portfolio.myapp.ui.view.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.data.model.task.TaskModel
import kotlinx.android.synthetic.main.item_row_sprint.view.*

class TaskAdapter(val itemClickListener:TaskClickListener):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = mutableListOf<TaskModel>() //retorna un ArrayList

    fun setListProject(taskList:MutableList<TaskModel>){
        this.taskList = taskList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_task,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskModel: TaskModel = taskList[position]
        holder.bindView(taskModel)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(taskModel: TaskModel){
            //itemView.txtSprintName.text = taskModel.name
           // itemView.cardDetailProject.setOnClickListener{
           //     itemClickListener.onTaskClickListener()
           // }
        }
    }

    interface TaskClickListener{
        fun onTaskClickListener()
    }
}