package com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import codes.side.andcolorpicker.view.picker.OnIntegerHSLColorPickListener
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener
import kotlinx.android.synthetic.main.fragment_custom_color.view.*


class CustomColorFragment(val listener: FragmentListener) : Fragment() {


    private val pickerGroup = PickerGroup<IntegerHSLColor>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun randomizePickedColor() {
        pickerGroup.setColor(
            IntegerHSLColor.createRandomColor().also {
                it.intL = 20 + it.intL / 2
            }
        )
    }
    private fun setUpColors() {
        pickerGroup.addListener(
            object : OnIntegerHSLColorPickListener() {
                override fun onColorPicked(
                    picker: ColorSeekBar<IntegerHSLColor>,
                    color: IntegerHSLColor,
                    value: Int,
                    fromUser: Boolean
                ) {
                    super.onColorPicked(
                        picker,
                        color,
                        value,
                        fromUser
                    )
                    //colorHex = color.toHex()
                }

                override fun onColorChanged(
                    picker: ColorSeekBar<IntegerHSLColor>,
                    color: IntegerHSLColor,
                    value: Int
                ) {
                    //colorHex = color.toHex()
                    Logger.i(color.floatH.toString())
                    listener.onColorSelectListner(color)

                }
            }
        )


    }
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_custom_color, container, false)
        pickerGroup.registerPickers(
            viewOfLayout.hueColorPickerSeekBar ,
            viewOfLayout.saturationColorPickerSeekBar,
            viewOfLayout.lightnessColorPickerSeekBar
        )
        setUpColors()
        randomizePickedColor()
        return viewOfLayout
    }

}