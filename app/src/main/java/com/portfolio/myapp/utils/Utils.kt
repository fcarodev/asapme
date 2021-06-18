package com.portfolio.myapp.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.portfolio.myapp.R

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

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    fun showNotification(user:String,pass:String,context: Context,){
        val   notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel: NotificationChannel
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel("ID_CHANNEL_NOTIFICATIONS", "Canal para envio de notificaciones", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(context, "ID_CHANNEL_NOTIFICATIONS")
                .setContentTitle("Cambio de contrasena")
                .setContentText("Hola ${user}! esta es tu nueva contrasena: ${pass}, no la pierdas!")
                .setSmallIcon(R.drawable.ic_email)
        } else {
            builder = Notification.Builder(context)
                .setContentTitle("Cambio de contrasena")
                .setContentText("Hola ${user}! esta es tu nueva contrasena: ${pass}, no la pierdas!")
                .setSmallIcon(R.drawable.ic_email)
        }
        notificationManager.notify(1234, builder.build())
    }

    fun getImageDrawable(name:String){

    }

}