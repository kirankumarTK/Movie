package com.example.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.MovieModel

@Dao
interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movieModel: MovieModel)

    @Query("Select * from MovieModel")
    suspend fun getMovieList(): List<MovieModel>

}
