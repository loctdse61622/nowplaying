package tiki.com.nowplaying.model

import android.os.Parcel
import android.os.Parcelable
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
                 val rating: Double = 0.0) : Parcelable {

    val getPosterPath: String
        get() = PREFIX + poster

    val getReleaseDate: String
        get() = "Release Date: " + releaseDate

    val getRating: String
        get() = rating.toString() + "/10"

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(title)
        writeString(overview)
        writeString(poster)
        writeString(releaseDate)
        writeDouble(rating)
    }

    companion object {
        private val PREFIX = "https://image.tmdb.org/t/p/w500"

        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}