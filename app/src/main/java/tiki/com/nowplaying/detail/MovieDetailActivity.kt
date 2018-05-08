package tiki.com.nowplaying.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import tiki.com.nowplaying.BuildConfig
import tiki.com.nowplaying.R
import tiki.com.nowplaying.api.MovieService
import tiki.com.nowplaying.databinding.ActivityDetailBinding
import tiki.com.nowplaying.model.Movie
import javax.inject.Inject

/**
 * Created by Admin on 5/7/2018.
 */
class MovieDetailActivity: YouTubeBaseActivity() {

    private val movie by lazy { intent.getParcelableExtra<Movie>(MOVIE) }
    private lateinit var binding: ActivityDetailBinding
    @Inject
    lateinit var movieService: MovieService
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        AndroidInjection.inject(this)

        if (movie != null) {
            binding.detail = movie
            binding.executePendingBindings()
        }

        player?.initialize(BuildConfig.YOUTUBE_KEY, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, p2: Boolean) {
                disposable = movieService.getTrailer(movie.id.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({listDto -> youTubePlayer?.cueVideo(listDto.items[0].key)}, Throwable::printStackTrace)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Toast.makeText(baseContext, "Failed to get video key!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (disposable != null) {
            disposable?.dispose()
        }
        this.finish()
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        val MOVIE = "movie"
    }
}