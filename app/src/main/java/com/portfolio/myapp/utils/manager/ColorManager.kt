package com.portfolio.myapp.utils.manager

class ColorManager {

    fun getColors():MutableList<ColorBackground>{
        val colorList = mutableListOf<ColorBackground>()
        colorList.add(ColorBackground("#FFFFFF","#ffffffff"))
        return colorList
    }
}

data class ColorBackground(var backGroundColor:String = "-1",var colorText:String)