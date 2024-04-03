package com.example.resonance.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.DayHabitEvent
import com.example.resonance.MainActivity
import com.example.resonance.MyDate
import com.example.resonance.states.TypeHabitState
import com.example.resonance.components.BottomNav
import com.example.resonance.components.DateUi
import com.example.resonance.components.HomeTopTab
import com.example.resonance.components.MyCard
import com.example.resonance.states.MainState
import java.util.Calendar

class DWHabitViewer{

    companion object tmp {
        val restart: MutableState<Boolean> = mutableStateOf(false) // to allow for daily updates
        //var bootup = true // to show today habits when app starts
        //val screen: MutableState<Boolean> = mutableStateOf(true)
    }


    @Composable
    fun dayScreen(
        navController: NavController,
        onEvent: (DayHabitEvent) -> Unit,
        state: TypeHabitState,
       // currentPos: Int,
       // DoW: Int
    ) {
        // for bot nav
        MainActivity.minimize = false
        MainState.pageNumber.value = 0

        val tab: HomeTopTab = HomeTopTab()
        val dS: DateUi = DateUi()
        val card: MyCard = MyCard()

        // updates card after every n period
        val cal = Calendar.getInstance()
        val re = MyDate(cal.get(5), cal.get(2), cal.get(1), cal.get(7))
        //var re by remember { mutableStateOf(MyDate(cal.get(5), cal.get(2), cal.get(1), cal.get(7))) }

        // make habit for 14 days at the start
        if (state.endDate.value.isZero()) {
            val endDate = re.addDays(14)
            onEvent(DayHabitEvent.setEndDate(endDate))
        }


        //daily updates on the current streak and etc
        if (restart.value) {
            //continue making daily habits after 14 days
            if (state.endDate.value.equals(re)) {
                val endDate = re.addDays(14)
                onEvent(DayHabitEvent.setEndDate(endDate))
                onEvent(DayHabitEvent.UpdateHabitsAfterWeeks(listOf()))
            }

            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH, -1)
            onEvent(DayHabitEvent.GetHabits(cal.get(5), cal.get(2), cal.get(1)))
            onEvent(DayHabitEvent.DayHasEnded(state.habits))
            Log.d("restart", "True")
            restart.value = false

        }
        tab.dayWeekTab(navController, 0)
        // to display today habits when app opens up
        // DateUi.start = bootup

        // so that today habits will not override other day habits when viewed
        // bootup = false

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0A0C11)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize()
            ) {
                tab.dayWeekTab(navController, 0)
                if (!MainState.hh.value) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(onClick = {
                        //    re = re.addDays(7)
                        //    state.restart.value = true
                        restart.value = true
                        Log.d("restart1", "True")
                    }) {
                        Text("Next 7 days")
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    dS.dateScroller(0, navController, 6, state, onEvent)
                    Spacer(modifier = Modifier.height(40.dp))
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(state.habits) { a ->
                            //cT.value = a.currentTimes
                            card.card(habit = a, onEvent = onEvent, state)
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(40.dp))
                    dS.dateScroller(1, navController,2, state, onEvent)
                }

            }
        }
    }
    


    @Composable
    fun weekScreen(navController: NavController) {
        val tab: HomeTopTab = HomeTopTab()
        val dS: DateUi = DateUi()
        val card: MyCard = MyCard()
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0A0C11)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                tab.dayWeekTab(navController, 1)
                Spacer(modifier = Modifier.height(40.dp))
                //dS.dateScroller(1, navController, 2)
                Spacer(modifier = Modifier.height(40.dp))
                //card.card(Habit("Drink juice", "Health", 7, false, currentTimes = 4))

            }


        }
    }
}
