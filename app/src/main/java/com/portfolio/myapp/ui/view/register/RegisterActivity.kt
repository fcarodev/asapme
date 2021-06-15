package com.portfolio.myapp.ui.view.register

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.ui.view.login.LoginActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.textView6

class RegisterActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(UserViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.statusBarColor = Color.parseColor("#ffffff")
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnBackRegister.setOnClickListener { goToLogin() }
        btnRegister.setOnClickListener { registerUser() }

        ViewAnimator
            .animate(btnBackRegister)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleRegisterUser)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etRutRegister)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameRegister)
            .translationX(2400f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etLastNameRegister)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)

            .andAnimate(etEmailRegister)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2800f, 0f)

            .andAnimate(etPasswordRegister)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(3000f, 0f)

            .thenAnimate(btnRegister)
            .alpha(0f, 1f)
            .duration(800)
            .start()

    }

    private fun goToLogin() {
        val intent =
            Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }

    private fun registerUser() {
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

    fun goToHome() {
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

}