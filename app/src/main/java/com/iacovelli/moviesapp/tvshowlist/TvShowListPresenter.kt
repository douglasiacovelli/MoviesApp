package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.common.BasePresenter
import com.iacovelli.moviesapp.common.configuration.FetchConfiguration
import com.iacovelli.moviesapp.models.SimpleConfiguration
import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowListPresenter(
        private val contract: TvShowListContract,
        private val getTvShowList: GetTvShowList = GetTvShowList(),
        private val fetchConfiguration: FetchConfiguration = FetchConfiguration(contract)
): BasePresenter(contract) {

    var tvShowResponse: TvShowResponse? = null
    var currentPage = 1
    var loadingNextResults = false

    init {
        fetchData()
    }

    override fun tryAgain() {
        fetchData()
    }

    fun shouldFetchNextPage(position: Int, loadedItemsCount: Int): Boolean {
        tvShowResponse?.let {
            return !loadingNextResults
                    && position >= loadedItemsCount - 4
                    && it.isNextPageAvailable()
        }
        return false
    }

    fun fetchNextPage(): Observable<TvShowResponse> {
        loadingNextResults = true
        return getTvShowList.execute(currentPage + 1)
                .doOnSuccess {
                    tvShowResponse = it
                    currentPage = it.page
                }
                .doAfterTerminate {
                    loadingNextResults = false
                }
                .toObservable()
    }

    private fun fetchData() {
        setLoading(true)

        val configurationPipeline = if (configuration == null) {
            fetchConfigurationFromTheServer()
        } else {
            Single.just(configuration)
        }

        val disposable = configurationPipeline
                .subscribeOn(Schedulers.io())
                .flatMap {
                    getTvShowList.execute(currentPage)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setLoading(false)
                    tvShowResponse = it
                    currentPage = it.page
                    contract.setupList(it.results)
                }, {
                    showTryAgain()
                    handleError(it)
                })

        compositeDisposable.add(disposable)
    }

    private fun fetchConfigurationFromTheServer(): Single<SimpleConfiguration> {
        return fetchConfiguration.execute()
                .doOnSuccess {
                    configuration = it
                }
    }
}