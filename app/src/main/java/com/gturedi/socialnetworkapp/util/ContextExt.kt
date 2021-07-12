package com.gturedi.socialnetworkapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent

fun Context.toast(@StringRes resId: Int) =
    Toast.makeText(this, this.getString(resId), Toast.LENGTH_SHORT).show()

fun Context.toast(text: String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.openCustomTab(url: String) =
    CustomTabsIntent.Builder()
        .build().also {
            it.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.launchUrl(this, Uri.parse(url))
        }