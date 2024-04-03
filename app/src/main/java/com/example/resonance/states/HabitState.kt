package com.example.resonance.states

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.resonance.Habit
import com.example.resonance.MyDate
import com.example.resonance.Tag

data class HabitState(
    val habits: SnapshotStateList<Habit> = mutableStateListOf<Habit>(),
    val name: String = "",
    val tag: String = "",
    val freq: Int = 0,
    val daily: Boolean = false,
    val days: List<Int> = listOf(0,0,0,0,0,0,0),
    val tags: List<Tag> = emptyList(),
    val id: Int = 0
)
