package com.portfolio.myapp.ui.view.registerSprint

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.github.florent37.viewanimator.ViewAnimator
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.sprint.SprintModel
import com.portfolio.myapp.ui.view.projectdetail.SprintActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.hideKeyboard
import com.portfolio.myapp.utils.extentions.toSimpleString
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.SprintViewModel
import kotlinx.android.synthetic.main.activity_register_sprint.*



class RegisterSprintActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(SprintViewModel::class.java) }
    var sprint = SprintModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_sprint)
        Logger.i("Actual sprint " + Gson().toJson(HawkManager().getActualSprint()))
        sprint = HawkManager().getActualSprint()
        if(sprint.innerId == "-1"){ newSprint() } else{ updateSprintView() }
        startAnimation()

        btnBackAddSprint.setOnClickListener { goToDetailProject() }
        dateInitSprint.setOnClickListener {
            it.hideKeyboard()
            val dateInit = SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(false)
                .displayHours(false)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .listener { dateInit->
                    dateInitSprint.setText(dateInit.toSimpleString())
                    Logger.i(dateInit.toSimpleString())
                }
            dateInit.display()
        }

        dateFinishSprint.setOnClickListener {
            it.hideKeyboard()
            val dateFinish = SingleDateAndTimePickerDialog.Builder(this)
                .bottomSheet()
                .curved()
                .displayMinutes(false)
                .displayHours(false)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .listener { dateInit->
                    dateFinishSprint.setText(dateInit.toSimpleString())
                    Logger.i(dateInit.toSimpleString())
                }
            dateFinish.display()
        }

        btnRegisterSprint.setOnClickListener {
            if(sprint.innerId == "-1"){
                registerSprint()
            }else{
                updateSprint()
            }

        }

    }

    private fun updateSprint() {

    }



    private fun updateSprintView() {
        txtTitleSprintForm.text = "Actualizar sprint"
        btnRegisterSprint.text = "Actualizar sprint"
        nameSprint.setText(sprint.name)
        descSprint.setText(sprint.description)
        dateInitSprint.setText(sprint.dateInit)
        dateFinishSprint.setText(sprint.dateFinish)
    }

    private fun newSprint() {
        txtTitleSprintForm.text = "Nuevo sprint"
        btnRegisterSprint.text = "Crear sprint"
    }

    private fun registerSprint() {
        val sprint = SprintModel()
        sprint.isActive = true
        sprint.dateInit = dateInitSprint.text.toString()
        sprint.dateFinish = dateFinishSprint.text.toString()
        sprint.name= nameSprint.text.toString()
        sprint.idProject = HawkManager().getCurrentProject().innerId.toString()
        sprint.description = descSprint.text.toString()

        viewModel.addSprint(sprint).observe(this, Observer { sprint ->
            if (sprint != null){
                Logger.i(Gson().toJson(sprint))
                goToDetailProject()
            }

        })
    }
    private fun goToDetailProject() {
        val intent =
            Intent(this, SprintActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
    private fun startAnimation() {
        ViewAnimator
            .animate(btnBackAddSprint)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleCreateSprint)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleSprintForm)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameAddSprint)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etDescAddSprint)
            .translationX(2400f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etDateInitAddSprint)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)

            .andAnimate(etDateFinishAddSprint)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2800f, 0f)

            .thenAnimate(btnRegisterSprint)
            .alpha(0f, 1f)
            .duration(800)
            .start()

    }
}