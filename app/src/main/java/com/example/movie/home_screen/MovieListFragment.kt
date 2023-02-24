package com.example.movie.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.BuildConfig
import com.example.movie.R
import com.example.movie.Results
import com.example.movie.dao.AppDatabase
import com.example.movie.databinding.MovieListFragmentBinding
import com.example.movie.services.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListFragment : Fragment() {
    private lateinit var dataBinding: MovieListFragmentBinding
    private val movieList = arrayListOf<Results>()
    private var page = 1
    private val movieAdapter: MovieListAdapter = MovieListAdapter(movieList, page)
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)

        db = context?.let { AppDatabase.getInstance(it) }!!

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.movieListRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = movieAdapter
        }
        CoroutineScope(Dispatchers.IO).launch {
            movieList.addAll(db.resultDao().getMovieList())
            withContext(Dispatchers.Main) {
                movieAdapter.notifyDataSetChanged()

            }

        }

    }
}