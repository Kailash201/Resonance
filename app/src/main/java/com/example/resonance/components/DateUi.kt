package com.example.resonance.components

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.DayHabitEvent
import com.example.resonance.MyDate
import com.example.resonance.Navigation
import com.example.resonance.states.MainState
import com.example.resonance.states.TypeHabitState

class DateUi {

    private val monthList = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Nov", "Dec")


    private val dayList = listOf("nil", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")


    @Composable
    fun weekTab(selected: Boolean, startDate: MyDate, endDate: MyDate, onClick: () -> Unit)  {
        Tab(selected,
            onClick,
            Modifier.background(Color(0xFF0A0C11)),
            selectedContentColor = Color.Transparent) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(90.dp)
                    .width(75.dp)
                    .background(if (selected) Color(0xFF2F013E) else Color(0xFF0B0B0B))
                    .padding(1.dp),

                ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    Column() {
                        Text(
                            text = startDate.Day.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = if (selected) Color.White else Color(0xFF312734)
                        )
                        Text(
                            text = monthList[startDate.Month],
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = if (selected) Color.White else Color(0xFF312734)
                        )
                    }
                    Text(
                        text = "--",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selected) Color.White else Color(0xFF312734)
                    )
                    Column() {
                        Text(
                            text = endDate.Day.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = if (selected) Color.White else Color(0xFF312734)
                        )
                        Text(
                            text = monthList[endDate.Month],
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = if (selected) Color.White else Color(0xFF312734)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun dateTab(selected: Boolean, title: MyDate, onClick: () -> Unit)  {
        Tab(selected,
            onClick,
            Modifier.background(Color(0xFF0A0C11)),
            selectedContentColor = Color.Transparent) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .height(90.dp)
                    .width(70.dp)
                    .background(if (selected) Color(0xFF2F013E) else Color(0xFF0B0B0B)),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = title.Day.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = if (selected) Color.White else Color(0xFF312734)
                    )
                    Text(
                        text = monthList[title.Month],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = if (selected) Color.White else Color(0xFF312734)
                    )
                    Text(
                        text = dayList[title.DoW],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = if (selected) Color.White else Color(0xFF312734)
                    )
                }
            }
        }
    }

    @Composable
    fun dateScroller(
        type: Int,
        navController: NavController,
        currentPos: Int = 6,
        habitState: TypeHabitState,
        event: (DayHabitEvent) -> Unit
    ) {

        var state by remember { mutableStateOf(currentPos) } // start at the current date
        var weekState by remember { mutableStateOf(2) } // start at the current week

        // for day view
        val cal = Calendar.getInstance()
        val dateList = ArrayList<MyDate>()
        // start to add back from 1 week earlier
        cal.add(cal.get(2), -7)

        // another calender to crosscheck
        val crCal = Calendar.getInstance()
        val curDate = MyDate(crCal.get(5), crCal.get(2), crCal.get(1), crCal.get(7))


        for (i in 0..14) {
            cal.add(cal.get(2), 1)
            dateList.add(MyDate(cal.get(5), cal.get(2), cal.get(1),
//                if (cal.get(5) == crCal.get(5)) 8
//                else if (cal.get(5) + 1 == crCal.get(5)) 10
//                else if (cal.get(5) - 1 == crCal.get(5)) 9
//                else
                    cal.get(7)
            ))
        }
        // for week view
        val secCal = Calendar.getInstance()
        val weekDateList = ArrayList<com.example.resonance.Pair>()
        secCal.add(secCal.get(2), -secCal.get(7) + 2) // sets the date to monday of the cur week
        val firstDayOfWeek = MyDate(secCal.get(5), secCal.get(2), secCal.get(1), secCal.get(7))
        secCal.add(secCal.get(2), -14) // goes back two weeks
        for (i in 0..4) {
            val startDate = MyDate(secCal.get(5), secCal.get(2), secCal.get(1), 2)
            secCal.add(secCal.get(2), 6)
            val endDate = MyDate(secCal.get(5), secCal.get(2), secCal.get(1), 2)
            weekDateList.add(com.example.resonance.Pair(startDate, endDate))
            secCal.add(secCal.get(2), 1)
        }

        // to display list on bootup
        if (state == 6) {
            val cl = Calendar.getInstance()
            event(DayHabitEvent.GetHabits(cl.get(5), cl.get(2), cl.get(1)))
            habitState.clickable.value = true
        }

        ScrollableTabRow(
            selectedTabIndex = if (type == 0) state else weekState,
            edgePadding = 0.dp,
            indicator = {},
            divider = {}
        ) {

            if (type == 0) {
                dateList.forEachIndexed { index, title ->
                    val selected = state == index
                    dateTab(selected = selected, title = title, onClick = {
                        state = index
                        if (true) {
                            event(DayHabitEvent.GetHabits(title.Day, title.Month, title.Year))
                            habitState.clickable.value =
                                title.equals(curDate) //only click on the curr date
                            MainState.completed = false
                         }
                    })
                }
            } else
                    weekDateList.forEachIndexed {
                        index, title ->
                    val selected = weekState == index
                    weekTab(
                        selected = selected,
                        startDate = title.Head,
                        endDate = title.Tail,
                        onClick = {weekState = index})
                }
        }
    }



}