package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.models.Configuration
import io.reactivex.Single
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration")
    fun getConfiguration(): Single<Configuration>
}