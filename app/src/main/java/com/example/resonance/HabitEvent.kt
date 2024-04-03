package com.example.resonance

sealed interface HabitEvent {
    object SaveHabit: HabitEvent
    data class setName(val name: String): HabitEvent
    data class setTag(val Tag: String, val newTag: Boolean = false): HabitEvent
    data class setFreq(val freq: Int): HabitEvent
    data class setDaily(val daily: Boolean): HabitEvent
    data class deleteHabit(val habit: Habit): HabitEvent
    data class setDays(val days: List<Int>): HabitEvent
    object SetAllHabits: HabitEvent
    object GetAllTags: HabitEvent
}