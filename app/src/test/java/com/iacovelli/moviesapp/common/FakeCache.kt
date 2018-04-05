package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.common.configuration.Cache
import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE

const val FAKE_URL = "baseUrl"
const val FAKE_POSTER_SIZE = "w300"
const val FAKE_BACKDROP_SIZE = "w720"

class FakeCache(private val existsInfo: Boolean): Cache {

    override fun contains(key: String): Boolean {
        return existsInfo
    }

    override fun getString(key: String): String {
        if (!existsInfo) return ""

        return when (key) {
            CONFIG_BASE_URL -> FAKE_URL
            CONFIG_POSTER_SIZE -> FAKE_POSTER_SIZE
            CONFIG_BACKDROP_SIZE -> FAKE_BACKDROP_SIZE
            else -> ""
        }
    }
}