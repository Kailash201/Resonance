package com.example.resonance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.states.MainState

class HomeTopTab {

    @Composable
    fun dayWeekTab(navController: NavController, stateOri: Int) {

        var state by remember { mutableStateOf(stateOri) }
        //var state = stateOri
        val titles = listOf("Day", "Week")
        val tmp = listOf("DayScreen", "WeekScreen")

        TabRow(
            selectedTabIndex = state,
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .fillMaxWidth(0.6f),
            containerColor = Color(0xFF0B0B0B),
            indicator = {},
            divider = {}
        ) {
            titles.forEachIndexed { index, title ->
                val selected = state == index
                Tab(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(if (selected) Color(0xFF2F013E) else Color(0xFF0B0B0B)),
                    selected = selected,
                    onClick = {
                        state = index
                        MainState.hh.value = state != 0
                        //navController.navigate(tmp[state])
                    },
                    text = {
                        Text(
                            text = title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = if (selected) Color.White else Color(0xFF312734)
                        )
                    }
                )
            }
        }

    }
}