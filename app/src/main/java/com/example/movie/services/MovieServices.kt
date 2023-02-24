package com.example.movie.services

import com.example.movie.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieServices {
    @GET
    suspend fun getPopularMovies(@Url url: String) : Response<MovieModel>
}