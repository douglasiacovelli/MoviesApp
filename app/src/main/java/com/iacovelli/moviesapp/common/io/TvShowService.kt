package com.iacovelli.moviesapp.common.io

import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/popular")
    fun getTvShows(@Query("page") page: Int): Single<TvShowResponse>

    @GET("tv/{id}?append_to_response=similar")
    fun getTvShow(@Path("id") tvShowId: Int)
}