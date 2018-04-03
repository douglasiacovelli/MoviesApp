package com.iacovelli.moviesapp.common

import android.view.View

class LoadingPresenter(val tryAgainListener: () -> Unit) {
    private var state = State.HIDDEN

    val loadingVisibility
        get() = if (state == State.LOADING) View.VISIBLE else View.GONE

    val tryAgainVisibility
        get() = if (state == State.TRY_AGAIN) View.VISIBLE else View.GONE

    fun setLoading(loading: Boolean) {
        state = if (loading) {
            State.LOADING
        } else {
            State.HIDDEN
        }
    }

    fun showTryAgain() {
        state = State.TRY_AGAIN
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