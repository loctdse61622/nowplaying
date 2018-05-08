package tiki.com.nowplaying.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import tiki.com.nowplaying.R
import tiki.com.nowplaying.detail.MovieDetailActivity
import tiki.com.nowplaying.model.Movie
import tiki.com.nowplaying.utils.getLoading
import tiki.com.nowplaying.utils.hideLoading
import java.security.AccessController.getContext
import javax.inject.Inject

/**
 * Created by Admin on 5/3/2018.
 */
class MainActivity : MviActivity<MainView, MainPresenter>(), MainView, MainAdapter.OnItemClickListener {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var adapter: MainAdapter
    private var layoutManager: LinearLayoutManager? = null
    private var isFirstCreated: Boolean? = true
    private val loadFirstPageOb: PublishSubject<Boolean> = PublishSubject.create()
    private val loadingView: KProgressHUD by lazy { getLoading(this) }
    private var hasShown: Boolean? = false

    override fun createPresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        initView()
        adapter?.setOnMovieClickListener(this)
        if (isFirstCreated == true) {
            recycler_view?.rootView?.post {
                loadFirstPageOb.onNext(true)
            }
            isFirstCreated = false
        }
    }

    private fun initView() {
        if (recycler_view == null) {
            throw IllegalArgumentException("Recycler view must not be null")
        }

        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(applicationContext)
            recycler_view?.layoutManager = this.layoutManager
        }

        recycler_view?.adapter = this.adapter
        (recycler_view?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun render(state: MainViewState) {
        hideLoading(loadingView)
        adapter.setLoading(state.isLoading)
        adapter.setNextPage(state.hasNextPage)

        if (state.isLoading == true) {
            if (state.isLoadingFirstPage == true) {
                setRefreshing(true)
            }
        } else {
            setRefreshing(false)
            if (state.data.isNotEmpty()) {
                adapter.setData(state.data as List<Movie>, false)
            }
        }

        if (state.throwable != null && hasShown == false) {
            Toast.makeText(applicationContext, "Error, please check your Internet connection and try again", Toast.LENGTH_SHORT).show()
            hasShown = true
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(applicationContext, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie)
        startActivity(intent)
    }

    private fun setRefreshing(isRefresh: Boolean?) {
        refresh_layout?.post { refresh_layout?.isRefreshing = isRefresh!! }
    }

    override fun loadFirstPageIntent(): Observable<Boolean> = loadFirstPageOb.doOnNext { loadingView.show() }

    override fun loadNextPageIntent(): Observable<Boolean> =
            RxRecyclerView.scrollEvents(recycler_view)
                    .filter { adapter.isLoading() == false && adapter.hasNextPage() == true}
                    .filter { (recycler_view?.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() >= adapter.itemCount - 1 }
                    .map { true }
                    .doOnNext { loadingView.show() }

    override fun pullToRefreshIntent(): Observable<Boolean> = RxSwipeRefreshLayout.refreshes(refresh_layout)
            .map { true }
            .doOnNext { loadingView.show() }
            .doOnNext { hasShown = false }
}