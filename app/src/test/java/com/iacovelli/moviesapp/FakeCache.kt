package com.iacovelli.moviesapp

import com.iacovelli.moviesapp.common.configuration.Cache
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.models.SimpleConfiguration

const val FAKE_URL = "baseUrl"
const val FAKE_POSTER_SIZE = "w300"
const val FAKE_BACKDROP_SIZE = "w720"

class FakeCache(private val existsInfo: Boolean = true): Cache {
    private var configurationSaved: SimpleConfiguration? = null

    init {
        if (existsInfo) {
            configurationSaved = SimpleConfiguration(FAKE_URL, FAKE_BACKDROP_SIZE, FAKE_POSTER_SIZE)
        }
    }

    override fun contains(key: String): Boolean {
        return configurationSaved != null
    }

    override fun getString(key: String): String {
        return when (key) {
            CONFIG_BASE_URL -> configurationSaved?.baseUrl ?: ""
            CONFIG_POSTER_SIZE -> configurationSaved?.posterSize ?: ""
            CONFIG_BACKDROP_SIZE -> configurationSaved?.backdropSize ?: ""
            else -> ""
        }
    }

    override fun saveConfiguration(configuration: SimpleConfiguration) {
        configurationSaved = configuration
    }
}