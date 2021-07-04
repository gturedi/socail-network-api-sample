package com.gturedi.socialnetworkapp.util

import android.util.Log
import android.widget.Toast
import com.gturedi.socialnetworkapp.App

fun toast(text:String) = Toast.makeText(App.ctx, text, Toast.LENGTH_SHORT).show()

fun log(text:String) = Log.i("qq", text)