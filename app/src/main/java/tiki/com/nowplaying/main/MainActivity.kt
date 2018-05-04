package tiki.com.nowplaying.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import tiki.com.nowplaying.R
import tiki.com.nowplaying.model.Movie
import javax.inject.Inject

/**
 * Created by Admin on 5/3/2018.
 */
class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var adapter: MainAdapter
    private var layoutManager: LinearLayoutManager? = null
    private var isFirstCreated: Boolean? = true
    private val loadFirstPageOb: PublishSubject<Boolean> = PublishSubject.create()

    override fun createPresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        initView()
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
    }

    override fun render(state: MainViewState) {
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
    }

    private fun setRefreshing(isRefresh: Boolean?) {
        refresh_layout.post { refresh_layout?.isRefreshing = isRefresh!! }
    }

    override fun loadFirstPageIntent(): Observable<Boolean> = loadFirstPageOb

    override fun loadNextPageIntent(): Observable<Boolean> =
            RxRecyclerView.scrollEvents(recycler_view)
                    .filter { adapter.isLoading() == false && adapter.hasNextPage() == true}
                    .filter { layoutManager?.findLastCompletelyVisibleItemPosition()!! >= adapter.itemCount - 1 }
                    .map { true }

    override fun pullToRefreshIntent(): Observable<Boolean> = RxSwipeRefreshLayout.refreshes(refresh_layout)
            .map { true }
}