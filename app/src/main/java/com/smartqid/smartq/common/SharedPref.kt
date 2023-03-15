package com.smartqid.smartq.common

import android.content.Context

class SharedPref(val context: Context) {
    private val FIRST_TIME = "first_time"
    private val PREF_NAME = "smartq"

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, 0)

    fun isFirstTime(): Int = sharedPreferences.getInt(FIRST_TIME, 0)

    fun firstTime() {
        val editor = sharedPreferences.edit()
        editor.putInt(FIRST_TIME, 1)
        editor.apply()
    }
}