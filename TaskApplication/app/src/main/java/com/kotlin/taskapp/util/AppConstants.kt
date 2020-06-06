package com.kotlin.odtviewm.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kotlin.taskapp.R
import com.kotlin.taskapp.TaskApplication


class AppConstants {
    companion object {

        var LIVE_URL = "https://mobile-tha-server.firebaseapp.com/" //Live URL


        const val MSG_NETWORK = "Turn on mobile data or connect to Wi-Fi to access this feature."
        const val PROCESSING_REQUEST = "Please wait..."


        fun showExceptionLog(ex: Exception) {

            ex.printStackTrace()
        }

        @SuppressLint("InflateParams")
        fun displayToast(toastMsg: String?) {
            val inflater = TaskApplication.getContext()!!
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.custom_toast, null)
            val text = view.findViewById<View>(R.id.text) as TextView
            text.text = toastMsg
            val toast = Toast(TaskApplication.getContext())
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = view
            toast.show()
        }
    }

    //***************** START **************


}