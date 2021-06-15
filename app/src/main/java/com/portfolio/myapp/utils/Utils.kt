package com.portfolio.myapp.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView

class Utils {

    fun calcPercent(total:Int,num:Int):String{
        if (total==0) return "0"
        return ((num*100)/total).toString()
    }

     fun setProgressAnimate(pb: ProgressBar, progressTo: Int, durarionAnimation: Long) {
        val animation: ObjectAnimator =
            ObjectAnimator.ofInt(pb, "progress", pb.progress, progressTo * 100)
        animation.duration = durarionAnimation
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }
    fun startCountAnimation(number: TextView, value: Int, durarionAnimation: Long) {
        val animator: ValueAnimator = ValueAnimator.ofInt(0, value)
        animator.duration = durarionAnimation
        animator.addUpdateListener { animation ->
            number.text = animation.animatedValue.toString() + "%"
        }
        animator.start()
    }

}