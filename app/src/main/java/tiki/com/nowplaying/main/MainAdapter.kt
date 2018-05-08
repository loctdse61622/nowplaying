package tiki.com.nowplaying.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tiki.com.nowplaying.R
import tiki.com.nowplaying.databinding.ItemMovieBinding
import tiki.com.nowplaying.model.Movie

/**
 * Created by Admin on 5/4/2018.
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var movie: List<Movie> = emptyList()
    private var isLoading: Boolean? = false
    private var hasNextPage: Boolean? = false
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onMovieClick(movie: Movie)
    }

    fun setOnMovieClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setData(data: List<Movie>, isRefresh: Boolean?) {
        this.movie = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemMovieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_movie, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movie.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(movie[position])
        holder?.itemView?.setOnClickListener { listener?.onMovieClick(movie = movie[position]) }
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) {
            binding.item = data
            binding.executePendingBindings()
        }
    }

    fun isLoading(): Boolean? = isLoading

    fun setLoading(loading: Boolean?) {
        this.isLoading = loading
    }

    fun setNextPage(hasNextPage: Boolean?) {
        this.hasNextPage = hasNextPage
    }

    fun hasNextPage(): Boolean? = hasNextPage
}