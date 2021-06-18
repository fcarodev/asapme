package com.portfolio.myapp.ui.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.google.android.material.button.MaterialButton
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.register.RegisterActivity
import com.portfolio.myapp.ui.view.updateUser.ResetPasswordActivity
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: MaterialButton
    lateinit var txtAddUser: TextView
    private val viewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnLogin = findViewById(R.id.btnLogin)
        txtAddUser = findViewById(R.id.txtAddUser)

        txtForgotPass.setOnClickListener {
            goToResetPassword()
        }
        txtRegister.setOnClickListener {
            goToRegister()
        }

        btnLogin.setOnClickListener {
            doLogin()
        }

        val durationEntrance = 800.toLong()
        ViewAnimator
            .animate(textView4)
            .translationX(1000f, 0f)
            .alpha(0f, 1f)
            .duration(durationEntrance)
            .andAnimate(etUsername)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)
            .andAnimate(etPassword)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)
            .thenAnimate(btnLogin)
            .alpha(0f, 1f)
            .duration(800)
            .andAnimate(txtForgotPass)
            .alpha(0f, 1f)
            .duration(800)
            .andAnimate(linearBottomLogin)
            .alpha(0f, 1f)
            .duration(800)
            .start()
    }

    private fun goToResetPassword() {

            val intent =
                Intent(this, ResetPasswordActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            goToActivityAnimation()
            finish()

    }



    fun doLogin() {
        val email = email.text.toString()
        val pass = password.text.toString()
        txtErrorHome.visibility = View.INVISIBLE
        viewModel.login(email, pass).observe(this, Observer { user ->
            Logger.i("doLogin:${user.innerId}")
            if (user.innerId == "0") {
                txtErrorHome.visibility = View.VISIBLE
            } else {
                goToHome()
            }
        })
    }

    fun goToHome() {
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

    fun goToRegister() {
        val intent =
            Intent(this, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }


}