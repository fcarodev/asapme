package com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.image.ImageModel
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.ImageAdapter
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.fragment_select_image.view.*


class SelectImageFragment(var listener: FragmentListener) : Fragment(),ImageAdapter.ImageClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var viewOfLayout: View
    lateinit var recyclerViewImage: RecyclerView
    lateinit var adapter: ImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_select_image, container, false)
        val layoutManager: LinearLayoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        adapter = ImageAdapter(this)
        recyclerViewImage = viewOfLayout.recyclerImage
        recyclerViewImage.layoutManager = layoutManager
        recyclerViewImage.adapter = adapter

        val colorModel = mutableListOf<ImageModel>()
        colorModel.add(ImageModel(ITEM_PLACEHOLDER, "#ffffff"))
        adapter.setListImage(getImages())
        adapter.notifyDataSetChanged()
        return viewOfLayout
    }

    private fun getImages(): MutableList<ImageModel> {
        val imageModel = mutableListOf<ImageModel>()
        imageModel.add(ImageModel("ic_radiant_gradient", "#ffffff","gradient"))
        imageModel.add(ImageModel("ic_rose_petals", "#ffffff","petals"))
        imageModel.add(ImageModel("ic_square_versatiles", "#ffffff","square"))
        imageModel.add(ImageModel("ic_radiant_gradient", "#ffffff","gradient"))
        imageModel.add(ImageModel("ic_abstract_envelope", "#ffffff","envelope"))
        imageModel.add(ImageModel("ic_flat_mountains", "#ffffff","mountains"))
        imageModel.add(ImageModel("ic_quantum_gradient", "#ffffff","quantum"))
        imageModel.add(ImageModel("ic_repeating_triangles", "#ffffff","triangles"))
        imageModel.add(ImageModel("ic_confetti_doodles", "#ffffff","confetti"))
        imageModel.add(ImageModel("ic_endless_constellation", "#ffffff","endless"))
        imageModel.add(ImageModel("ic_spectrum_gradient", "#ffffff","spectrum"))

        return imageModel
    }

    override fun onImageClickListener(item: ImageModel) {
        listener.onSVGBackgroundListener(item)
    }

}

