package com.portfolio.myapp.ui.view.updateProjectStyle.fragments.iconTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.icon.IconModel
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.IconAdapter
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener
import com.portfolio.myapp.utils.constant.ITEM_PLACEHOLDER
import kotlinx.android.synthetic.main.fragment_select_icon.view.*


class SelectIconFragment(val listener: FragmentListener) : Fragment(), IconAdapter.IconClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var viewOfLayout: View
    lateinit var recyclerViewIcon: RecyclerView
    lateinit var adapter: IconAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout =  inflater.inflate(R.layout.fragment_select_icon, container, false)


        val layoutManager: LinearLayoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        adapter = IconAdapter(this)
        recyclerViewIcon = viewOfLayout.recyclerIcon
        recyclerViewIcon.layoutManager = layoutManager
        recyclerViewIcon.adapter = adapter

        val colorModel = mutableListOf<IconModel>()
        colorModel.add(IconModel(ITEM_PLACEHOLDER))
        adapter.setListColor(getIcons())
        adapter.notifyDataSetChanged()
        getIcons()
        return viewOfLayout
    }
    private fun getIcons(): MutableList<IconModel> {
        val iconModel = mutableListOf<IconModel>()
        iconModel.add(IconModel("https://cdn1.iconfinder.com/data/icons/logotypes/32/google-drive-256.png","drive"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/logos-brands-3/24/logo_brand_brands_logos_maps_google-256.png","maps"))
        iconModel.add(IconModel("https://cdn4.iconfinder.com/data/icons/social-media-logos-6/512/103-GooglePlay_play_google_play_apps-256.png","play"))
        iconModel.add(IconModel("https://cdn1.iconfinder.com/data/icons/logotypes/32/square-facebook-128.png","facebook"))
        iconModel.add(IconModel("https://cdn2.iconfinder.com/data/icons/social-media-2285/512/1_Youtube_colored_svg-128.png","Youtube"))
        iconModel.add(IconModel("https://cdn2.iconfinder.com/data/icons/social-media-2285/512/1_Twitter3_colored_svg-128.png","Twitter"))
        iconModel.add(IconModel("https://cdn4.iconfinder.com/data/icons/logos-and-brands/512/343_Twitch_logo-128.png","Twitch"))
        iconModel.add(IconModel("https://cdn4.iconfinder.com/data/icons/logos-and-brands/512/27_Artstation_logo_logos-128.png","Artstation"))
        iconModel.add(IconModel("https://cdn1.iconfinder.com/data/icons/logotypes/32/pinterest-128.png","pinterest"))
        iconModel.add(IconModel("https://cdn2.iconfinder.com/data/icons/new-year-resolutions/64/resolutions-16-128.png","resolution"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/logos-brands-3/24/logo_brand_brands_logos_ubuntu-128.png","ubuntu"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/logos-brands-3/24/logo_brand_brands_logos_google_sheets-256.png","sheets"))
        iconModel.add(IconModel("https://cdn4.iconfinder.com/data/icons/small-n-flat/24/floppy-256.png","floppy"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/education-and-school-8/48/Book-256.png","Book"))
        iconModel.add(IconModel("https://cdn2.iconfinder.com/data/icons/random-set-1/420/Asset_91-256.png","Asset"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/logos-brands-3/24/logo_brand_brands_logos_icloud-256.png","icloud"))
        iconModel.add(IconModel("https://cdn3.iconfinder.com/data/icons/logos-brands-3/24/logo_brand_brands_logos_sketch_app-256.png","sketch"))
        iconModel.add(IconModel("https://cdn2.iconfinder.com/data/icons/bitsies/128/EditDocument-256.png","Document"))
        return iconModel
    }
    override fun onIconClickListener(item: IconModel) {
        listener.onIconSelectListener(item)
    }

}