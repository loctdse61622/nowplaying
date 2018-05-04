package tiki.com.nowplaying.main

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.yheriatovych.reductor.Action
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.Store
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tiki.com.nowplaying.api.MovieService
import tiki.com.nowplaying.dto.ListDto
import tiki.com.nowplaying.model.Movie
import tiki.com.nowplaying.pageloader.ApiPageLoader

/**
 * Created by Admin on 5/4/2018.
 */
class MainPresenter(val movieService: MovieService, val pageLoader: ApiPageLoader<ListDto<Movie>>) :
        MviBasePresenter<MainView, MainViewState>() {

    private val store = Store.create(MainViewStateReducer.create())
    private val actions = Actions.from(MainViewStateReducerActions::class.java)

    override fun bindIntents() {
        val loadFirstPage : Observable<Action> = intent { it.loadFirstPageIntent() }
                .switchMap {
                    pageLoader.loadFirst()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { items -> actions.loadFirst(items, pageLoader.hasNextPage()) }
                            .startWith(actions.loadItem(true, true))
                }

        val loadNextPage : Observable<Action> = intent { it.loadNextPageIntent() }
                .switchMap {
                    pageLoader.loadNextPage()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { items -> actions.loadNext(items, pageLoader.hasNextPage()) }
                            .startWith(actions.loadItem(false, true))
                }

        val refresh : Observable<Action> = intent { it.pullToRefreshIntent() }
                .flatMap {
                    pageLoader.loadFirst()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { items -> actions.loadFirst(items, pageLoader.hasNextPage()) }
                            .startWith(actions.loadItem(true, true))
                }

        val finalObservable : Observable<MainViewState> = Observable.merge(loadFirstPage, loadNextPage, refresh)
                .map { action ->
                    store.dispatch(action)
                    store.state
                }

        subscribeViewState(finalObservable) { obj, state -> obj.render(state) }
    }
}