package com.iacovelli.moviesapp.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.view.View
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.ui.ItemTvShowPresenter
import com.iacovelli.moviesapp.details.ScreenStatus.Status.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class ScreenStatus(val status: Status, val message: Int? = null) {
    enum class Status {
        LOADING,
        TRY_AGAIN,
        OK
    }

    val loadingVisibility: Int
        get() = if (status == LOADING) View.VISIBLE else View.GONE
}

class DetailsPresenter(
        private val tvShowId: Int,
        private val getTvShow: GetTvShow = GetTvShow()
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val tvShow = MutableLiveData<ItemTvShowPresenter>()
    val status = MutableLiveData<ScreenStatus>()

    init {
        fetchData()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun fetchData() {
        setLoading()
        val disposable = getTvShow.execute(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setOk()
                    tvShow.value = ItemTvShowPresenter(null, it)
                }, {
                    setTryAgain(it)
                })

        compositeDisposable.add(disposable)
    }

    private fun setLoading() {
        status.value = ScreenStatus(LOADING)
    }

    private fun setTryAgain(throwable: Throwable) {
        val message = if (throwable is IOException) {
            R.string.connection_error
        } else {
            R.string.unexpected_error
        }
        status.value = ScreenStatus(TRY_AGAIN, message)
    }

    private fun setOk() {
        status.value = ScreenStatus(OK)
    }

    class Factory(private val tvShowId: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailsPresenter(tvShowId) as T
        }
    }

}