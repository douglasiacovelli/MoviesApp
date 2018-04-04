package com.iacovelli.moviesapp.common

import android.content.Context
import android.support.annotation.StringRes

interface BaseContract {
    fun getContext(): Context
    fun showMessage(@StringRes resId: Int)
}