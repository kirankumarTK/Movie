package com.example.movie.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movie.Convertor
import com.example.movie.MovieModel
import com.example.movie.Results

@Database(entities = [MovieModel::class, Results::class], version = 1, exportSchema = false)
@TypeConverters(Convertor::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, "movieDatabase.db")
                        .allowMainThreadQueries().build()
            }
            return instance as AppDatabase
        }
    }

    abstract fun movieDao(): MovieListDao

    abstract fun resultDao(): ResultDAO
}