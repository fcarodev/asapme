package com.portfolio.myapp.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.register.RegisterActivity
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogin: MaterialButton
    lateinit var txtAddUser: TextView
    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin = findViewById(R.id.btnLogin)
        txtAddUser = findViewById(R.id.txtAddUser)


        txtRegister.setOnClickListener {
            goToRegister()
        }

        btnLogin.setOnClickListener {
            doLogin()
        }
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

    fun goToRegister(){
        val intent =
            Intent(this, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }


}