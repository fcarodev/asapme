package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.login.LoginActivity
import com.portfolio.myapp.utils.Utils
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.textView6
import kotlinx.android.synthetic.main.activity_reset_password.txtTitleChangePass

class ResetPasswordActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        btnBackUResetPass.setOnClickListener { goToLogin() }

        btnResetPass.setOnClickListener {
            resetPassword()
        }

        startAnimation()
    }

    private fun startAnimation() {
        ViewAnimator
            .animate(btnBackUResetPass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleChangePass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etActualRut)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etActualEmail)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2400f, 0f)

            .thenAnimate(btnResetPass)
            .alpha(0f, 1f)
            .duration(800)
            .start()
    }

    private fun resetPassword() {
        val userModel = UserModel()
        userModel.rut = actualRut.text.toString()
        userModel.email = actualEMail.text.toString()
        viewModel.getUserByMailAndRut(userModel).observe(this, Observer { userResult ->
            if (userResult.innerId != "0") {
                userResult.password = Utils().getRandomString(6)
                updateUserPass(userResult)
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUserPass(userResult: UserModel) {
        viewModel.updateUser(userResult).observe(this, Observer {
            if (userResult.innerId != "0") {
                Utils().showNotification(userResult.name, userResult.password, this)
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        goToLogin()
    }

    private fun goToLogin() {
        val intent =
            Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
}