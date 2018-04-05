package com.iacovelli.moviesapp.common.configuration

import android.content.Context

class GetDefaultCache(val context: Context) {
    fun execute(): Cache {
        return SharedPreference(context)
    }

}