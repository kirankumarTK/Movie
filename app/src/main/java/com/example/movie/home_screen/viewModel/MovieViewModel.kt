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
    var page = 0
    var total = 0
    var low = 0
    var high = 20

    init {
        db = AppDatabase.getInstance(context)
        fetchMovieList(low, high)
    }

    fun fetchMovieList(low: Int, high: Int) {
        viewModelScope.launch {
            movieList.value = db.resultDao().getMovieList(low, high) as ArrayList<Results>
            total = db.resultDao().getTotalCount()
            page = db.movieDao().getPage()
        }
    }

    fun fetchMovieListFromAPI() {
        viewModelScope.launch {
            val response =
                APIService.callAPI().getPopularMovies(BuildConfig.API_KEY, "en-US", ++page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    db.movieDao().insertMovies(response.body()!!)
                    db.resultDao().insertResult(response.body()!!.results)
                    fetchMovieList(low, high)
                }
            }

        }
    }
}