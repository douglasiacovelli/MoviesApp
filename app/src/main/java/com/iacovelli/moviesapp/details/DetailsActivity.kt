package com.iacovelli.moviesapp.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.common.ui.TvShowListAdapter
import com.iacovelli.moviesapp.databinding.ActivityDetailsBinding
import com.iacovelli.moviesapp.models.TvShow

class DetailsActivity: BaseActivity<DetailsViewModel>() {
    lateinit var dataBinding: ActivityDetailsBinding
    private val callback = { tvShow: TvShow ->
        openTvShow(tvShow)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val tvShowId = intent.getIntExtra(TV_SHOW_ID, 0)
        val factory = DetailsViewModel.Factory(tvShowId)

        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        dataBinding.presenter = viewModel
        dataBinding.setLifecycleOwner(this)

        setupUI()
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        viewModel.tvShow.observe(this, Observer {
            updateList(it?.similar ?: arrayListOf())
        })
        observeScreenStatus()
        setupSimilarList()
    }

    private fun updateList(arrayList: ArrayList<TvShow>) {
        (dataBinding.similarList.adapter as TvShowListAdapter).addResults(arrayList)
    }

    private fun setupToolbar() {
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupSimilarList() {
        val recyclerView = dataBinding.similarList
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = TvShowListAdapter(callback, view = R.layout.item_similar_tv_show)
    }

    private fun openTvShow(tvShow: TvShow) {
        startActivity(DetailsActivity.buildIntent(this, tvShow.id))
    }

    companion object {
        private const val TV_SHOW_ID = "com.iacovelli.moviesapp.TV_SHOW_ID"
        @JvmStatic fun buildIntent(context: Context, tvShowId: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(TV_SHOW_ID, tvShowId)
            return intent
        }
    }
}