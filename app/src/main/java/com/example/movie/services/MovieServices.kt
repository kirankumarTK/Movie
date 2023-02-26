package com.example.movie.services

import com.example.movie.BuildConfig
import com.example.movie.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieServices {
    @GET(BuildConfig.POPURAL_MOVIES)
    suspend fun getPopularMovies(@Query("api_key") api_key: String, @Query("language") language: String, @Query("page") page: Int) : Response<MovieModel>
}