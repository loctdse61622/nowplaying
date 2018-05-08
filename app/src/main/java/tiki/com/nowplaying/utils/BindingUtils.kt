package tiki.com.nowplaying.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatRatingBar
import com.squareup.picasso.Picasso
import tiki.com.nowplaying.R

/**
 * Created by Admin on 5/4/2018.
 */
class BindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("imgUrl")
        fun loadImgUrl(imageView: AppCompatImageView?, url: String?) {
            Picasso.with(imageView?.context).cancelRequest(imageView)
            if (!url.isNullOrEmpty())
                Picasso.with(imageView?.context).load(url).placeholder(R.mipmap.img_not_found).into(imageView)
        }

        @JvmStatic
        @BindingAdapter("setStars")
        fun setStars(ratingBar: AppCompatRatingBar?, value: Double?) {
            ratingBar?.rating = value?.toFloat() ?: 0f
        }
    }
}