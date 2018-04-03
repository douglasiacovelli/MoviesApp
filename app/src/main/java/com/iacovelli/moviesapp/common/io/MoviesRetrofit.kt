package com.iacovelli.moviesapp.common.io

import com.iacovelli.moviesapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRetrofit {
    private val BASE_URL: String = "https://api.themoviedb.org/3/"

    val instance: Retrofit
        get() {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonConverter.instance))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
        }


    private val okHttpClient: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            val apiKey = ApiKeyInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }

            return OkHttpClient.Builder()
                    .addInterceptor(apiKey)
                    .addInterceptor(logging)
                    .build()
        }
}