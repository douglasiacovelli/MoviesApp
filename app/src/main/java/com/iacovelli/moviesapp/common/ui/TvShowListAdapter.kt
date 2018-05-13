package com.iacovelli.moviesapp.common.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iacovelli.moviesapp.BR
import com.iacovelli.moviesapp.R
import com.iacovelli.moviesapp.models.TvShow

class TvShowListAdapter(
        private val callback: (tvShow: TvShow) -> Unit,
        private val data: ArrayList<TvShow> = arrayListOf(),
        private val view: Int = R.layout.item_tv_show
): RecyclerView.Adapter<TvShowListAdapter.TvShowViewHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, view, parent, false)
        return TvShowViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = data[position]
        val presenter = ItemTvShowPresenter(tvShow, callback)
        holder.dataBinding.setVariable(BR.presenter, presenter)
    }

    fun addResults(tvShowResults: List<TvShow>) {
        val lastPosition = data.size
        data.addAll(tvShowResults)
        notifyItemRangeInserted(lastPosition, tvShowResults.size)
    }

    class TvShowViewHolder(val dataBinding: ViewDataBinding): RecyclerView.ViewHolder(dataBinding.root)
}