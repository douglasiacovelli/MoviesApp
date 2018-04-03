package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.common.TvShowService
import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.Single

class GetTvShowList {
    fun execute(page: Int = 1): Single<TvShowResponse> {
        return MoviesRetrofit().instance.create(TvShowService::class.java)
                .getTvShows(page)
    }
}