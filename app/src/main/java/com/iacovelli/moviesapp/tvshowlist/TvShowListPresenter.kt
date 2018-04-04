package com.iacovelli.moviesapp.tvshowlist

import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BasePresenter
import com.iacovelli.moviesapp.common.configuration.FetchConfiguration
import com.iacovelli.moviesapp.models.SimpleConfiguration
import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class TvShowListPresenter(
        private val contract: TvShowListContract,
        private val getTvShowList: GetTvShowList = GetTvShowList(),
        private val fetchConfiguration: FetchConfiguration = FetchConfiguration(contract)
): BasePresenter(contract) {

    var tvShowResponse: TvShowResponse? = null

    init {
        fetchData()
    }

    override fun tryAgain() {
        fetchData()
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
                    getTvShowList.execute()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    setLoading(false)
                }
                .subscribe({
                    tvShowResponse = it
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

    private fun handleError(throwable: Throwable) {
        if (throwable is IOException) {
            contract.showMessage(R.string.connection_error)
        } else {
            contract.showMessage(R.string.unexpected_error)
        }
    }
}