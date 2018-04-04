package com.iacovelli.moviesapp.tvshowlist

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.databinding.ActivityTvShowListBinding
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

}
