package com.gturedi.socialnetworkapp.util

import android.content.SharedPreferences
import javax.inject.Inject

class PrefService @Inject constructor(
    private val prefs: SharedPreferences
) {

    private val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"

    fun accessToken(value: String) = prefs.edit()?.putString(KEY_ACCESS_TOKEN, value)?.apply()
    fun accessToken() = prefs.getString(KEY_ACCESS_TOKEN, "")

}