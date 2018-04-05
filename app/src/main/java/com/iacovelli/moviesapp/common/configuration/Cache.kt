package com.iacovelli.moviesapp.common.configuration

import com.iacovelli.moviesapp.models.SimpleConfiguration

interface Cache {
    fun contains(key: String): Boolean
    fun getString(key: String): String
    fun saveConfiguration(configuration: SimpleConfiguration)
}