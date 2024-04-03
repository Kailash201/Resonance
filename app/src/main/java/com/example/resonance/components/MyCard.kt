package com.example.resonance.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resonance.DayHabitEvent
import com.example.resonance.Habit
import com.example.resonance.HabitEvent
import com.example.resonance.MainActivity
import com.example.resonance.R
import com.example.resonance.Screen
import com.example.resonance.states.MainState
import com.example.resonance.states.TypeHabitState
import kotlinx.coroutines.delay
import java.util.Locale

class MyCard {

    @Composable
    fun miniCard (title: String, body: String) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(150.dp)
                .height(80.dp)
                .background(Color.Black)

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = title, color = Color.White)
                Text(text = body, color = Color.White, fontSize = 20.sp)

            }
        }


    }

    @Composable
    fun lastFiveDays(record: List<Int>){
        val size: Int = 15
        Row(
            modifier = Modifier
                .width(140.dp)
                .height(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            record.forEach {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(size.dp)
                        .background(
                            if (it == -1)
                                Color.DarkGray
                            else if (it == 1)
                                Color.Green
                            else Color.Red
                        )
                )
            }
        }
    }


    @Composable
    fun bigCard(
        habit: Habit,
        onEvent: (HabitEvent) -> Unit,
        navController: NavController,
    ) {
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                containerColor = Color((0xFFB82525)),

                title = {
                    Text(text = "Remove Habit", color = Color.White)
                },
                text = {
                    Text(text = "Are you sure you want to remove ${habit.name} habit?",
                        color = Color.White)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onEvent(HabitEvent.deleteHabit(habit))
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color((0xFF3E0000))
                        )
                    ) {
                        Text(text = "Yes")
                    }

                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color((0xFFB82525))
                        )
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
        val tmp = IntArray(habit.calendarCount.size)
        habit.calendarCount.forEachIndexed { index, i ->
            tmp[index] = i
        }

        Surface(
            modifier = Modifier
                .height(110.dp)
                .width(300.dp)
                .clickable(onClick = {
                    MainState.habit = habit
                    navController.navigate(Screen.ExpandedHabitView.route)

                }),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF740099)
        ) {
            Column(
                modifier = Modifier.width(300.dp),
                horizontalAlignment = Alignment.End) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_close_24),
                    contentDescription = "remove habit",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(onClick = {
                            // onEvent(HabitEvent.deleteHabit(habit))
                            openDialog.value = true
                        })
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = -20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .width(175.dp)
                            .height(150.dp)
                            .padding(start = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = habit.name.capitalize(Locale.ENGLISH),
                            color = Color.White, fontSize = 18.sp)
                        Text(text = habit.tag.capitalize(Locale.ENGLISH),
                            color = Color(0xFFECB2FF), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = if (habit.daily) "Daily" else "${habit.freq} Times per week",
                            color = Color.White,
                            fontSize = 14.sp)
                        Text(text = "Missed count: ${habit.missedDays} days", color = Color.White,
                            fontSize = 14.sp)
//                        Text(text = "Longest Streak: ${habit.longestStreak} days", color = Color.White)
//                        Text(text = "Current Streak: ${habit.currentStreak} days", color = Color.White)
                    }
                    Column(
                        modifier = Modifier
                            .width(175.dp)
                            .height(150.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        lastFiveDays(habit.fiveDayStreak)
                    }

                }


            }

        }
    }

    @Composable
    fun card(
        habit: Habit,
        onEvent: (DayHabitEvent) -> Unit,
        state: TypeHabitState,
    ) {

        val cT = remember() { mutableStateOf(habit.currentTimes) }
        Log.d("Secondary id", "${cT}")

        //changes cT when diff habits are given
        LaunchedEffect(habit) {
            cT.value = habit.currentTimes
        }



        Surface(
            modifier = Modifier
                .height(85.dp)
                .width(300.dp)
                .clickable(onClick = {
                    if (state.clickable.value && cT.value < habit.freq) {
                        cT.value = cT.value + 1
                        onEvent(DayHabitEvent.AddOne(habit))
                    }
                }),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFF740099)
        ) {
            // text and minimize
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                ) {
                    Text(text = habit.name.capitalize(Locale.ENGLISH), color = Color.White)
                    Text(text = habit.tag.capitalize(Locale.ENGLISH),
                        color = Color(0xFFECB2FF), fontSize = 12.sp)
                }
                Icon(
                    painter = painterResource(id = R.drawable.baseline_minimize_24),
                    contentDescription = "reduce count",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = {
                            if (state.clickable.value && cT.value > 0) {
                                cT.value = cT.value - 1
                                onEvent(DayHabitEvent.MinusOne(habit))
                            }
                        })
                        .offset(y = -14.dp)
                )
            }
            val animate by animateFloatAsState(
                targetValue = cT.value/habit.freq.toFloat(),
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )

            // progress bar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (0).dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                LinearProgressIndicator(
                    progress = animate,
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(),
                    color = Color(0xFF2F013E),
                    trackColor = Color(0xFFE18BFD),
                )
                Text(
                    text = "${cT.value}/${habit.freq} per ${if (habit.daily) "day" else "week" }",
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .offset(y = (-15).dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

        }





    }


}