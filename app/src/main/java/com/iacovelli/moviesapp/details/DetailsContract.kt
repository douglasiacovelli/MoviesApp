package com.iacovelli.moviesapp.details

import com.iacovelli.moviesapp.common.BaseContract
import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.models.TvShow

interface DetailsContract: BaseContract, OpenTvShowContract {
    fun setupList(tvShowList: ArrayList<TvShow>)
}