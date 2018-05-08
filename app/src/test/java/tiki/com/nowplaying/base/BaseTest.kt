package tiki.com.nowplaying.base

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import tiki.com.nowplaying.BuildConfig
import tiki.com.nowplaying.api.MovieService
import javax.inject.Inject

/**
 * Created by Admin on 5/7/2018.
 */
@Config(constants = BuildConfig::class, sdk = [23], application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseTest {
    @Inject
    lateinit var movieService: MovieService
}