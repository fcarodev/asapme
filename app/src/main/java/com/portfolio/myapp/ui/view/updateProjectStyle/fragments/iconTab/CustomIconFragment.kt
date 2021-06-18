package com.portfolio.myapp.ui.view.updateProjectStyle.fragments.iconTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.IconAdapter
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener


class CustomIconFragment(val listener: FragmentListener) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout =  inflater.inflate(R.layout.fragment_custom_icon, container, false)


        return viewOfLayout
    }

}