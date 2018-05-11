package com.iacovelli.moviesapp.models

data class TvShow(
        val id: Int,
        val name: String,
        val voteAverage: Float,
        val backdropPath: String? = null,
        val overview: String? = null,
        val posterPath: String? = null,
        val similar: TvShowResponse
)