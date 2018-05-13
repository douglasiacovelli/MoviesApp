package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.common.ui.ItemTvShowPresenter
import com.iacovelli.moviesapp.models.TvShow

class ItemTvShowFactory {
    fun create(tvShow: TvShow,
               callback: ((tvShow: TvShow) -> Unit)? = null
    ) = ItemTvShowPresenter(tvShow, callback)
}