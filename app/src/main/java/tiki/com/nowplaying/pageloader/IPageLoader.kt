package tiki.com.nowplaying.pageloader

import io.reactivex.Observable
import java.util.*

/**
 * Created by Admin on 5/4/2018.
 */
interface IPageLoader<O : Any> {

    fun loadFirst(): Observable<List<O?>>?

    fun loadNextPage(): Observable<List<O?>>?

    fun hasNextPage(): Boolean
}