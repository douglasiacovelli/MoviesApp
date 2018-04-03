package com.iacovelli.moviesapp.models

data class TvShowResponse(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<TvShow>
) {

    fun isNextPageAvailable() = page < totalPages
}