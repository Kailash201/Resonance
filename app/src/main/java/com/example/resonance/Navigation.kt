package com.example.resonance

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.resonance.pages.DWHabitViewer
import com.example.resonance.pages.ExpandedHabitView
import com.example.resonance.pages.HabitAdder
import com.example.resonance.pages.MoodCalendarView
import com.example.resonance.pages.OverallHabitView
import com.example.resonance.states.HabitState
import com.example.resonance.states.TypeHabitState

class Navigation {

    @Composable
    fun myNavigator(
        navController: NavHostController,
        state: HabitState,
        stateA: TypeHabitState,
        onEvent: (HabitEvent) -> Unit,
        onEventA: (DayHabitEvent) -> Unit
    ) {
        val homeScreen: DWHabitViewer = DWHabitViewer()
        val habitScreen: OverallHabitView = OverallHabitView()
        val addHabit: HabitAdder = HabitAdder()
        val detailHabit: ExpandedHabitView = ExpandedHabitView()
        val moodView: MoodCalendarView = MoodCalendarView()

        NavHost(
            navController = navController,
            startDestination = Screen.DayScreen.route
        //            + "/{currentPos}/{DoW}"
            ) {
            composable(
                route = Screen.DayScreen.route
                //        + "/{currentPos}/{DoW}"
                ,
//                arguments = listOf(
//                    navArgument("currentPos") {
//                        type = NavType.IntType
//                        defaultValue = 6
//                        nullable = false
//                    },
//                    navArgument("DoW") {
//                            type = NavType.IntType
//                            defaultValue = 2
//                            nullable = false
//                        }
//
//                )
            ) { entry -> homeScreen.dayScreen(
                    navController = navController,
                    onEvent = onEventA,
                    state = stateA,
                  //  currentPos = entry.arguments!!.getInt("currentPos"),
                  //  DoW = entry.arguments!!.getInt("DoW")
                )
            }
            composable(route = Screen.WeekScreen.route) {
                homeScreen.weekScreen(navController = navController)
            }
            composable(route = Screen.HabitScreen.route) {
                habitScreen.mainHabitView(
                    navController = navController,
                    state = state,
                    onEvent = onEvent
                )
            }
            composable(route = Screen.AddHabitScreen.route) {
                addHabit.addHabitPage(
                    navController = navController,
                    state = state,
                    onEvent = onEvent
                )
            }
            composable(
                route = Screen.ExpandedHabitView.route
            ) {
                detailHabit.detailedHabit(
                    navController = navController
                )
            }
            composable(
                route = Screen.MoodCalendarView.route
            ) {
                moodView.MoodCalendar(
                    navController = navController
                )
            }

        }
    }
}