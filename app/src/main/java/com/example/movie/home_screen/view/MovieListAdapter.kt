package com.example.movie.home_screen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.BuildConfig
import com.example.movie.R
import com.example.movie.Results
import com.example.movie.databinding.MovieListItemViewBinding

class MovieListAdapter(private val movieList: ArrayList<Results>) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    class MovieViewHolder(var movieView: MovieListItemViewBinding) :
        RecyclerView.ViewHolder(movieView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.movie_list_item_view, parent, false
        )
    )

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieView.movieModel = movieList[position]
        holder.movieView.imagePath = BuildConfig.IMAGE_PATH + movieList[position].posterPath

//        holder.movieView.movieDesc.text = movieList[position].originalTitle
    }

    fun updateList(tempList: ArrayList<Results>) {
        movieList.addAll(tempList)
        tempList.clear()
        notifyDataSetChanged()
    }

}