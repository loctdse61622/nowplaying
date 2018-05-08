package tiki.com.nowplaying.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tiki.com.nowplaying.dto.ListDto
import tiki.com.nowplaying.model.Movie
import tiki.com.nowplaying.model.Trailer

/**
 * Created by Admin on 5/4/2018.
 */
interface MovieService {

    @GET("now_playing")
    fun getNowPlaying(@Query("page") page: Int) : Observable<ListDto<Movie>>

    @GET("{id}/videos")
    fun getTrailer(@Path("id") id: String): Observable<ListDto<Trailer>>
}