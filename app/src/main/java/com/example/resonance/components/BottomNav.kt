package com.example.resonance.components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.resonance.DayHabitEvent
import com.example.resonance.HabitEvent
import com.example.resonance.MainActivity
import com.example.resonance.pages.DWHabitViewer
import com.example.resonance.pages.OverallHabitView
import com.example.resonance.states.HabitState
import com.example.resonance.states.MainState
import com.example.resonance.states.TypeHabitState

class BottomNav {

    @Composable
    fun botNav (
        navController: NavController,
       // current: Int,

    ) {
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf<String>("Home", "Habits", "Moodie" ,"Profile")
        val icons = listOf<ImageVector>(Icons.Filled.Home, Icons.Filled.List,
            Icons.Filled.Create, Icons.Filled.Person)
        val tmp = listOf("DayScreen", "HabitScreen", "MoodCalendarView","ProfileScreen")

        NavigationBar(
            modifier = Modifier.height(80.dp),
            containerColor = Color(0xFF0B0B0B),
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = {Text(item)},
                    selected = MainState.pageNumber.value == index,
                    onClick = {
                        MainState.pageNumber.value = index
                        navController.popBackStack()
                        navController.navigate(tmp[MainState.pageNumber.value]) {
                           this.launchSingleTop = true
                        }
//                        if (MainState.pageNumber.value == 0) {
//                           // DWHabitViewer.bootup = true
//                            DWHabitViewer.screen.value = true
//                        } else {
//                            DWHabitViewer.screen.value = false
//
//                        }
                              },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color(0xFF2F013E),
                        selectedIconColor = Color.White,
                        selectedTextColor = Color(0xFF2F013E),
                        unselectedIconColor = Color(0xFF312734),
                        unselectedTextColor = Color(0xFF312734)))
                }

            }

     }
}

