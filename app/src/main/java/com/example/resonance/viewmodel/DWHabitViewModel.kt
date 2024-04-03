package com.example.resonance.viewmodel

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resonance.DayHabitEvent
import com.example.resonance.Habit
import com.example.resonance.HabitDao
import com.example.resonance.MyDate
import com.example.resonance.states.MainState
import com.example.resonance.states.TypeHabitState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class DWHabitViewModel(
    private val dao: HabitDao
    ): ViewModel() {

    private val _state = MutableStateFlow(TypeHabitState())
    val state = _state

    fun onEvent(event: DayHabitEvent){
        when (event){
            is DayHabitEvent.AddOne -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val habit = dao.getHabitViaId(event.habit.id)
                    dao.updateCurTime(habit.currentTimes + 1, habit.id)
                    if (habit.updateCount == 0)
                        dao.updateYesCurTime(1, habit.id)
                }

            }
            is DayHabitEvent.GetHabits -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val habits = dao.getAllHabits()
                    val tmp = ArrayList<Habit>().apply {
                        habits.forEach {
                            habit -> val x = habit.displayDate
                                //for (x in 0 until habit.dates.size step 4) {
//                                    Log.d("nmber", "$habit.dates.size")
//                                    Log.d("nmber", "$x")
                                    if (habit.dates[x] == event.day &&
                                        habit.dates[x + 1] == event.month &&
                                        habit.dates[x + 2] == event.year &&
                                        habit.dates[x + 3] == 1)
                                        this.add(habit)
                                }
//                        }
                    }
                    _state.update {
                        it.copy(
                            habits = tmp
                        )
                    }
                }
            }
            is DayHabitEvent.MinusOne -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val habit = dao.getHabitViaId(event.habit.id)
                    dao.updateCurTime(habit.currentTimes - 1, habit.id)
                    if (habit.updateCount == 1)
                        dao.updateYesCurTime(0, habit.id)
                }
            }
            is DayHabitEvent.setEndDate -> {
                _state.update {
                    it.copy(
                        endDate = mutableStateOf(
                            MyDate(event.myDate.Day,
                            event.myDate.Month,
                            event.myDate.Year,
                            event.myDate.DoW))
                        )
                }
            }
            is DayHabitEvent.UpdateHabitsAfterWeeks -> {
                val max = 1
                viewModelScope.launch(Dispatchers.IO) {
                    val hbts = dao.getHabitViaDone(false)
                    val tmpDates = ArrayList<Int>()
                    hbts.forEach {// change the date to nextwks
                        for (x in 0 until it.dates.size step 4) {
                            val cDate = MyDate(
                                Day = it.dates[x],
                                Month = it.dates[x + 1],
                                Year = it.dates[x + 2],
                                selected = 1
                            )
                            val nDate = cDate.addDays(7).dateToIntList()
                            nDate.forEach { d ->
                                tmpDates.add(d)
                            }
                        }
                        if (it.updateCount > max) {
                            dao.updateDone(true, it.secId)
                            dao.insertHabit(it.copy(dates = tmpDates, done = false, id = 0))
                        } else {
                            dao.insertHabit(it.copy(dates = tmpDates, done = false, id = 0,
                                updateCount = it.updateCount + 1))
                        }

                        //tmpHabits.add(it.copy(dates = tmpDates, displayDate = 0, done = false))
                        //dao.insertHabit(it.copy(dates = tmpDates, done = false, id = 0))
                        tmpDates.clear()
                    }
                }

            }

            is DayHabitEvent.DayHasEnded -> {
                viewModelScope.launch(Dispatchers.IO) {
                    event.habits.forEach {
                        val hab = dao.getMasterHabitViaSecId(it.secId, true) // get masterHabit
                        if ((hab.daily && it.currentTimes == hab.freq) ||
                            (!hab.daily && it.updateCount == 1)) {
                            dao.updateCS(hab.currentStreak + 1, hab.id)
                            val tmp = listOf(
                                hab.fiveDayStreak[1],
                                hab.fiveDayStreak[2],
                                hab.fiveDayStreak[3],
                                hab.fiveDayStreak[4],
                                1
                            )
                            dao.updateFiveDaysStreak(tmp, hab.id)
                            dao.updateCompletedCount(hab.completedCount + 1, hab.id)

                            //for calender
                            val cal = Calendar.getInstance()
                            val date = MyDate(0,0,0).doneDateInList(cal, 100)
                            date.forEach {
                                hab.calendarCount.add(it)
                            }
                            dao.updateCalendarCount(hab.calendarCount, hab.id)


                            if (hab.currentStreak >= hab.longestStreak)
                                dao.updateLS(hab.longestStreak + 1, hab.id)
                        } else {
                            dao.updateMissedDays(hab.missedDays + 1, hab.id)
                            val tmp = listOf(
                                hab.fiveDayStreak[1],
                                hab.fiveDayStreak[2],
                                hab.fiveDayStreak[3],
                                hab.fiveDayStreak[4],
                                0
                            )
                            dao.updateFiveDaysStreak(tmp, hab.id)

                            val cal = Calendar.getInstance()
                            val date = MyDate(0,0,0).doneDateInList(cal, -100)
                            date.forEach {
                                hab.calendarCount.add(it)
                            }
                            dao.updateCalendarCount(hab.calendarCount, hab.id)
                        }
                    }



                }

            }

        }

    }

}


//            is DayHabitEvent.SetCurtime -> {
//                viewModelScope.launch(Dispatchers.IO) {
//                    val habit = dao.getHabitViaId(event.habit.id)
////                    _state.update {
////                        val tmp = mutableStateOf(habit.currentTimes)
////                        it.copy(
////                            currentTimes = tmp
////                        )
////                    }
//                }
//
//            }
//            is DayHabitEvent.GetHabit -> {
//                viewModelScope.launch(Dispatchers.IO) {
//                    val habit = dao.getHabitViaId(event.Habit.id)
//                    val tmp = mutableStateOf(habit.currentTimes)
////                    _state.update {
////                        it.copy(
////                            currentTimes = tmp
////                        )
////                    }
//                }
//            }