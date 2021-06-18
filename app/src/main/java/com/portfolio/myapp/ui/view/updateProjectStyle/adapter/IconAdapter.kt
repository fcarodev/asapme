package com.portfolio.myapp.ui.view.updateProjectStyle.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.icon.IconModel
import com.portfolio.myapp.ui.view.updateProjectStyle.viewholder.IconViewHolder
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.item_row_color.view.*
import kotlinx.android.synthetic.main.item_row_home.view.*
import kotlinx.android.synthetic.main.item_row_icon.view.*

class IconAdapter(val itemClickListener: IconAdapter.IconClickListener) : RecyclerView.Adapter<IconViewHolder<*>>() {
    private var iconList = mutableListOf<IconModel>() //retorna un ArrayList

    fun setListColor(iconList: MutableList<IconModel>){
        this.iconList = iconList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder<*> {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_empty_data_icon, parent, false)
                IconViewEmptyData(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_placeholder_icon, parent, false)
                IconViewPlaceholder(view)

            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_icon, parent, false)
                IconViewHolderData(view)
            }
            else -> throw IllegalArgumentException("error view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (iconList[0].imgUrl == ITEM_EMPTY_DATA) {
            return 0
        }
        if (iconList[0].imgUrl == ITEM_PLACEHOLDER) {
            return 1
        }
        return 2
    }
    override fun onBindViewHolder(holder: IconViewHolder<*>, position: Int) {
        when (holder) {
            is IconAdapter.IconViewEmptyData -> holder.bindView(
                IconModel(ITEM_EMPTY_DATA),
                itemClickListener
            )
            is IconAdapter.IconViewPlaceholder -> holder.bindView(
                IconModel(ITEM_PLACEHOLDER),
                itemClickListener
            )
            is IconAdapter.IconViewHolderData -> holder.bindView(
                iconList[position],
                itemClickListener
            )
        }
    }

    override fun getItemCount(): Int {
        if (iconList[0].imgUrl == ITEM_EMPTY_DATA) {
            return 1
        }
        if (iconList[0].imgUrl == ITEM_PLACEHOLDER) {
            return 10
        }
        return iconList.size
    }

    inner class IconViewPlaceholder(itemView: View): IconViewHolder<IconModel>(itemView){
        override fun bindView(item: IconModel, itemClickListener: IconClickListener) {
            val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container_icon)
            shimer.startShimmer()
        }


    }
    inner class IconViewEmptyData(itemView: View): IconViewHolder<IconModel>(itemView){
        override fun bindView(item: IconModel, itemClickListener: IconClickListener) {

        }

    }
    inner class IconViewHolderData(itemView: View): IconViewHolder<IconModel>(itemView){
        override fun bindView(item: IconModel, itemClickListener: IconClickListener) {
            Glide.with(itemView.context).load(item.imgUrl)
                .placeholder(itemView.context.getDrawable(R.drawable.ic_project_placeholder))
                .into(itemView.imgIconSelect)
            itemView.txtIconName.text = item.iconName
            itemView.cardIconImage.setOnClickListener {
                itemClickListener.onIconClickListener(item)
            }
        }

    }

    interface IconClickListener{
        fun onIconClickListener(item: IconModel)
    }
}