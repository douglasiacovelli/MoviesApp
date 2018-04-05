package com.iacovelli.moviesapp.details

import com.iacovelli.moviesapp.common.BasePresenter
import com.iacovelli.moviesapp.common.configuration.Cache
import com.iacovelli.moviesapp.common.configuration.GetDefaultCache
import com.iacovelli.moviesapp.common.ui.ItemTvShowPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
        private val contract: DetailsContract,
        private val tvShowId: Int,
        private val getTvShow: GetTvShow = GetTvShow(),
        cache: Cache = GetDefaultCache(contract.getContext()).execute()
): BasePresenter(contract, cache) {

    var tvShowPresenter: ItemTvShowPresenter? = null

    init {
        fetchData()
    }

    override fun tryAgain() {
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        tvShowPresenter?.onDestroy()
        tvShowPresenter = null
    }

    private fun fetchData() {
        setLoading(true)
        val disposable = getTvShow.execute(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setLoading(false)
                    tvShowPresenter = ItemTvShowPresenter(contract, it)
                    contract.setupList(it.similar!!.results)
                    notifyChange()
                }, {
                    showTryAgain()
                    handleError(it)
                })

        compositeDisposable.add(disposable)
    }

}