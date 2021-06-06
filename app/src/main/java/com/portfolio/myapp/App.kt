package com.portfolio.myapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        Logger.addLogAdapter(AndroidLogAdapter())

    }
}