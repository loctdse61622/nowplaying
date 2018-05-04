package tiki.com.nowplaying.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import tiki.com.nowplaying.dto.ListDto
import tiki.com.nowplaying.model.Movie

/**
 * Created by Admin on 5/4/2018.
 */
interface MovieService {

    @GET("now_playing")
    fun getNowPlaying(@Query("page") page: Int) : Observable<ListDto<Movie>>

}