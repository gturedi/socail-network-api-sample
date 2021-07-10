@file:Suppress("unused")

package com.gturedi.socialnetworkapp.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.R
import com.squareup.picasso.Picasso

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

fun ImageView.loadImageUrl(url: String?) =
    Picasso.get()
        .apply {
            isLoggingEnabled = BuildConfig.DEBUG
        }
        .load(url)
        //.placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
        //.placeholder(android.R.color.darker_gray)
        .placeholder(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_arrow_circle_down_24)!!)
        .error(R.drawable.ic_baseline_error_outline_24)
        .into(this)