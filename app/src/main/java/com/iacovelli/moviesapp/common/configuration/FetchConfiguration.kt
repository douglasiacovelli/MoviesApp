package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.common.BaseContract
import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.iacovelli.moviesapp.models.SimpleConfiguration
import io.reactivex.Single

class FetchConfiguration(
        contract: BaseContract,
        private val saveConfiguration: SaveConfiguration = SaveConfiguration(contract.getContext())
) {
    fun execute(): Single<SimpleConfiguration> {
        return MoviesRetrofit().instance.create(ConfigurationService::class.java)
                .getConfiguration()
                .map {
                    val baseUrl = it.images.secureBaseUrl
                    val imageSizes = it.images.backdropSizes[it.images.backdropSizes.lastIndex - 1]
                    SimpleConfiguration(baseUrl, imageSizes)
                }
                .doOnSuccess {
                    saveConfiguration.execute(it)
                }
    }
}