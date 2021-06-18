package com.portfolio.myapp.ui.view.updateProjectStyle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.image.ImageModel
import com.portfolio.myapp.ui.view.updateProjectStyle.viewholder.ImageViewHolder
import com.portfolio.myapp.utils.constant.ITEM_EMPTY_DATA
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.item_row_image.view.*

class ImageAdapter(val itemClickListener: ImageClickListener) : RecyclerView.Adapter<ImageViewHolder<*>>() {
    private var imageList = mutableListOf<ImageModel>() //retorna un ArrayList

    fun setListImage(imageList: MutableList<ImageModel>){
        this.imageList = imageList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder<*> {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_empty_data_image, parent, false)
                ImageViewEmptyData(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_placeholder_image, parent, false)
                ImageViewPlaceholder(view)

            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_image, parent, false)
                ImageViewHolderData(view)
            }
            else -> throw IllegalArgumentException("error view type")
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (imageList[0].imgName == ITEM_EMPTY_DATA) {
            return 0
        }
        if (imageList[0].imgName == ITEM_PLACEHOLDER) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: ImageViewHolder<*>, position: Int) {
        when (holder) {
            is ImageAdapter.ImageViewEmptyData -> holder.bindView(
                ImageModel(ITEM_EMPTY_DATA),
                itemClickListener
            )
            is ImageAdapter.ImageViewPlaceholder -> holder.bindView(
                ImageModel(ITEM_PLACEHOLDER),
                itemClickListener
            )
            is ImageAdapter.ImageViewHolderData -> holder.bindView(
                imageList[position],
                itemClickListener
            )
        }
    }
    override fun getItemCount(): Int {
        if (imageList[0].imgName == ITEM_EMPTY_DATA) {
            return 1
        }
        if (imageList[0].imgName == ITEM_PLACEHOLDER) {
            return 10
        }
        return imageList.size
    }
    inner class ImageViewPlaceholder(itemView: View): ImageViewHolder<ImageModel>(itemView){
        override fun bindView(item: ImageModel, itemClickListener: ImageClickListener) {
          val shimer: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_view_container_image)
            shimer.startShimmer()
        }

    }
    inner class ImageViewEmptyData(itemView: View):  ImageViewHolder<ImageModel>(itemView){
        override fun bindView(item: ImageModel, itemClickListener: ImageClickListener) {

        }

    }
    inner class ImageViewHolderData(itemView: View):  ImageViewHolder<ImageModel>(itemView){
        override fun bindView(item: ImageModel, itemClickListener: ImageClickListener) {
            val id: Int = itemView.context.getResources().getIdentifier(
                "com.portfolio.myapp:drawable/${item.imgName}",
                null,
                null
            )
            itemView.imgBack.setImageResource(id);
            itemView.txtImageName.text = item.shortName
            itemView.cardImgImage.setOnClickListener { itemClickListener.onImageClickListener(item) }
        }

    }

    interface ImageClickListener{
        fun onImageClickListener(item: ImageModel)
    }
}