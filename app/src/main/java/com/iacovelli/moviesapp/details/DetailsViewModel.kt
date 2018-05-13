package com.iacovelli.moviesapp.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.iacovelli.moviesapp.common.BaseViewModel
import com.iacovelli.moviesapp.common.ui.ItemTvShowPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(
        private val tvShowId: Int,
        private val getTvShow: GetTvShow = GetTvShow()
): BaseViewModel() {

    val tvShow = MutableLiveData<ItemTvShowPresenter>()

    init {
        fetchData()
    }

    private fun fetchData() {
        setLoading()
        val disposable = getTvShow.execute(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setOk()
                    tvShow.value = ItemTvShowPresenter(it)
                }, {
                    setTryAgain(it)
                })

        addToDisposables(disposable)
    }

    override fun onClickTryAgain() {
        fetchData()
    }

    class Factory(private val tvShowId: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailsViewModel(tvShowId) as T
        }
    }

}