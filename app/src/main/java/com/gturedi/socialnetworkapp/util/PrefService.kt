package com.gturedi.socialnetworkapp.util

import android.content.Context
import com.gturedi.socialnetworkapp.App

object PrefService {

    private val prefs = App.ctx?.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"

    fun accessToken(value: String) = prefs?.edit()?.putString(KEY_ACCESS_TOKEN, value)?.apply()
    fun accessToken() = prefs?.getString(KEY_ACCESS_TOKEN, "")

}