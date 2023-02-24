package com.example.movie

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class Convertor {
    @TypeConverter
    fun fromString(value: String): List<Results> {
        val listType = object : TypeToken<List<Results>>() {}.type
        return Gson().fromJson<List<Results>>(value, listType).toList()
    }

    @TypeConverter
    fun fromArrayList(list: List<Results>): String {
        val type = object : TypeToken<List<Results>>() {}.type
        return Gson().toJson(list, type)
    }
}