package com.iacovelli.moviesapp.models

data class TvShow(
        val id: Int,
        val name: String,
        val voteAverage: Float,
        val backdropPath: String?,
        val overview: String?,
        val posterPath: String?,
        val similar: TvShowResponse?
)