package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_update_user.*
import kotlinx.android.synthetic.main.activity_update_user.textView6

class UpdateUserActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }
    var actualUser = UserModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnBackUpdateUser.setOnClickListener {
           goToHome()
        }

        btnUpdateUser.setOnClickListener {
            updateUser()
        }
        actualUser = HawkManager().getUserLoggedIn()
        rut.setText(actualUser.rut)
        name.setText(actualUser.name)
        lastName.setText(actualUser.lastName)
        email.setText(actualUser.email)
        startAnimation()

    }

    private fun updateUser() {
        actualUser = HawkManager().getUserLoggedIn()
        actualUser.rut =rut.text.toString()
        actualUser.name = name.text.toString()
        actualUser.lastName = lastName.text.toString()
        actualUser.email = email.text.toString()
        viewModel.updateUser(actualUser).observe(this, Observer { resultUser->
            if(resultUser.innerId != "0"){
                Logger.i("updateUser ${Gson().toJson(resultUser)}")
            }
        })

    }

    private fun startAnimation() {
        ViewAnimator
            .animate(btnBackUpdateUser)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleUpdateProfile)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etRutUpdateUser)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2400f, 0f)

            .andAnimate(etLastNameUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)

            .andAnimate(etEmailUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2800f, 0f)

            .thenAnimate(btnUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .start()
    }

    fun goToHome(){
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
    override fun onBackPressed() {
        goToHome()
    }
}