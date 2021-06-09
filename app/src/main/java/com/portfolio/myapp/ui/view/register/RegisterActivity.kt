package com.portfolio.myapp.ui.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.login.LoginActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.viewmodel.LoginViewModel
import com.portfolio.myapp.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(RegisterViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnBackRegister.setOnClickListener {
            val intent =
                Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            backFromActivityAnimation()
            finish()
        }
        btnRegister.setOnClickListener {
            val userToRegister = UserModel()
            userToRegister.rut = rut.text.toString()
            userToRegister.name = name.text.toString()
            userToRegister.lastName = lastName.text.toString()
            userToRegister.password = pass.text.toString()
            userToRegister.email = email.text.toString()
            viewModel.registerUser(userToRegister).observe(this, Observer { user ->
                if (user.innerId == "0") {
                    Toast.makeText(this,"error al agregar",Toast.LENGTH_SHORT).show()
                } else {
                    goToHome()
                }
            })
        }

    }
    fun goToHome() {
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }


}