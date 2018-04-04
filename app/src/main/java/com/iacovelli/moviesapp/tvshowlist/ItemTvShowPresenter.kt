package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.models.TvShow

class ItemTvShowPresenter(
        private var contract: TvShowListContract? = null,
        private val tvShow: TvShow) {

    val name: String
        get() = tvShow.name

    val rating: Float
        get() = tvShow.voteAverage

    val backdropUrl: String?
        get() = tvShow.backdropPath

    fun onClick() {
        contract?.openTvShowDetail(tvShow)
    }

    fun onDestroy() {
        contract = null
    }
}