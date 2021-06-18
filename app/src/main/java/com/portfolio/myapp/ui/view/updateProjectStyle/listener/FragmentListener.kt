package com.portfolio.myapp.ui.view.updateProjectStyle.listener

import codes.side.andcolorpicker.model.IntegerHSLColor
import com.portfolio.myapp.data.model.color.ColorModel
import com.portfolio.myapp.data.model.icon.IconModel
import com.portfolio.myapp.data.model.image.ImageModel

interface FragmentListener {
    fun onColorSelectListner(colorHex: IntegerHSLColor)
    fun onIconSelectListener(item: IconModel)
    fun onColorStaticListener(colorModel: ColorModel)
    fun onSVGBackgroundListener(imageModel: ImageModel)
}