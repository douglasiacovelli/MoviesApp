package com.iacovelli.moviesapp.tvshowlist

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.databinding.ActivityTvShowListBinding
import com.iacovelli.moviesapp.models.TvShow
import kotlinx.android.synthetic.main.activity_tv_show_list.*

class TvShowListActivity : BaseActivity(), TvShowListContract {
    lateinit var dataBinding: ActivityTvShowListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_list)
        presenter = TvShowListPresenter(this)

        dataBinding.presenter = presenter as TvShowListPresenter
        setSupportActionBar(toolbar)
    }

    override fun setupList(tvShowList: List<TvShow>) {
        val recyclerView = dataBinding.list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TvShowListAdapter(this, tvShowList)
    }

    override fun openTvShowDetail(tvShow: TvShow) {
        //TODO: this
    }

}
