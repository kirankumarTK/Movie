package com.example.movie.home_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

        movieViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            dataBinding.progressDialog.isVisible = false
            movieList.let {
                movieAdapter.updateList(it)
                dataBinding.progressDialog.visibility = View.GONE
            }

        }


        dataBinding.movieListRecyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager?
                val movieListSize = movieAdapter.itemCount
                if (dataBinding.progressDialog.visibility == View.GONE) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == movieListSize - 1) {
                        dataBinding.progressDialog.visibility = View.VISIBLE
                        movieViewModel.fetchMovieListFromAPI()
                    }
                }
            }


        })
    }
}