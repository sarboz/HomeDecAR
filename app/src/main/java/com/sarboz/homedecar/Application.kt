package com.sarboz.homedecar

import android.app.Application
import android.content.Intent


class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            handleUncaughtException(thread, e)
        }
    }

    fun handleUncaughtException(thread: Thread?, e: Throwable) {

    }
}