package com.gturedi.socialnetworkapp

import android.util.Log
import android.widget.Toast

fun toast(text:String) = Toast.makeText(App.ctx, text, Toast.LENGTH_SHORT).show()

fun log(text:String) = Log.i("qq", text)