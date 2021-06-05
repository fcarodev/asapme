package com.portfolio.myapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.portfolio.myapp.R
import com.portfolio.myapp.extentions.backFromActivityAnimation
import com.portfolio.myapp.extentions.goToActivityAnimation
import com.portfolio.myapp.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    lateinit var btnRegister: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener{
            val intent =
                Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            goToActivityAnimation()
            finish()
        }


    }
}