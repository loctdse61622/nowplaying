package tiki.com.nowplaying.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.AppCompatImageView
import com.squareup.picasso.Picasso

/**
 * Created by Admin on 5/4/2018.
 */
class BindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("imgUrl")
        fun loadImgUrl(imageView: AppCompatImageView?, url: String?) {
            if (!url.isNullOrEmpty())
                Picasso.with(imageView?.context).load(url).into(imageView)
        }
    }
}