package com.iacovelli.moviesapp.common.ui

import android.databinding.BaseObservable
import android.view.View

class LoadingPresenter(val tryAgainListener: () -> Unit): BaseObservable() {
    private var state = State.HIDDEN

    val loadingVisibility
        get() = if (state == State.LOADING) View.VISIBLE else View.GONE

    val pageVisibility
        get() = if (state == State.HIDDEN) View.GONE else View.VISIBLE

    val tryAgainVisibility
        get() = if (state == State.TRY_AGAIN) View.VISIBLE else View.GONE

    fun setLoading(loading: Boolean) {
        state = if (loading) {
            State.LOADING
        } else {
            State.HIDDEN
        }
        notifyChange()
    }

    fun showTryAgain() {
        state = State.TRY_AGAIN
        notifyChange()
    }

    fun onClickTryAgain() {
        tryAgainListener()
    }

    enum class State {
        HIDDEN,
        LOADING,
        TRY_AGAIN
    }
}