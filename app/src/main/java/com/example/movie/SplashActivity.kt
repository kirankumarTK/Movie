package com.example.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.movie.dao.AppDatabase
import com.example.movie.databinding.SplashBinding
import com.example.movie.home_screen.view.HomeActivity
import com.example.movie.services.APIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash)

        db = AppDatabase.getInstance(this)

        CoroutineScope(Dispatchers.IO).launch {
            if (db.resultDao().hasMovies()) {
                val response = APIService.callAPI()
                    .getPopularMovies( BuildConfig.API_KEY, "en-US",1)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        db.movieDao().insertMovies(response.body()!!)
                        db.resultDao().insertResult(response.body()!!.results)
                        gotoHome()
                    }
                }

            } else {
                gotoHome()
            }
        }
    }

    private fun gotoHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}

