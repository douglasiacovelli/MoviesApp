package com.iacovelli.moviesapp.tvshowlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.common.ui.TvShowListAdapter
import com.iacovelli.moviesapp.databinding.ActivityTvShowListBinding
import com.iacovelli.moviesapp.details.DetailsActivity
import com.iacovelli.moviesapp.models.TvShow
import com.iacovelli.moviesapp.models.TvShowResponse
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvShowListActivity : BaseActivity<TvShowListViewModel>() {
    private lateinit var dataBinding: ActivityTvShowListBinding
    private val compositeDisposable = CompositeDisposable()
    private val callback = { tvShow: TvShow ->
        openTvShow(tvShow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_list)
        dataBinding.setLifecycleOwner(this)

        val factory = TvShowListViewModel.Factory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(TvShowListViewModel::class.java)
        dataBinding.presenter = viewModel

        setupUI()
        setSupportActionBar(dataBinding.toolbar)
    }

    private fun setupList() {
        val recyclerView = dataBinding.list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TvShowListAdapter(callback)
        setupPagination()
    }

    private fun openTvShow(tvShow: TvShow) {
        startActivity(DetailsActivity.buildIntent(this, tvShow.id))
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        setupPagination()
    }

    private fun setupPagination() {
        if (compositeDisposable.size() == 0 && dataBinding.list.adapter != null) {
            val disposable = RxRecyclerView.scrollEvents(dataBinding.list)
                    .flatMap {
                        Observable.just((dataBinding.list.layoutManager as LinearLayoutManager)
                                .findLastVisibleItemPosition())
                    }
                    .distinctUntilChanged()
                    .filter {
                        val itemCount = (dataBinding.list.adapter as TvShowListAdapter).itemCount
                        viewModel.shouldFetchNextPage(it, itemCount)
                    }
                    .observeOn(Schedulers.io())
                    .flatMap {
                        viewModel.fetchNextPage()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                        viewModel.setTryAgain(it)
                    })

            compositeDisposable.add(disposable)
        }
    }

    private fun setupUI() {
        viewModel.tvShowResponse.observe(this, Observer {
            addResultsToTheList(it)
        })
        observeScreenStatus()
        setupList()
    }

    private fun addResultsToTheList(tvShowResponse: TvShowResponse?) {
        tvShowResponse?.let {
            (dataBinding.list.adapter as TvShowListAdapter).addResults(it.results)
        }
    }

}
