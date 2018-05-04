package tiki.com.nowplaying.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Admin on 5/4/2018.
 */
data class Movie(val id: Int? = 0,
                 val title: String? = "",
                 val overview: String? = "",
                 @SerializedName("poster_path")
                 private val poster: String? = "",
                 @SerializedName("release_date")
                 val releaseDate: String? = "",
                 @SerializedName("vote_average")
                 val rating: Double = 0.0) {

    val getPosterPath: String
        get() = PREFIX + poster

    companion object {
        private val PREFIX = "https://image.tmdb.org/t/p/w500"
    }
}