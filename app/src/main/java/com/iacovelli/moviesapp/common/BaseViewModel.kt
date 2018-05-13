package com.iacovelli.moviesapp.common

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.ScreenStatus.Status.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.IOException


open class BaseViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val screenStatus = MutableLiveData<ScreenStatus>()

    open fun onClickTryAgain() {
        Log.w("debug", "This method should be overridden")
    }

    fun addToDisposables(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun setLoading() {
        screenStatus.value = ScreenStatus(LOADING)
    }

    fun setOk() {
        screenStatus.value = ScreenStatus(OK)
    }

    fun setTryAgain(throwable: Throwable) {
        val message = if (throwable is IOException) {
            R.string.connection_error
        } else {
            R.string.unexpected_error
        }

        screenStatus.value = ScreenStatus(TRY_AGAIN, message)
    }

}

class ScreenStatus(val status: Status, val message: Int? = null) {
    enum class Status {
        LOADING,
        TRY_AGAIN,
        OK
    }

    val loadingVisibility: Int
        get() = if (status == LOADING) View.VISIBLE else View.GONE

    val tryAgainVisibility: Int
        get() = if (status == TRY_AGAIN) View.VISIBLE else View.GONE

    val pageVisibility: Int
        get() = if (status == OK) View.GONE else View.VISIBLE
}