package com.portfolio.myapp.ui.view.updateUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.portfolio.myapp.R
import com.portfolio.myapp.ui.view.home.HomeActivity
import com.portfolio.myapp.utils.extentions.backFromActivityAnimation
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        btnBackUpdatePass.setOnClickListener {
            goToHome()
        }
    }

    fun goToHome(){
        val intent =
            Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        backFromActivityAnimation()
        finish()
    }
}