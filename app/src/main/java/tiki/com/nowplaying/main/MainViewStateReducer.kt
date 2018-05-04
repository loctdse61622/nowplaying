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
                    isRefresh = true)

    @AutoReducer.Action(value = "LOAD_ITEM", generateActionCreator = true)
    fun loadItem(state: MainViewState, isFirstPage: Boolean, isLoading: Boolean): MainViewState =
            state.copy(isFirstPage = isFirstPage, isLoadingFirstPage = if (isFirstPage) isLoading else false,
                    isLoadingNextPage = if (!isFirstPage) false else isLoading)

    @AutoReducer.Action(value = "LOAD_NEXT", generateActionCreator = true)
    fun loadNext(state: MainViewState, list: List<Any>, hasNextPage: Boolean): MainViewState {
//        val oldList = state.data
//        val newList: List<Any> = ArrayList(oldList.size + list.size)
//        newList.toMutableList().apply {
//            addAll(oldList)
//            addAll(list)
//        }
        val newList = mutableListOf<Any>()
        newList.addAll(state.data)
        newList.addAll(list)

        return state.copy(data = Collections.unmodifiableList(newList), hasNextPage = hasNextPage, isLoading = false,
                isLoadingNextPage = false, isRefresh = false)
    }

    companion object {
        fun create(): MainViewStateReducer = MainViewStateReducerImpl()
    }
}