package com.iacovelli.moviesapp.details

import com.iacovelli.moviesapp.common.BasePresenter
import com.iacovelli.moviesapp.tvshowlist.ItemTvShowPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
        private val contract: DetailsContract,
        private val tvShowId: Int,
        private val getTvShow: GetTvShow = GetTvShow()
): BasePresenter(contract) {

    var tvShowPresenter: ItemTvShowPresenter? = null

    init {
        fetchData()
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