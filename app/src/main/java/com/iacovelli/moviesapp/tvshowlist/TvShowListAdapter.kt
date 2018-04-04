package com.iacovelli.moviesapp.tvshowlist

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iacovelli.moviesapp.BR
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.common.OpenTvShowContract
import com.iacovelli.moviesapp.models.TvShow
import com.iacovelli.moviesapp.models.TvShowResponse
import com.iacovelli.moviesapp.tvshowlist.TvShowListAdapter.TvShowViewHolder

class TvShowListAdapter(
        private val contract: OpenTvShowContract,
        private val data: ArrayList<TvShow>,
        private val view: Int = R.layout.item_tv_show
): RecyclerView.Adapter<TvShowViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, view, parent, false)
        return TvShowViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = data[position]
        val presenter = ItemTvShowPresenter(contract, tvShow)
        holder.dataBinding.setVariable(BR.presenter, presenter)
    }

    override fun onViewRecycled(holder: TvShowViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    fun addNextResults(tvShowResponse: TvShowResponse) {
        val lastPosition = data.size
        data.addAll(tvShowResponse.results)
        notifyItemRangeInserted(lastPosition, tvShowResponse.results.size)
    }

    class TvShowViewHolder(val dataBinding: ViewDataBinding):
            RecyclerView.ViewHolder(dataBinding.root) {

        var presenter: ItemTvShowPresenter? = null

        fun clear() {
            presenter?.onDestroy()
            presenter = null
        }
    }
}