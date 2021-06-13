package com.portfolio.myapp.ui.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.portfolio.myapp.R
import com.portfolio.myapp.databinding.ActivityMainBinding
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.ui.view.login.LoginActivity
import com.portfolio.myapp.utils.manager.HawkManager


class SplashActivity : AppCompatActivity() {
    private val TIME:Long = 1000

    private lateinit var binding: ActivityMainBinding
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
            if(isUserLoggerId()){
                goToHome()
            }else{
                goToLogin()
            }

        }, TIME * 2)
    }

    private fun isUserLoggerId(): Boolean = HawkManager().getUserLoggedIn().innerId != "0"

    private fun goToLogin(){
        val intent =
            Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }
    private fun goToHome(){
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        goToActivityAnimation()
        finish()
    }

}