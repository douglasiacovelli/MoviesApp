package com.iacovelli.moviesapp.common.ui

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.iacovelli.moviesapp.common.configuration.SharedPreference
import com.iacovelli.moviesapp.models.SimpleConfiguration
import com.squareup.picasso.provider.PicassoProvider

class PicassoBinder {

    companion object {
        @BindingAdapter("backdropImage")
        @JvmStatic fun setBackdropImage(imageView: ImageView, url: String?) {
            val configuration = getConfiguration(imageView.context)

            val backdropUrl = configuration.baseUrl + configuration.backdropSize + url
            showImageCenterCropped(imageView, backdropUrl)
        }

        @BindingAdapter("posterImage")
        @JvmStatic fun setPosterImage(imageView: ImageView, url: String?) {
            val configuration = getConfiguration(imageView.context)

            val backdropUrl = configuration.baseUrl + configuration.posterSize + url
            showImageCenterCropped(imageView, backdropUrl)
        }

        private fun getConfiguration(context: Context): SimpleConfiguration {
            val cache = SharedPreference(context)
            return GetCachedConfiguration(cache).execute() ?:
            throw IllegalStateException("configuration could not be loaded")
        }

        private fun showImageCenterCropped(imageView: ImageView, url: String) {
            PicassoProvider.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imageView)
        }
    }

}