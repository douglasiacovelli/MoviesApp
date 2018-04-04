package com.iacovelli.moviesapp.details

import com.iacovelli.moviesapp.common.io.MoviesRetrofit
import com.iacovelli.moviesapp.common.io.TvShowService
import com.iacovelli.moviesapp.models.TvShow
import io.reactivex.Single

class GetTvShow {
    fun execute(id: Int): Single<TvShow> {
        return MoviesRetrofit.instance.create(TvShowService::class.java)
                .getTvShow(id)
    }
}