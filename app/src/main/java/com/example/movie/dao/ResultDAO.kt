package com.example.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.Results

@Dao
interface ResultDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResult(result: List<Results>)

    @Query("Select * from Results where rowId BETWEEN (:low) AND (:high) order by originalTitle  ASC")
    suspend fun getMovieList(low: Int, high: Int): List<Results>

    @Query("SELECT (SELECT COUNT(*) FROM Results) == 0")
    suspend fun hasMovies(): Boolean

    @Query("SELECT COUNT(*) FROM Results")
    suspend fun getTotalCount(): Int

}