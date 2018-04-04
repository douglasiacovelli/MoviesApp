package com.iacovelli.moviesapp.common

import android.databinding.BaseObservable
import android.util.Log
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.models.SimpleConfiguration
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter(
        baseContract: BaseContract,
        private val getCachedConfiguration: GetCachedConfiguration =
                GetCachedConfiguration(baseContract.getContext())
): BaseObservable() {

    val loadingPresenter = LoadingPresenter(::tryAgain)
    val compositeDisposable = CompositeDisposable()
    var configuration = getCachedConfiguration()

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

    private fun getCachedConfiguration(): SimpleConfiguration? {
        return getCachedConfiguration.execute()
    }

}