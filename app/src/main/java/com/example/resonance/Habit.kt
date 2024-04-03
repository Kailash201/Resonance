package com.example.resonance

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity
data class Habit(
    val name: String,
    val tag: String,
    val freq: Int,
    val daily: Boolean,
    val missedDays: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val currentTimes: Int = 0,
    val completedCount: Int = 0,
    val calendarCount: ArrayList<Int> = ArrayList<Int>(),
    val fiveDayStreak: List<Int> = listOf(-1,-1,-1,-1,-1),
    val dates: List<Int> = emptyList(), //all the dates the habit need to have
    val startDate: List<Int> = emptyList(),
    var displayDate: Int = 0, //the index for the dates
    val updateCount: Int = 0, //mark when it is done
    val secId: Int = 0, //id for each unique habit
    val done: Boolean = false, // tag to know which habit to update after every 2 wk
    var master: Boolean = true, // the main habit card
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    )
