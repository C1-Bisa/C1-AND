package com.binar.finalproject.utils

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.binar.finalproject.R

fun Toast.showCustomToast(message: String, activity: Activity, layoutResId: Int)
{
    val layout = activity.layoutInflater.inflate (
        layoutResId,
        activity.findViewById(R.id.toast_container)
    )

    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}