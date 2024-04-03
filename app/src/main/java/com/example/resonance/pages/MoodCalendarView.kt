package com.example.resonance.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.resonance.components.CustomCalendar
import java.util.Calendar

class MoodCalendarView {

    @Composable
    fun MoodCalendar(
        navController: NavController
    ){

        val cal = Calendar.getInstance()
        Column() {
            CustomCalendar().myCal(month = cal.get(2))

        }

    }

}