package com.example.movie

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class Convertor {
    @TypeConverter
    fun fromString(value: String): ArrayList<Results> {
        val listType = object : TypeToken<ArrayList<Results>>() {}.type
        return Gson().fromJson<ArrayList<Results>>(value, listType).toList() as ArrayList<Results>
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Results>): String {
        val type = object : TypeToken<ArrayList<Results>>() {}.type
        return Gson().toJson(list, type)
    }
}