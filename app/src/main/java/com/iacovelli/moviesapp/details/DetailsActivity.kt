package com.iacovelli.moviesapp.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.common.ui.TvShowListAdapter
import com.iacovelli.moviesapp.databinding.ActivityDetailsBinding
import com.iacovelli.moviesapp.models.TvShow

class DetailsActivity: BaseActivity(), DetailsContract, OpenTvShowContract {
    lateinit var dataBinding: ActivityDetailsBinding
    lateinit var viewModel: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val tvShowId = intent.getIntExtra(TV_SHOW_ID, 0)
        val factory = DetailsPresenter.Factory(tvShowId)

        viewModel = ViewModelProviders.of(this, factory).get(DetailsPresenter::class.java)
        dataBinding.presenter = viewModel
        dataBinding.setLifecycleOwner(this)

        setupViewModelObservers()
        setupToolbar()
    }

    private fun setupViewModelObservers() {
        viewModel.status.observe(this, Observer {
            Log.d("debug", "teste ${it?.message}")
            it?.message?.let {
                showMessage(it)
            }
        })

        viewModel.tvShow.observe(this, Observer {
            setupList(it?.similar ?: arrayListOf())
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setupList(tvShowList: ArrayList<TvShow>) {
        val recyclerView = dataBinding.similarList
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = TvShowListAdapter(this, tvShowList, R.layout.item_similar_tv_show)
    }

    override fun openTvShow(id: Int) {
        startActivity(DetailsActivity.buildIntent(this, id))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return false
        }
        return super.onOptionsItemSelected(item)
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