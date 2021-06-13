package com.portfolio.myapp.ui.view.projectdetail

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class SprintViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(item: T, itemClickListener: SprintAdapter.SprintClickListener)
}