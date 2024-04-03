package com.example.resonance.states

import androidx.compose.runtime.mutableStateOf
import com.example.resonance.Habit

class MainState() {

    companion object habit {
        var habit: Habit = Habit("d","d",0, true)
        var pageNumber =  mutableStateOf(0)
        var completed = false

        //debugging
        var hh = mutableStateOf(false)

    }
}
