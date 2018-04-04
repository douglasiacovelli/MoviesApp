package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.common.BaseContract
import com.iacovelli.moviesapp.models.TvShow

interface TvShowListContract: BaseContract {
    fun openTvShowDetail(tvShow: TvShow)
    fun setupList(tvShowList: ArrayList<TvShow>)
}