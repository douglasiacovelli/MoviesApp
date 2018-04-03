package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.common.BasePresenter
import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class TvShowListPresenter: BasePresenter() {
    var tvShowResponse: TvShowResponse? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        setLoading(true)
        val disposable = GetTvShowList().execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    setLoading(false)
                }
                .subscribe({
                    tvShowResponse = it
                }, {
                    showTryAgain()
                    if (it is IOException) {

                    } else {

                    }
                })
        compositeDisposable.add(disposable)
    }
}