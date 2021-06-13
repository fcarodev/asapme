package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import kotlinx.android.synthetic.main.activity_update_user.*

class UpdateUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)
        btnBackUpdateUser.setOnClickListener {
           goToHome()
        }

        ViewAnimator
            .animate(btnBackUpdateUser)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleUpdateProfile)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etRutUpdateUser)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNameUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2400f, 0f)

            .andAnimate(etLastNameUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)

            .andAnimate(etEmailUpdateUser)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2800f, 0f)

            .thenAnimate(btnRegister)
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
}