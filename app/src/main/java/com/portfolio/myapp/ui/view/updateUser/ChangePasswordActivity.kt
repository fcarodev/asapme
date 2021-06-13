package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.florent37.viewanimator.ViewAnimator
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_change_password.textView6
import kotlinx.android.synthetic.main.activity_update_user.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        btnBackUpdatePass.setOnClickListener {
            goToHome()
        }

        ViewAnimator
            .animate(btnBackUpdatePass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(txtTitleChangePass)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(textView6)
            .translationX(2000f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etActualPass)  // desde aqui 200 mas
            .translationX(2200f, 0f)
            .alpha(0f, 1f)
            .duration(800)

            .andAnimate(etNewPass)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2400f, 0f)

            .andAnimate(etRepeatNewPass)
            .alpha(0f, 1f)
            .duration(800)
            .translationX(2600f, 0f)


            .thenAnimate(btnChangePass)
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