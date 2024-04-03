package com.example.resonance.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.HabitEvent
import com.example.resonance.MainActivity
import com.example.resonance.states.HabitState
import com.example.resonance.R
import com.example.resonance.Screen
import com.example.resonance.components.BottomNav
import com.example.resonance.components.MyCard
import com.example.resonance.states.MainState

class OverallHabitView {

    @Composable
    fun mainHabitView(
        navController: NavController,
        state: HabitState,
        onEvent: (HabitEvent) -> Unit
    ) {
        MainState.pageNumber.value = 1
        MainActivity.minimize = false


        //get all the habits and update it in state
        onEvent(HabitEvent.SetAllHabits)
        val habits = state.habits.filter { (it.master) }
        val card = MyCard()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0A0C11)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                //contentPadding = PaddingValues(20.dp)
            ) {
                items(habits) { habit ->
                    card.bigCard(habit = habit, onEvent = onEvent, navController)
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = -80.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddHabitScreen.route) },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "Add Habit"
                    )
                }
                // bottomNav.botNav(navController = navController, 1)
            }

        }
    }
}