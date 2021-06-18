package com.portfolio.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.portfolio.myapp.data.model.color.ColorModel
import com.portfolio.myapp.data.model.color.ColorProvider
import com.portfolio.myapp.data.model.project.ProjectModel

class ColorViewModel : ViewModel() {
    val repo = ColorProvider()

    fun getAllColors(): LiveData<MutableList<ColorModel>> {
        val mutableData = MutableLiveData<MutableList<ColorModel>>()
        repo.getColors().observeForever{ colorList ->
            mutableData.value = colorList
        }
        return mutableData
    }
}