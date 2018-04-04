package com.iacovelli.moviesapp.common.io

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCES = "com.iacovelli.moviesapp.SHARED_PREFERENCES"
const val CONFIG_BASE_URL = "com.iacovelli.moviesapp.CONFIG_BASE_URL"
const val CONFIG_IMAGE_SIZE = "com.iacovelli.moviesapp.CONFIG_IMAGE_SIZE"

class GetSharedPreferences(private val context: Context) {
    fun execute(): SharedPreferences {
        return context.getSharedPreferences(
                SHARED_PREFERENCES,
                Context.MODE_PRIVATE)
    }
}