package com.portfolio.myapp.extentions

import android.app.Activity
import com.portfolio.myapp.R

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

