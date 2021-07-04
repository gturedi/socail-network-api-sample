package com.gturedi.socialnetworkapp.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes resId:Int) = Toast.makeText(this, this.getString(resId), Toast.LENGTH_SHORT).show()

fun Context.toast(text:String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()