package com.iacovelli.moviesapp.common

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.iacovelli.moviesapp.common.configuration.GetCachedConfiguration
import com.squareup.picasso.provider.PicassoProvider

class PicassoBinder {

    companion object {

        @BindingAdapter("imageUrl")
        @JvmStatic fun setImageUrl(imageView: ImageView, url: String?) {
            val configuration = GetCachedConfiguration(imageView.context).execute()
            val finalUrl = configuration?.baseUrl + configuration?.imageSize + url
            PicassoProvider.get()
                    .load(finalUrl)
                    .fit()
                    .centerCrop()
                    .into(imageView)

        }
    }

}