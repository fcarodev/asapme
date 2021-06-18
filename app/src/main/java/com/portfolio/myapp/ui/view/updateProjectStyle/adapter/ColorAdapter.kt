package com.portfolio.myapp.ui.view.updateProjectStyle.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.color.ColorModel
import com.portfolio.myapp.ui.view.updateProjectStyle.viewholder.ColorViewHolder
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.item_row_color.view.*

class ColorAdapter(val itemClickListener: ColorAdapter.ColorClickListener) : RecyclerView.Adapter<ColorViewHolder<*>>() {
    private var colorList = mutableListOf<ColorModel>() //retorna un ArrayList

    fun setListColor(colorList: MutableList<ColorModel>){
        this.colorList = colorList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder<*> {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_empty_data_color, parent, false)
                ColorViewEmptyData(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_placeholder_color, parent, false)
                ColorViewPlaceholder(view)

            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_color, parent, false)
                ColorViewHolderData(view)
            }
            else -> throw IllegalArgumentException("error view type")
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (colorList[0].back == ITEM_EMPTY_DATA) {
            return 0
        }
        if (colorList[0].back == ITEM_PLACEHOLDER) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: ColorViewHolder<*>, position: Int) {
        when (holder) {
            is ColorAdapter.ColorViewEmptyData -> holder.bindView(
                ColorModel(ITEM_EMPTY_DATA),
                itemClickListener
            )
            is ColorAdapter.ColorViewPlaceholder -> holder.bindView(
                ColorModel(ITEM_PLACEHOLDER),
                itemClickListener
            )
            is ColorAdapter.ColorViewHolderData -> holder.bindView(
                colorList[position],
                itemClickListener
            )
        }
    }
    override fun getItemCount(): Int {
        if (colorList[0].back == ITEM_EMPTY_DATA) {
            return 1
        }
        if (colorList[0].back == ITEM_PLACEHOLDER) {
            return 10
        }
        return colorList.size
    }
    inner class ColorViewPlaceholder(itemView: View): ColorViewHolder<ColorModel>(itemView){
        override fun bindView(item: ColorModel, itemClickListener: ColorClickListener) {
            val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container_color)
            shimer.startShimmer()
        }

    }
    inner class ColorViewEmptyData(itemView: View): ColorViewHolder<ColorModel>(itemView){
        override fun bindView(item: ColorModel, itemClickListener: ColorClickListener) {

        }

    }
    inner class ColorViewHolderData(itemView: View): ColorViewHolder<ColorModel>(itemView){
        override fun bindView(item: ColorModel, itemClickListener: ColorClickListener) {
            itemView.imgColor.setColorFilter(Color.parseColor(item.back.toString()));
            itemView.txtColorName.text = item.back
            val csl = ColorStateList.valueOf(Color.parseColor(item.back.toString().replace("#","#33")))
            itemView.cardImgColor.rippleColor = csl
            itemView.cardImgColor.setOnClickListener {
                //itemView.cardImgColor.strokeWidth = 2
                itemClickListener.onColorClickListener(item)
            }
        }

    }




    interface ColorClickListener{
        fun onColorClickListener(colorModel: ColorModel)
    }
}