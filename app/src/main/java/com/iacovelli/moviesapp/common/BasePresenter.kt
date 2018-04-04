package com.iacovelli.moviesapp.common

import android.databinding.BaseObservable
import android.util.Log
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.common.ui.LoadingPresenter
import com.iacovelli.moviesapp.models.SimpleConfiguration
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException

abstract class BasePresenter(
        val baseContract: BaseContract,
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

    open fun onDestroy() {
        compositeDisposable.clear()
    }

    fun handleError(throwable: Throwable) {
        if (throwable is IOException) {
            baseContract.showMessage(R.string.connection_error)
        } else {
            baseContract.showMessage(R.string.unexpected_error)
        }
    }

    private fun getCachedConfiguration(): SimpleConfiguration? {
        return getCachedConfiguration.execute()
    }

}