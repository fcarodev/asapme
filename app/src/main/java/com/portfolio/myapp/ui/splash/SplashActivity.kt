package com.portfolio.myapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.portfolio.myapp.R
import com.portfolio.myapp.databinding.ActivityMainBinding
import com.portfolio.myapp.extentions.goToActivityAnimation
import com.portfolio.myapp.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    val TIME:Long = 1000
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()
    }
    private fun startAnimation(){
        YoYo.with(Techniques.FadeIn)
                .duration(TIME)
                .repeat(0)
                .playOn(findViewById(R.id.ic_splash))
        Handler().postDelayed({
            goToLogin()
        }, TIME * 2)
    }
    private fun goToLogin(){
        val intent =
            Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    private fun goToHome(){

    }
}