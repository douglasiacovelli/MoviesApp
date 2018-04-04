package com.iacovelli.moviesapp.tvshowlist

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.databinding.ActivityTvShowListBinding
import com.iacovelli.moviesapp.details.DetailsActivity
import com.iacovelli.moviesapp.models.TvShow
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_tv_show_list.*

class TvShowContractListActivity : BaseActivity(), TvShowListContract {
    lateinit var dataBinding: ActivityTvShowListBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_list)
        presenter = TvShowListPresenter(this)

        dataBinding.presenter = presenter as TvShowListPresenter
        setSupportActionBar(toolbar)
    }

    override fun setupList(tvShowList: ArrayList<TvShow>) {
        val recyclerView = dataBinding.list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TvShowListAdapter(this, tvShowList)
        setupPagination()
    }

    override fun openTvShow(id: Int) {
        startActivity(DetailsActivity.buildIntent(this, id))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
                        (presenter as? TvShowListPresenter)?.shouldFetchNextPage(it, itemCount) == true
                    }
                    .observeOn(Schedulers.io())
                    .flatMap {
                        (presenter as TvShowListPresenter).fetchNextPage()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        (dataBinding.list.adapter as TvShowListAdapter).addNextResults(it)
                    }, {
                        Log.e("debug", "error while fetching next nextPage")
                    })

            compositeDisposable.add(disposable)
        }
    }

}
