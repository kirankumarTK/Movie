package com.example.movie.home_screen.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.BuildConfig
import com.example.movie.Results
import com.example.movie.dao.AppDatabase
import com.example.movie.services.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(context: Application) : AndroidViewModel(context) {

    private var db: AppDatabase
    val movieList = MutableLiveData<ArrayList<Results>>()
    private var page = 0

    init {
        db = AppDatabase.getInstance(context)
        fetchMovieList()
    }

    private fun fetchMovieList() {
        viewModelScope.launch {
            movieList.value = db.resultDao().getMovieList() as ArrayList<Results>
            page = db.movieDao().getPage()
        }
    }

    fun fetchMovieListFromAPI() {
        viewModelScope.launch {
            val response = APIService.callAPI().getPopularMovies(BuildConfig.API_KEY, "en-US", ++page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    db.movieDao().insertMovies(response.body()!!)
                    db.resultDao().insertResult(response.body()!!.results)
                    movieList.value = response.body()!!.results as ArrayList<Results>
                }
            }

        }
    }
}