package com.example.movie.home_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.Results
import com.example.movie.databinding.MovieListFragmentBinding
import com.example.movie.home_screen.viewModel.MovieViewModel

class MovieListFragment : Fragment() {
    private lateinit var dataBinding: MovieListFragmentBinding
    private val movieAdapter: MovieListAdapter = MovieListAdapter(ArrayList<Results>())
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_list_fragment, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        dataBinding.movieListRecyclerview.apply {
            adapter = movieAdapter
        }

        movieViewModel.movieList.observe(viewLifecycleOwner, Observer { movieList ->
            dataBinding.progressDialog.isVisible = false
            movieList.let {
                movieAdapter.updateList(it)
            }

        })

        dataBinding.movieListRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    val layoutManager =
                        dataBinding.movieListRecyclerview.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    if (visibleItemCount == layoutManager.itemCount && movieViewModel.total == layoutManager.itemCount) {
                        dataBinding.progressDialog.isVisible = true

                       /* movieViewModel.low = movieViewModel.high
                        movieViewModel.high += movieViewModel.high
                        movieViewModel.fetchMovieList(movieViewModel.low, movieViewModel.high)*/

                        movieViewModel.fetchMovieListFromAPI()
                    }


                }
            }
        })

    }
}