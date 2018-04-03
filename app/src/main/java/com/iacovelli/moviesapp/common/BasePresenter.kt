package com.iacovelli.moviesapp.common

import android.databinding.BaseObservable
import android.util.Log
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter: BaseObservable() {
    val compositeDisposable = CompositeDisposable()
    private val loadingPresenter = LoadingPresenter(::tryAgain)

    open fun tryAgain() {
        Log.w("debug", "This method should be overridden")
    }

    fun setLoading(loading: Boolean) {
        loadingPresenter.setLoading(loading)
    }

    fun showTryAgain() {
        loadingPresenter.showTryAgain()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}