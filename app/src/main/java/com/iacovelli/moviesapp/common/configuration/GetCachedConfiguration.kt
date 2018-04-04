package com.iacovelli.moviesapp.common.configuration

import android.content.Context
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.common.io.GetSharedPreferences
import com.iacovelli.moviesapp.models.SimpleConfiguration

class GetCachedConfiguration(private val context: Context) {
    fun execute(): SimpleConfiguration? {
        val sharedPreference = GetSharedPreferences(context).execute()
        return if (sharedPreference.contains(CONFIG_POSTER_SIZE)) {
            val baseUrl = sharedPreference.getString(CONFIG_BASE_URL, "")
            val backdropSize = sharedPreference.getString(CONFIG_BACKDROP_SIZE, "")
            val posterSize = sharedPreference.getString(CONFIG_POSTER_SIZE, "")
            SimpleConfiguration(baseUrl, backdropSize, posterSize)
        } else {
            null
        }
    }

}