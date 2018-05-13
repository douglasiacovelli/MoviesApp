package com.iacovelli.moviesapp.tvshowlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.iacovelli.moviesapp.common.BaseViewModel
import com.iacovelli.moviesapp.common.configuration.FetchConfiguration
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.common.configuration.SharedPreference
import com.iacovelli.moviesapp.models.SimpleConfiguration
import com.iacovelli.moviesapp.models.TvShowResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TvShowListViewModel (
        private val getTvShowList: GetTvShowList = GetTvShowList(),
        private val fetchConfiguration: FetchConfiguration,
        private val getCachedConfiguration: GetCachedConfiguration
): BaseViewModel() {

    private var configuration: SimpleConfiguration? = null
    private var loadingNextResults = false

    val tvShowResponse = MutableLiveData<TvShowResponse>()

    init {
        fetchData()
    }

    override fun onClickTryAgain() {
        fetchData()
    }

    fun shouldFetchNextPage(position: Int, loadedItemsCount: Int): Boolean {
        tvShowResponse.value?.let {
            return !loadingNextResults
                    && position >= loadedItemsCount - 4
                    && it.isNextPageAvailable()
        }
        return false
    }

    fun fetchNextPage(): Observable<TvShowResponse> {
        loadingNextResults = true
        return getTvShowList.execute(tvShowResponse.value?.page?.plus(1) ?: 1)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    tvShowResponse.value = it
                }
                .doAfterTerminate {
                    loadingNextResults = false
                }
                .toObservable()
    }

    private fun fetchData() {
        setLoading()
        configuration = getCachedConfiguration.execute()

        val configurationPipeline = if (configuration == null) {
            fetchConfigurationFromTheServer()
        } else {
            Single.just(configuration)
        }

        val disposable = configurationPipeline
                .subscribeOn(Schedulers.io())
                .flatMap {
                    getTvShowList.execute(1)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setOk()
                    tvShowResponse.value = it
                }, {
                    setTryAgain(it)
                })

        addToDisposables(disposable)
    }

    private fun fetchConfigurationFromTheServer(): Single<SimpleConfiguration> {
        return fetchConfiguration.execute()
                .doOnSuccess {
                    configuration = it
                }
    }

    class Factory(val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            val sharedPreferences = SharedPreference(context)
            return TvShowListViewModel(
                    GetTvShowList(),
                    FetchConfiguration(sharedPreferences),
                    GetCachedConfiguration(sharedPreferences)) as T
        }
    }
}