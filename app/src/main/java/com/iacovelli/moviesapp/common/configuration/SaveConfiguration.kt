package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.models.SimpleConfiguration

class SaveConfiguration(private val cache: Cache) {
    fun execute(configuration: SimpleConfiguration) {
        cache.saveConfiguration(configuration)
    }
}