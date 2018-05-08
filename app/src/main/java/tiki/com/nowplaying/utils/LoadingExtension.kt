package tiki.com.nowplaying.utils

import android.app.Activity
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * Created by Admin on 5/7/2018.
 */
fun Activity.getLoading(activity: Activity): KProgressHUD {
    return KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
}

fun Activity.hideLoading(loadingView: KProgressHUD?) {
    try {
        loadingView?.dismiss()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}