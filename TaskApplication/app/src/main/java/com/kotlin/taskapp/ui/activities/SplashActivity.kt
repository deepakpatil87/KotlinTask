package com.kotlin.taskapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity

import com.kotlin.taskapp.R

class SplashActivity : AppCompatActivity() {
    private lateinit var mDelayHandler: Handler
    private val splashDelay: Long = 3000 //3 seconds

    internal val mRunnable: Runnable = Runnable {

        val intent = Intent(applicationContext, ProductActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Initializing the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler.postDelayed(mRunnable, splashDelay)
    }
}