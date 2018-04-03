package com.iacovelli.moviesapp.common.io

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = "715970e8204ee604fb14e2dc55464cf3"

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val authorizedUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

        val authorizedRequest = originalRequest.newBuilder()
                .url(authorizedUrl)
                .build()

        return chain.proceed(authorizedRequest)
    }
}