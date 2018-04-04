package com.iacovelli.moviesapp.models

data class ImageConfiguration(
        val baseUrl: String,
        val secureBaseUrl: String,
        val backdropSizes: List<String>,
        val posterSizes: List<String>
)