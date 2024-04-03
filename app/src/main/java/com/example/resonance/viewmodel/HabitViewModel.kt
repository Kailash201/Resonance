package com.example.resonance.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resonance.Habit
import com.example.resonance.HabitDao
import com.example.resonance.HabitEvent
import com.example.resonance.MyDate
import com.example.resonance.Tag
import com.example.resonance.states.HabitState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class HabitViewModel (
    private val dao: HabitDao
    ): ViewModel() {

    private val _state = MutableStateFlow(HabitState())
    val state = _state

    companion object SecId {
        var SecId = 0
    }

   fun onEvent(event: HabitEvent) {
       when(event) {
           HabitEvent.SaveHabit -> {
               val name = _state.value.name
               val tag = _state.value.tag
               val freq = _state.value.freq
               val daily = _state.value.daily
               val days = _state.value.days

               val cal = Calendar.getInstance()

               if(name.isBlank() || tag.isBlank()) {
                   return
               }
               val habit = Habit(
                   name,
                   tag,
                   freq,
                   daily,
                   dates = days,
                   secId = SecId.SecId,
                   startDate = listOf(cal.get(5), cal.get(2), cal.get(1)),
                   displayDate = 0,
                   master = true

               )
               val tagObj = Tag(tag)

               viewModelScope.launch(Dispatchers.IO) {
                   for (x in 0 until if (daily) 7*2 else (days.size/4)) {
                       dao.insertHabit(habit) //insert for all the days
                       habit.master = false
                       habit.displayDate += 4
                   }
                   val tags = dao.getAllTags()

                   if (!tags.contains(tagObj))
                       dao.insertTag(tagObj)

               }
               SecId.SecId += 1

               _state.update {
                   it.copy(
                       name = "",
                       tag = "",
                       freq = 0,
                       daily = false,
                       days = listOf(0,0,0,0,0,0,0),
                       //id = it.id + 1,
                   )
               }
           }
           is HabitEvent.deleteHabit -> {
               viewModelScope.launch(Dispatchers.IO) {
                   dao.deleteHabit(event.habit.secId)
                   HabitEvent.SetAllHabits
               }
           }
           is HabitEvent.setDaily -> {
               _state.update{
                   it.copy(daily = event.daily)
               }
           }
           is HabitEvent.setFreq -> {
               _state.update {
                   it.copy(freq = event.freq)
               }
           }
           is HabitEvent.setName -> {
               _state.update {
                   it.copy(name = event.name)
               }
           }
           is HabitEvent.setTag -> {
               _state.update {
                   it.copy(tag = event.Tag)
               }
               if (event.newTag) {
                   viewModelScope.launch(Dispatchers.IO) {
                       dao.insertTag(Tag(event.Tag))
                   }
               }
           }
           is HabitEvent.SetAllHabits -> {
               viewModelScope.launch(Dispatchers.IO) {
                   val habits = dao.getAllHabits()
                   val tmp = mutableStateListOf<Habit>()
                   tmp.addAll(habits)
                   _state.update {
                       it.copy(habits = tmp)
                   }
               }
           }
           is HabitEvent.setDays -> {
               _state.update {
                   it.copy(
                       days = event.days
                   )
               }
           }
           HabitEvent.GetAllTags -> {
               viewModelScope.launch(Dispatchers.IO) {
                   val tags = dao.getAllTags()
                   _state.update {
                       it.copy(
                           tags = tags
                       )
                   }
               }

           }

       }
   }





}