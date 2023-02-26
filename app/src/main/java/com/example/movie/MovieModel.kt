package com.example.movie

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MovieModel(
    @ColumnInfo @SerializedName("page") @PrimaryKey var page: Int = 0,
    @Ignore @SerializedName("results") var results: List<Results> = arrayListOf(),
    @ColumnInfo @SerializedName("total_pages") var totalPages: Int = 0,
    @ColumnInfo @SerializedName("total_results") var totalResults: Int = 0
) : Parcelable


@Parcelize
@Entity
data class Results(
    @PrimaryKey(autoGenerate = true) var rowId: Int = 0,
    @ColumnInfo @SerializedName("adult") var adult: String = "",
    @ColumnInfo @SerializedName("backdrop_path") var backdropPath: String = "",
    @ColumnInfo @SerializedName("id")  var id: String = "",
    @ColumnInfo @SerializedName("original_language") var originalLanguage: String = "",
    @ColumnInfo @SerializedName("original_title") var originalTitle: String = "",
    @ColumnInfo @SerializedName("overview") var overview: String = "",
    @ColumnInfo @SerializedName("popularity") var popularity: String = "",
    @ColumnInfo @SerializedName("poster_path") var posterPath: String = "",
    @ColumnInfo @SerializedName("release_date") var releaseDate: String = "",
    @ColumnInfo @SerializedName("title") var title: String = "",
    @ColumnInfo @SerializedName("video") var video: String = "",
    @ColumnInfo @SerializedName("vote_average") var voteAverage: String = "",
    @ColumnInfo @SerializedName("vote_count") var voteCount: String = ""
) : Parcelable