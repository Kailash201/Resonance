package com.example.resonance

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlin.collections.ArrayList


class Converters {

    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): ArrayList<Int> {
        val tmp = ArrayList<Int>()
        Gson().fromJson(value, Array<Int>::class.java).toList().forEach {
            tmp.add(it)
        }
        return tmp
    }








}