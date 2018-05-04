package tiki.com.nowplaying.pageloader

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tiki.com.nowplaying.dto.ListDto

/**
 * Created by Admin on 5/4/2018.
 */
abstract class ApiPageLoader<T: ListDto<*>>: IPageLoader<Any> {

    private var nextPage = 1
    private var hasNextPage = true

    fun loadPage(page: Int): Observable<List<Any?>> {
        return loadApiPage(page).subscribeOn(Schedulers.io())
//                .compose<T>({ RxUtils.assertApiWithListSuccess(it) })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { data ->
                    nextPage = page + 1
                    hasNextPage = nextPage <= data.total_pages
                }
                .map { it.items }
    }

    protected abstract fun loadApiPage(page: Int): Observable<T>

    override fun loadFirst(): Observable<List<Any?>> {
        nextPage = 1
        return loadPage(1)
    }

    override fun loadNextPage(): Observable<List<Any?>> {
        return loadPage(nextPage)
    }

    override fun hasNextPage(): Boolean {
        return hasNextPage
    }
}