package tiki.com.nowplaying.main

import com.yheriatovych.reductor.Reducer
import com.yheriatovych.reductor.annotations.AutoReducer
import java.util.*

/**
 * Created by Admin on 5/4/2018.
 */
@AutoReducer
abstract class MainViewStateReducer : Reducer<MainViewState> {

    @AutoReducer.InitialState
    fun defaultState(): MainViewState = MainViewState()

    @AutoReducer.Action(value = "LOAD_FIRST", generateActionCreator = true)
    fun loadFirst(state: MainViewState, list: List<Any>, hasNextPage: Boolean) =
            state.copy(data = list, hasNextPage = hasNextPage, isLoading = false, isLoadingFirstPage = false,
                    isRefresh = true, throwable = null)

    @AutoReducer.Action(value = "LOAD_ITEM", generateActionCreator = true)
    fun loadItem(state: MainViewState, isFirstPage: Boolean, isLoading: Boolean): MainViewState =
            state.copy(isFirstPage = isFirstPage, isLoadingFirstPage = if (isFirstPage) isLoading else false,
                    isLoadingNextPage = if (!isFirstPage) false else isLoading)

    @AutoReducer.Action(value = "LOAD_NEXT", generateActionCreator = true)
    fun loadNext(state: MainViewState, list: List<Any>, hasNextPage: Boolean): MainViewState {
        val newList = mutableListOf<Any>()
        newList.addAll(state.data)
        newList.addAll(list)

        return state.copy(data = Collections.unmodifiableList(newList), hasNextPage = hasNextPage, isLoading = false,
                isLoadingNextPage = false, isRefresh = false, throwable = null)
    }

    @AutoReducer.Action(value = "LOAD_ERROR", generateActionCreator = true)
    fun error(state: MainViewState, throwable: Throwable): MainViewState =
            state.copy(throwable = throwable)

    companion object {
        fun create(): MainViewStateReducer = MainViewStateReducerImpl()
    }
}