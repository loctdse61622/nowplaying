package tiki.com.nowplaying.main

import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import tiki.com.nowplaying.api.MovieService
import tiki.com.nowplaying.dto.ListDto
import tiki.com.nowplaying.model.Movie
import tiki.com.nowplaying.pageloader.ApiPageLoader

/**
 * Created by Admin on 5/4/2018.
 */
@Module
class MainActivityModule {

    @Provides
    fun providePageLoader(movieService: MovieService): ApiPageLoader<ListDto<Movie>> {
        return object : ApiPageLoader<ListDto<Movie>>() {
            override fun loadApiPage(page: Int): Observable<ListDto<Movie>> {
                return movieService.getNowPlaying(page)
            }
        }
    }

    @Provides
    fun providePresenter(movieService: MovieService, pageLoader: ApiPageLoader<ListDto<Movie>>) : MainPresenter =
            MainPresenter(movieService, pageLoader)

    @Provides
    fun provideAdapter() : MainAdapter = MainAdapter()
}