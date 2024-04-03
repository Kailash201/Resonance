package com.example.resonance.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.resonance.Habit
import com.example.resonance.MyDate
import java.io.Serializable

data class TypeHabitState(
    val habits: ArrayList<Habit> = ArrayList<Habit>(),
    //val currentTimes: MutableState<Int> = mutableStateOf(0),
    val currentDate: MutableState<MyDate> = mutableStateOf(MyDate(0,0,0,0)),
    val selectedDate: MutableState<MyDate> = mutableStateOf(MyDate(1,1,1,1)),
    val clickable: MutableState<Boolean> = mutableStateOf(false),
    val endDate: MutableState<MyDate> = mutableStateOf(MyDate(0,0,0,0)),
    val restart: MutableState<Boolean> = mutableStateOf(false),

)