package com.iacovelli.moviesapp.common

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    var presenter: BasePresenter? = null

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}