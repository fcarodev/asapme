package com.portfolio.myapp.ui.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.project.ProjectModel
import com.portfolio.myapp.databinding.ActivityMainBinding
import com.portfolio.myapp.utils.extentions.goToActivityAnimation
import com.portfolio.myapp.ui.view.login.LoginActivity




class SplashActivity : AppCompatActivity() {
    enum class Signal { OPEN, CLOSED, SENDING }
    val TIME:Long = 100

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
    fun main() {
        val students = arrayOf("Abel", "Bill", "Cindy", "Darla")
        printStudents(*students)
    }
    fun printStudents(vararg students: String) {
        for (student in students) println(student)
    }
}