package com.iacovelli.moviesapp.common

import com.iacovelli.moviesapp.models.TvShow

interface CallbackClickTvShow {
    fun onClick(tvShow: TvShow)
}