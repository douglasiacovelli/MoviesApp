package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.common.io.CONFIG_BACKDROP_SIZE
import com.iacovelli.moviesapp.common.io.CONFIG_BASE_URL
import com.iacovelli.moviesapp.common.io.CONFIG_POSTER_SIZE
import com.iacovelli.moviesapp.models.SimpleConfiguration

class GetCachedConfiguration(private val cache: Cache) {
    fun execute(): SimpleConfiguration? {

        return if (cache.contains(CONFIG_POSTER_SIZE)) {
            val baseUrl = cache.getString(CONFIG_BASE_URL)
            val backdropSize = cache.getString(CONFIG_BACKDROP_SIZE)
            val posterSize = cache.getString(CONFIG_POSTER_SIZE)
            SimpleConfiguration(baseUrl, backdropSize, posterSize)
        } else {
            null
        }
    }

}