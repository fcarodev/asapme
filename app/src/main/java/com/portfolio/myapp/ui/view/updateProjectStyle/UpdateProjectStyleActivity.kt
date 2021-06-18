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
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.color.ColorModel
import com.portfolio.myapp.data.model.icon.IconModel
import com.portfolio.myapp.data.model.image.ImageModel
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.ui.view.updateProjectStyle.adapter.ViewPagerColorsAdapter
import com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab.CustomColorFragment
import com.portfolio.myapp.ui.view.updateProjectStyle.fragments.iconTab.SelectIconFragment
import com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab.SelectColorFragment
import com.portfolio.myapp.ui.view.updateProjectStyle.fragments.colorTab.SelectImageFragment
import com.portfolio.myapp.ui.view.updateProjectStyle.fragments.iconTab.CustomIconFragment
import com.portfolio.myapp.ui.view.updateProjectStyle.listener.FragmentListener
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.activity_update_project_style.*
import kotlinx.android.synthetic.main.item_row_icon.view.*


class UpdateProjectStyleActivity : AppCompatActivity(), FragmentListener {

    private val viewModel by lazy { ViewModelProviders.of(this).get(ProjectViewModel::class.java) }
    private val colorfulViews = hashSetOf<View>()
    private var colorizationConsumer: ColorizationConsumer? = null
    private val colorizeHSLColorCache = IntegerHSLColor()
    var colorCard = ""
    var colorText = ""
    var iconCard = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_project_style)
        setUpTabsColors()
        setUpTabsImages()
        btnBackStyleProject.setOnClickListener { goToDetailsProject() }
        val project = HawkManager().getCurrentProject()
        colorfulViews.add(btnBackStyleProject)
        colorfulViews.add(txtPercentStyle)
        colorfulViews.add(txtSprintProjecStyle)
        colorfulViews.add(txtDescProjectStyle)
        colorfulViews.add(cardEditStyle)
        colorfulViews.add(txtTitleStyleProject)
        colorfulViews.add(txtNameProjectStyle)


        btnSaveStyle.setOnClickListener {
            project.colorBackground = colorCard
            project.colorText = colorText
            project.imgUrl = iconCard
            updateStyleProject(project)
        }
    }

    private fun setUpTabsImages() {
        val adapter = ViewPagerColorsAdapter(supportFragmentManager)
        adapter.addFragment(SelectIconFragment(this),"Iconos")
        adapter.addFragment(CustomIconFragment(this),"Subir")

        viewPagerIcon.adapter = adapter
        tabIconLayout.setupWithViewPager(viewPagerIcon)
    }

    private fun setUpTabsColors() {
        val adapter = ViewPagerColorsAdapter(supportFragmentManager)
        adapter.addFragment(SelectColorFragment(this),"Predeterminado")
        adapter.addFragment(CustomColorFragment(this),"Custom")
        adapter.addFragment(SelectImageFragment(this),"SVG")

        viewPagerColor.adapter = adapter
        tabColorLayout.setupWithViewPager(viewPagerColor)

    }

    fun updateStyleProject(project: ProjectModel) {
        viewModel.updateProject(project).observe(this, Observer { project ->
            if (project != null) {
                Logger.i(Gson().toJson(project))
                HawkManager().setCurrentProject(project)
                goToDetailsProject()
            } else {
            }
        })
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

    override fun onBackPressed() {
        goToDetailsProject()
    }

    override fun onColorSelectListner(colorHex: IntegerHSLColor) {
       colorize(colorHex)
        window.statusBarColor = Color.parseColor(colorHex.toHex())
    }

    override fun onIconSelectListener(item: IconModel) {
        putIconProject(item)
    }

    private fun putIconProject(item: IconModel) {
        iconCard = item.imgUrl
        Glide.with(this).load(item.imgUrl)
            .placeholder(getDrawable(R.drawable.ic_project_placeholder))
            .into(imgProjectStyle)
    }

    override fun onColorStaticListener(colorModel: ColorModel) {
        Logger.i("Color selected: ${Gson().toJson(colorModel)}")
        changeColorView(colorModel)
    }

    override fun onSVGBackgroundListener(imageModel: ImageModel) {
        changeSVGBackground(imageModel)
    }

    private fun changeSVGBackground(imageModel: ImageModel) {

        btnBackStyleProject.backgroundTintList = ColorStateList.valueOf(0x00000000)
        btnBackStyleProject.setTextColor(Color.parseColor(imageModel.textColor))
        btnBackStyleProject.iconTint = ColorStateList.valueOf(Color.parseColor(imageModel.textColor))


        txtPercentStyle.setTextColor(Color.parseColor(imageModel.textColor))
        txtPercentStyle.setBackgroundColor(0x00000000)

        txtSprintProjecStyle.setTextColor(Color.parseColor(imageModel.textColor))
        txtSprintProjecStyle.setBackgroundColor(0x00000000)

        txtDescProjectStyle.setTextColor(Color.parseColor(imageModel.textColor))
        txtDescProjectStyle.setBackgroundColor(0x00000000)

        txtTitleStyleProject.setTextColor(Color.parseColor(imageModel.textColor))
        txtTitleStyleProject.setBackgroundColor(0x00000000)

        txtNameProjectStyle.setTextColor(Color.parseColor(imageModel.textColor))
        txtNameProjectStyle.setBackgroundColor(0x00000000)

        val id: Int = getResources().getIdentifier(
            "com.portfolio.myapp:drawable/${imageModel.imgName}",
            null,
            null
        )
        cardEditStyle.setBackgroundResource(id)

        colorCard = imageModel.imgName
        colorText = imageModel.textColor
    }

    private fun changeColorView(colorModel: ColorModel){
        window.statusBarColor = Color.parseColor(colorModel.back)
        btnBackStyleProject.setTextColor(Color.parseColor(colorModel.text))
        btnBackStyleProject.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorModel.back))
        btnBackStyleProject.iconTint = ColorStateList.valueOf(Color.parseColor(colorModel.text))

        txtPercentStyle.setTextColor(Color.parseColor(colorModel.text))
        txtPercentStyle.setBackgroundColor(Color.parseColor(colorModel.back))

        txtSprintProjecStyle.setTextColor(Color.parseColor(colorModel.text))
        txtSprintProjecStyle.setBackgroundColor(Color.parseColor(colorModel.back))

        txtDescProjectStyle.setTextColor(Color.parseColor(colorModel.text))
        txtDescProjectStyle.setBackgroundColor(Color.parseColor(colorModel.back))

        txtTitleStyleProject.setTextColor(Color.parseColor(colorModel.text))
        txtTitleStyleProject.setBackgroundColor(Color.parseColor(colorModel.back))

        txtNameProjectStyle.setTextColor(Color.parseColor(colorModel.text))
        txtNameProjectStyle.setBackgroundColor(Color.parseColor(colorModel.back))

        cardEditStyle.getBackground().setTint(Color.parseColor(colorModel.back))
        //constEditStyle.getBackground().setTint(Color.parseColor(colorModel.back))

        colorCard = colorModel.back
        colorText = colorModel.text
    }
}


interface ColorizationConsumer {
    fun colorize(color: IntegerHSLColor)
}
