package com.damar.submitinter2.etc

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    fun getSharedPreferencesEditor(context: Context, name: String): SharedPreferences.Editor {
        val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPreferences.edit()
    }

    fun logout(context: Context) {
        val editor = getSharedPreferencesEditor(context, "onSignIn")
        editor.remove("token")
        editor.remove("status")
        editor.apply()
    }
}
