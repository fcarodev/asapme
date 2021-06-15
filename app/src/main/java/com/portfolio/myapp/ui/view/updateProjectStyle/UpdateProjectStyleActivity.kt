package com.portfolio.myapp.ui.view.updateProjectStyle

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import codes.side.andcolorpicker.converter.*
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import codes.side.andcolorpicker.view.picker.OnIntegerHSLColorPickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.activity_update_project_style.*


class UpdateProjectStyleActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(ProjectViewModel::class.java) }

    private val colorfulViews = hashSetOf<View>()
    private val pickerGroup =
        PickerGroup<IntegerHSLColor>()
    private var colorizationConsumer: ColorizationConsumer? = null
    private val colorizeHSLColorCache = IntegerHSLColor()
    var colorCard = ""
    var colorText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_project_style)
        btnBackStyleProject.setOnClickListener { goToDetailsProject() }
        val project = HawkManager().getCurrentProject()
        colorfulViews.add(btnBackStyleProject)
        colorfulViews.add(txtPercentStyle)
        colorfulViews.add(txtSprintProjecStyle)
        colorfulViews.add(txtDescProjectStyle)
        colorfulViews.add(cardEditStyle)
        colorfulViews.add(txtTitleStyleProject)
        colorfulViews.add(txtNameProjectStyle)

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
                    colorize(color)
                    window.statusBarColor = Color.parseColor(color.toHex())

                }
            }
        )

        pickerGroup.registerPickers(
            hueColorPickerSeekBar,
            saturationColorPickerSeekBar,
            lightnessColorPickerSeekBar
        )
        randomizePickedColor()


        btnSaveStyle.setOnClickListener {
            project.colorBackground = colorCard
            project.colorText = colorText
            updateStyleProject(project)
        }
    }

    fun updateStyleProject(project: ProjectModel) {
        viewModel.updateProject(project).observe(this, Observer { project ->
            if (project != null) {
                Logger.i(Gson().toJson(project))
                HawkManager().setCurrentProject(project)
                goToDetailsProject()
            } else {
                Logger.i("projecto nuloz")
            }

        })
    }
    private fun randomizePickedColor() {
        pickerGroup.setColor(
            IntegerHSLColor.createRandomColor().also {
                it.intL = 20 + it.intL / 2
            }
        )
    }
    fun IntegerHSLColor.toHex(): String {
        val color = floatArrayOf(floatH, floatS, floatL)
        val intColor = ColorUtils.HSLToColor(color)
        val red: Int = Color.red(intColor)
        val green: Int = Color.green(intColor)
        val blue: Int = Color.blue(intColor)
        return String.format("#%02x%02x%02x", red, green, blue)
    }


    private fun colorize(color: IntegerHSLColor) {
        val contrastColor = color.toContrastColor()
        val alphaContrastColor = color.toContrastColor(ContrastColorAlphaMode.LIGHT_BACKGROUND)
        colorizeHSLColorCache.setFrom(color)
        colorizeHSLColorCache.floatL = colorizeHSLColorCache.floatL.coerceAtMost(0.8f)
        val opaqueColorInt = colorizeHSLColorCache.toOpaqueColorInt()
        colorfulViews.forEach {
            when (it) {
                is MaterialButton -> {
                    it.setTextColor(contrastColor)
                    it.backgroundTintList = ColorStateList.valueOf(opaqueColorInt)
                    it.iconTint = ColorStateList.valueOf(contrastColor)
                }
                is TextView -> {
                    colorText = java.lang.String.format("#%06X", 0xFFFFFF and alphaContrastColor)
                    it.setTextColor(alphaContrastColor)
                    it.setBackgroundColor(color.toColorInt())
                    TextViewCompat.setCompoundDrawableTintList(
                        it,
                        ColorStateList.valueOf(alphaContrastColor)
                    )
                }
                is MaterialCardView -> {
                    colorCard = color.toHex()
                    it.getBackground().setTint(Color.parseColor(color.toHex()))
                }
            }
        }
        colorizationConsumer?.colorize(color)
    }
    fun goToDetailsProject() {
        val intent =
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }


}


interface ColorizationConsumer {
    fun colorize(color: IntegerHSLColor)
}
