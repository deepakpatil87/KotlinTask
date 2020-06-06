package com.kotlin.taskapp

import android.app.Application
import android.content.Context

class TaskApplication : Application() {
    companion object {
        var ctx: Context? = null

        fun getContext(): Context?
        {
            return ctx
        }

    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }


}
