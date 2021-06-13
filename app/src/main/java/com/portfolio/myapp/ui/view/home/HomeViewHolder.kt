package com.portfolio.myapp.ui.view.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class HomeViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(item: T, itemClickListener: HomeAdapter.ProjectClickListener)
}