package com.iacovelli.moviesapp.common.configuration

import android.content.Context
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_IMAGE_SIZE
import com.iacovelli.moviesapp.common.io.GetSharedPreferences
import com.iacovelli.moviesapp.models.SimpleConfiguration

class GetCachedConfiguration(private val context: Context) {
    fun execute(): SimpleConfiguration? {
        val sharedPreference = GetSharedPreferences(context).execute()
        return if (sharedPreference.contains(CONFIG_BASE_URL)) {
            val baseUrl = sharedPreference.getString(CONFIG_BASE_URL, "")
            val imageSize = sharedPreference.getString(CONFIG_IMAGE_SIZE, "")
            SimpleConfiguration(baseUrl, imageSize)
        } else {
            null
        }
    }

}