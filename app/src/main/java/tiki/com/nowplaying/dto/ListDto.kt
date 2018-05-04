package tiki.com.nowplaying.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Admin on 5/4/2018.
 */
class ListDto<T> {

    @SerializedName(value = "results")
    var items: List<T> = emptyList()

    @SerializedName(value = "total_pages")
    var total_pages: Int = 0
}