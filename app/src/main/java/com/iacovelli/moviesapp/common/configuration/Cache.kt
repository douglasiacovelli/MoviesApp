package com.iacovelli.moviesapp.common.configuration

interface Cache {
    fun contains(key: String): Boolean
    fun getString(key: String): String
}