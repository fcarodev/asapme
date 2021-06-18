package com.portfolio.myapp.ui.view.task

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.task.TaskModel
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.item_row_empty_data_task.view.*
import kotlinx.android.synthetic.main.item_row_task.view.*


class TaskAdapter(val itemClickListener: TaskClickListener) :RecyclerView.Adapter<TaskViewHolder<*>>() {

    private var taskList = mutableListOf<TaskModel>() //retorna un ArrayList

    fun setListTask(taskList: MutableList<TaskModel>){
        this.taskList = taskList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder<*> {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_empty_data_task, parent, false)
                TaskViewEmptyData(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_placeholder_task, parent, false)
                TaskViewPlaceholder(view)

            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_task, parent, false)
                TaskViewHolderData(view)
            }
            else -> throw IllegalArgumentException("error view type")
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (taskList[0].idSprint == ITEM_EMPTY_DATA) {
            return 0
        }
        if (taskList[0].idSprint == ITEM_PLACEHOLDER) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: TaskViewHolder<*>, position: Int) {
        when (holder) {
            is TaskAdapter.TaskViewEmptyData -> holder.bindView(
                TaskModel(ITEM_EMPTY_DATA),
                itemClickListener
            )
            is TaskAdapter.TaskViewPlaceholder -> holder.bindView(
                TaskModel(ITEM_PLACEHOLDER),
                itemClickListener
            )
            is TaskAdapter.TaskViewHolderData -> holder.bindView(
                taskList[position],
                itemClickListener
            )
        }
    }

    override fun getItemCount(): Int {
        if (taskList[0].idSprint == ITEM_EMPTY_DATA) {
            return 1
        }
        if (taskList[0].idSprint == ITEM_PLACEHOLDER) {
            return 5
        }
        return taskList.size
    }

    inner class TaskViewPlaceholder(itemView: View): TaskViewHolder<TaskModel>(itemView){
        override fun bindView(item: TaskModel, itemClickListener: TaskClickListener) {
            val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container_task)
            shimer.startShimmer()
        }

    }
    inner class TaskViewEmptyData(itemView: View): TaskViewHolder<TaskModel>(itemView){
        override fun bindView(item: TaskModel, itemClickListener: TaskClickListener) {

            ViewAnimator
                .animate(itemView.parentTaskEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .andAnimate(itemView.btnAddTaskEmpty)
                .alpha(0f, 1f)
                .duration(800)
                .start()
            itemView.btnAddTaskEmpty.setOnClickListener {
                itemClickListener.onAddTaskClickListener()
            }

        }

    }
    inner class TaskViewHolderData(itemView: View): TaskViewHolder<TaskModel>(itemView) {
        override fun bindView(item: TaskModel, itemClickListener: TaskClickListener) {

            itemView.txtNameTask.setText(item.name)
            itemView.chkTask.isChecked = item.status
            if(item.status){
                itemView.chkTask.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                itemView.cardTask.getBackground()
                    .setTint(Color.parseColor("#303F9F"))
                itemView.txtNameTask.setTextColor(Color.parseColor("#FFFFFF"))
            }else{
                itemView.chkTask.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#303F9F")))
                itemView.cardTask.getBackground()
                    .setTint(Color.parseColor("#f2f2f7"))
                itemView.txtNameTask.setTextColor(Color.parseColor("#303F9F"))
            }
            itemView.chkTask.setOnClickListener{
                item.status = itemView.chkTask.isChecked
                itemClickListener.onEditStatusClickListener(item)
            }
            itemView.cardTask.setOnClickListener { itemClickListener.onTaskClickListener(item)  }
        }
    }

    interface TaskClickListener{
        fun onEditTaskClickListener(taskModel: TaskModel)
        fun onAddTaskClickListener()
        fun onEditStatusClickListener(taskModel: TaskModel)
        fun onTaskClickListener(taskModel: TaskModel)
    }
}