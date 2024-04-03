package com.example.resonance.pages

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.MainActivity
import com.example.resonance.Screen
import com.example.resonance.components.CustomCalendar
import com.example.resonance.components.MyCard
import com.example.resonance.states.MainState
import java.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

class ExpandedHabitView {

    @Composable
    fun detailedHabit(
        navController: NavController
    ) {
        val habit = MainState.habit
        MainState.pageNumber.value = 3
        MainActivity.minimize = true

        Column(
            modifier = Modifier
                .background(Color(0xFF0A0C11))
                .fillMaxSize()
        ) {
            val cal = Calendar.getInstance()


            CustomCalendar(habit.calendarCount, 0 ,0).myCal(month = cal.get(2))
            Column(
                modifier = Modifier
//                    .padding(10.dp)
//                    .clip(RoundedCornerShape(20.dp))
//                    .background(Color(0xFF0B0B0B))
                    .fillMaxWidth()
                    ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    MyCard().miniCard(title = "No. of missed days", body = "${habit.missedDays}" )
                    Spacer(modifier = Modifier.width(10.dp))
                    MyCard().miniCard(title = "No. of completed times", body = "${habit.completedCount}" )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row() {
                    MyCard().miniCard(title = "Current Steak", body = "${habit.currentStreak}" )
                    Spacer(modifier = Modifier.width(10.dp))
                    MyCard().miniCard(title = "Longest Steak", body = "${habit.longestStreak}" )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row() {
                    MyCard().miniCard(
                        title = "Times per ${if (habit.daily) "day" else "week"}",
                        body = "${habit.freq}"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

            }
        }
        var backHandlingEnabled by remember { mutableStateOf(true) }
        BackHandler(backHandlingEnabled) {
            MainActivity.minimize = false
            navController.popBackStack()
        }


    }



}