package com.iacovelli.moviesapp.common.configuration

import android.content.Context
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_IMAGE_SIZE
import com.iacovelli.moviesapp.common.io.GetSharedPreferences
import com.iacovelli.moviesapp.models.SimpleConfiguration

class SaveConfiguration(private val context: Context) {
    fun execute(configuration: SimpleConfiguration) {
        val sharedPreferences = GetSharedPreferences(context).execute()
        val editor = sharedPreferences.edit()
        editor.putString(CONFIG_BASE_URL, configuration.baseUrl)
        editor.putString(CONFIG_IMAGE_SIZE, configuration.imageSize)
        editor.apply()
    }
}