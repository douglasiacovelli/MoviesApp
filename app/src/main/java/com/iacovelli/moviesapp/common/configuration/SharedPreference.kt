package com.iacovelli.moviesapp.common.configuration

import android.content.Context
import com.iacovelli.moviesapp.common.io.GetSharedPreferences

class SharedPreference(context: Context): Cache {
    val sharedPreference = GetSharedPreferences(context).execute()

    override fun contains(key: String): Boolean {
        return sharedPreference.contains(key)
    }

    override fun getString(key: String): String {
        return sharedPreference.getString(key, "")
    }
}