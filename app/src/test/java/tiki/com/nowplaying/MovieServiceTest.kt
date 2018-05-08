package tiki.com.nowplaying

import org.junit.Test
import tiki.com.nowplaying.base.BaseTest
import tiki.com.nowplaying.dto.ListDto
import tiki.com.nowplaying.model.Movie

/**
 * Created by Admin on 5/7/2018.
 */
class MovieServiceTest : BaseTest() {

    @Test
    fun getNowPlayingFailed() {
        val testNowPlaying = movieService.getNowPlaying(1000)
                .test()
        testNowPlaying.apply {
            assertValue { it.items.isEmpty() }
            assertComplete()
        }
    }

    @Test
    fun getNowPlayingSuccess() {
        val testNowPlaying = movieService.getNowPlaying(1)
                .test()
        testNowPlaying.apply {
            assertValue { it.items.isNotEmpty() }
            assertComplete()
        }
    }

    @Test
    fun getTrailerFailed() {
        val testGetTrailer = movieService.getTrailer("").test()
        testGetTrailer.apply {
            assertValue { it.items.isEmpty() }
            assertComplete()
        }
    }

    @Test
    fun getTrailerSuccess() {
        val testGetTrailer = movieService.getNowPlaying(1)
                .flatMap {
                    listDto: ListDto<Movie> -> movieService.getTrailer(listDto.items[0].id.toString())
                }.test()
        testGetTrailer.apply {
            assertValue { it.items.isNotEmpty() }
            assertComplete()
        }
    }
}