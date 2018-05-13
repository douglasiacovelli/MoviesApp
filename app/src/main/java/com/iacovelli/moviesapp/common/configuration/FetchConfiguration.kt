package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.iacovelli.moviesapp.models.SimpleConfiguration
import io.reactivex.Single

class FetchConfiguration(
        private val cache: Cache,
        private val configurationService: ConfigurationService =
                MoviesRetrofit.instance.create(ConfigurationService::class.java)
) {
    fun execute(): Single<SimpleConfiguration> {
        return  configurationService
                .getConfiguration()
                .map {
                    val baseUrl = it.images.secureBaseUrl
                    val backdropSize = it.images.backdropSizes[it.images.backdropSizes.lastIndex - 2]
                    val posterSize = it.images.posterSizes[it.images.posterSizes.lastIndex - 2]
                    SimpleConfiguration(baseUrl, backdropSize, posterSize)
                }
                .doOnSuccess {
                    cache.saveConfiguration(it)
                }
    }
}