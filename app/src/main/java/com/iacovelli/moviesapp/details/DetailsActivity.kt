package com.iacovelli.moviesapp.details

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.BaseActivity
import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.common.ui.TvShowListAdapter
import com.iacovelli.moviesapp.databinding.ActivityDetailsBinding
import com.iacovelli.moviesapp.models.TvShow

class DetailsActivity: BaseActivity(), DetailsContract, OpenTvShowContract {
    lateinit var dataBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val tvShowId = intent.getIntExtra(TV_SHOW_ID, 0)
        presenter = DetailsPresenter(this, tvShowId)

        dataBinding.presenter = presenter as DetailsPresenter
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