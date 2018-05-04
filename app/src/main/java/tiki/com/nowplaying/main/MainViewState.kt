package tiki.com.nowplaying.main

/**
 * Created by Admin on 5/4/2018.
 */
data class MainViewState(var data: List<Any> = emptyList(), val throwable: Throwable? = null, val hasNextPage: Boolean? = null,
                         val isLoading: Boolean? = null, val isFirstPage: Boolean? = null, val isLoadingFirstPage: Boolean? = null,
                         val isLoadingNextPage: Boolean? = null, val isRefresh: Boolean? = null)