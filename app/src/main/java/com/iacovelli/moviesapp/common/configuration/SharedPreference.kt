package com.iacovelli.moviesapp.common.configuration

import android.content.Context
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.common.io.GetSharedPreferences
import com.iacovelli.moviesapp.models.SimpleConfiguration

class SharedPreference(context: Context): Cache {
    private val sharedPreference = GetSharedPreferences(context).execute()

    override fun contains(key: String): Boolean {
        return sharedPreference.contains(key)
    }

    override fun getString(key: String): String {
        return sharedPreference.getString(key, "")
    }

    override fun saveConfiguration(configuration: SimpleConfiguration) {
        val editor = sharedPreference.edit()
        editor.putString(CONFIG_BASE_URL, configuration.baseUrl)
        editor.putString(CONFIG_BACKDROP_SIZE, configuration.backdropSize)
        editor.putString(CONFIG_POSTER_SIZE, configuration.posterSize)
        editor.apply()
    }
}