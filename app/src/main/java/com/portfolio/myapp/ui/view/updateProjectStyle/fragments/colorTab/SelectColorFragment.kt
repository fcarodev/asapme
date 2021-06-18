package com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.color.ColorModel
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.ColorAdapter
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import com.portfolio.myapp.viewmodel.ColorViewModel
import kotlinx.android.synthetic.main.fragment_select_color.view.*


class SelectColorFragment(var listener: FragmentListener) : Fragment(),
    ColorAdapter.ColorClickListener {
    private val viewModel by lazy { ViewModelProviders.of(this).get(ColorViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var viewOfLayout: View
    lateinit var recyclerViewColor: RecyclerView
    lateinit var adapter: ColorAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout = inflater.inflate(R.layout.fragment_select_color, container, false)
        val layoutManager: LinearLayoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        adapter = ColorAdapter(this)
        recyclerViewColor = viewOfLayout.recyclerColor
        recyclerViewColor.layoutManager = layoutManager
        recyclerViewColor.adapter = adapter

        val colorModel = mutableListOf<ColorModel>()
        colorModel.add(ColorModel(ITEM_PLACEHOLDER, "#ffffff"))
        adapter.setListColor(getColors())
        adapter.notifyDataSetChanged()
        getColors()
        return viewOfLayout
    }

    private fun getColors(): MutableList<ColorModel> {
        val colorModel = mutableListOf<ColorModel>()
        colorModel.add(ColorModel("#33FF3F", "#ffffff"))
        colorModel.add(ColorModel("#3361FF", "#ffffff"))
        colorModel.add(ColorModel("#FF6B33", "#ffffff"))
        colorModel.add(ColorModel("#FF33C1", "#ffffff"))
        colorModel.add(ColorModel("#7D33FF", "#ffffff"))
        colorModel.add(ColorModel("#C70039", "#ffffff"))
        colorModel.add(ColorModel("#581845", "#ffffff"))
        colorModel.add(ColorModel("#E9967A", "#ffffff"))
        colorModel.add(ColorModel("#FFA07A", "#ffffff"))
        colorModel.add(ColorModel("#E9A6A6", "#ffffff"))
        colorModel.add(ColorModel("#99A3A4", "#ffffff"))
        colorModel.add(ColorModel("#ED9573", "#ffffff"))
        colorModel.add(ColorModel("#FF0000", "#ffffff"))
        colorModel.add(ColorModel("#104860", "#ffffff"))
        colorModel.add(ColorModel("#2E9934", "#ffffff"))
        colorModel.add(ColorModel("#FF5050", "#ffffff"))
        colorModel.add(ColorModel("#00838F", "#ffffff"))
        colorModel.add(ColorModel("#4CB7F9", "#ffffff"))
        return colorModel
    }

    override fun onColorClickListener(colorModel: ColorModel) {
        listener.onColorStaticListener(colorModel)
    }


}