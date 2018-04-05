package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.common.BaseContract
import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.models.TvShow

interface TvShowListContract: BaseContract, OpenTvShowContract {
    fun setupList(tvShowList: ArrayList<TvShow>)
    fun addResults(results: ArrayList<TvShow>)
}