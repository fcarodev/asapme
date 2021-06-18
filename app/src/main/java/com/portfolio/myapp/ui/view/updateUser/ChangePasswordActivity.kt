package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.manager.HawkManager
import com.portfolio.myapp.viewmodel.ProjectViewModel
import com.portfolio.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_password.textView6
import kotlinx.android.synthetic.main.activity_update_user.*

class ChangePasswordActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnBackUpdatePass.setOnClickListener {
            goToHome()
        }
        startAnimation()
        btnChangePass.setOnClickListener {
            isPassUser()
        }
    }

    private fun isPassUser() {
        val actualUser = HawkManager().getUserLoggedIn()
        actualUser.password = actualPass.text.toString()
        viewModel.getUserByIdAndPass(actualUser).observe(this, Observer { userResult->
            if(userResult.innerId != "0"){
                userResult.password = newPass.text.toString()
                updateUserPass(userResult)
            }
        })
    }

    private fun updateUserPass(userModel: UserModel) {
        viewModel.updateUser(userModel).observe(this, Observer { resultUser->
            if(resultUser.innerId != "0"){
                HawkManager().setUserLoggedIn(resultUser)
            }
        })
    }

    private fun startAnimation() {
        ViewAnimator
            .animate(btnBackUpdatePass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleChangePass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etActualPass)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNewPass)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2400f, 0f)

            .andAnimate(etRepeatNewPass)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)


            .thenAnimate(btnChangePass)
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