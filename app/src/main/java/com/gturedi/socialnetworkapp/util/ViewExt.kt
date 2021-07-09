@file:Suppress("unused")

package com.gturedi.socialnetworkapp.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun View.show(duration: Long = 10L) {
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(null)
}

fun View.hide(duration: Long = 10L) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
            }
        })
}

internal fun ViewGroup.inflate(layoutRes: Int) =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun TextView.underline() {
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}