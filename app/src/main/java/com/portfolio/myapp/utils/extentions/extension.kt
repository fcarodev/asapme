package com.portfolio.myapp.utils.extentions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.portfolio.myapp.R
import java.text.SimpleDateFormat
import java.util.*

internal fun Activity.goToActivityAnimation() {
    overridePendingTransition(
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
}
internal fun Activity.backFromActivityAnimation() {
    overridePendingTransition(
        R.anim.slide_in_left,
        R.anim.slide_out_right
    )
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd-MM-yyyy")
    return format.format(this)
}