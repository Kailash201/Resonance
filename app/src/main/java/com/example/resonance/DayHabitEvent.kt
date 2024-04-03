package com.example.resonance

sealed interface DayHabitEvent {
    data class AddOne(val habit: Habit): DayHabitEvent
    data class MinusOne(val habit: Habit): DayHabitEvent
   // data class SetCurtime(val habit: Habit): DayHabitEvent
    data class GetHabits(val day: Int, val month: Int, val year: Int): DayHabitEvent
   // data class GetHabit(val Habit: Habit): DayHabitEvent
    data class setEndDate(val myDate: MyDate): DayHabitEvent
    data class UpdateHabitsAfterWeeks(val habits: List<Habit>): DayHabitEvent
    data class DayHasEnded(val habits: List<Habit>): DayHabitEvent
}