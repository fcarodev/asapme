package com.portfolio.myapp.ui.view.task

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class TaskViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(item: T, itemClickListener: TaskAdapter.TaskClickListener)
}