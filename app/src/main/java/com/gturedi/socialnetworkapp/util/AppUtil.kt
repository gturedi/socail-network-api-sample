package com.gturedi.socialnetworkapp.util

import android.util.Log
import android.widget.Toast
import com.gturedi.socialnetworkapp.App
import com.gturedi.socialnetworkapp.BuildConfig

fun toast(text: String) = Toast.makeText(App.ctx, text, Toast.LENGTH_SHORT).show()

fun log(text: String, tag: String = "qq") {
    if (BuildConfig.DEBUG) {
        Log.i(tag, text)
    }
}

fun logErr(e: Throwable, msg: String = "", tag: String = "qq") {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg, e)
    }
}

fun runIfDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

fun runSafely(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        logErr(e)
    }
}

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

inline fun String?.isNullOrBlank(block: () -> String): String {
    return if (this.isNullOrBlank()) block()
    else this
}