package com.portfolio.myapp.ui.view.updateProjectStyle.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.ColorAdapter

abstract class ColorViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(item: T, itemClickListener: ColorAdapter.ColorClickListener)
}