package tiki.com.nowplaying.main

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 * Created by Admin on 5/4/2018.
 */
interface MainView: MvpView {
    fun render(state: MainViewState)
    fun loadFirstPageIntent(): Observable<Boolean>
    fun loadNextPageIntent(): Observable<Boolean>
    fun pullToRefreshIntent(): Observable<Boolean>
}