package com.sakine.yassirtrendingmovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakine.yassirtrendingmovies.databinding.ListItemTrendingMoviesBinding
import com.sakine.yassirtrendingmovies.models.Result
import com.sakine.yassirtrendingmovies.utils.Constants.Companion.IMAGE_URL

class TrendingMoviesAdapter : RecyclerView.Adapter<TrendingMoviesAdapter.TrendingMoviesViewHolder>() {

    inner class TrendingMoviesViewHolder(private val binding: ListItemTrendingMoviesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result){
            binding.apply {
                Glide.with(this.root).load(IMAGE_URL+result.poster_path).into(posterPath)
                originalTitle.text = result.title
                originalDescription.text = result.overview
                releaseDate.text = result.release_date
                voteAverage.text = result.vote_average.toString()
                itemLayout.setOnClickListener {
                    onItemClickListener?.let { it(result) }
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        return TrendingMoviesViewHolder(
            ListItemTrendingMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {
        val result = differ.currentList[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Result) -> Unit)){
        onItemClickListener = listener
    }
}