package com.example.resonance

sealed class Screen(val route: String) {
    object DayScreen: Screen ("DayScreen")
    object WeekScreen: Screen ("WeekScreen")
    object HabitScreen: Screen ("HabitScreen")
    object ProfileScreen: Screen ("ProfileScreen")
    object AddHabitScreen: Screen ("AddHabitScreen")
    object ExpandedHabitView: Screen ("ExpandedHabitView")
    object MoodCalendarView: Screen ("MoodCalendarView")



}