package com.harshit.moengageassignment

import android.content.Context
import android.content.SharedPreferences

object Const {
    private const val PREF_NAME = "Pref"
    private const val KEY_DEVICE_TOKEN = "PREF_DEVICE_TOKEN"

    fun saveToken(context: Context, token: String) {
        openPref(context)?.edit()?.apply {
            putString(KEY_DEVICE_TOKEN, token)
            apply()
        }
    }

    fun getStringValue(context: Context): String? {
        return openPref(context)?.getString(KEY_DEVICE_TOKEN, null)
    }

    fun haveToken(context: Context): Boolean {
        return openPref(context)?.contains(KEY_DEVICE_TOKEN) ?: false
    }

    private fun openPref(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
