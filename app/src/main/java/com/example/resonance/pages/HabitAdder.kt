package com.example.resonance.pages

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerDefaults.windowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resonance.HabitEvent
import com.example.resonance.MainActivity
import com.example.resonance.MyDate
import com.example.resonance.Screen
import com.example.resonance.states.HabitState
import com.google.android.material.snackbar.Snackbar

class HabitAdder {
    
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
    @Composable
    fun addHabitPage(
        navController: NavController,
        state: HabitState,
        onEvent: (HabitEvent) -> Unit
    ) {
        MainActivity.minimize = true


        val date = MyDate(0,0,0,0)
        val textColor = 0xFFC76BFF
        val borderColor = 0xFFFF00F5
        val fillColor = 0xFF131313
        val grad = Brush.horizontalGradient(listOf(Color.Magenta, Color.White ))
        // get all the tags
        onEvent(HabitEvent.GetAllTags)
        val tags = state.tags

        var tagState by remember { mutableStateOf(-1)}
        var openBtmSheet by remember { mutableStateOf(false)}
        var dailyState by remember { mutableStateOf(true)}
        var freqState by remember { mutableStateOf("")}
        val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val checkedState = remember { mutableStateListOf<Int>().apply {
            addAll(listOf(0,0,0,0,0,0,0))
            }
        }
        var tagCount by remember { mutableStateOf(0)}
        val showToast = remember(false) { mutableStateOf(false)}

        if (showToast.value) {
            Toast.makeText(
                LocalContext.current, "Some details are missing",
                Toast.LENGTH_SHORT
            ).show()
            showToast.value = false
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0A0C11)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Create", fontSize = 40.sp, style = TextStyle(brush = grad))
                    Text(text = "Habit", fontSize = 40.sp, style = TextStyle(brush = grad) )
                }
                TextField(
                    value = state.name,
                    onValueChange = { onEvent(HabitEvent.setName(it)) },
                    label = { Text("Habit's name") },
                    modifier = Modifier
                        .border(1.dp, Color(borderColor), CircleShape)
                        .clip(CircleShape),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(fillColor),
                        textColor = Color.White
                    )
                )
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 10.dp)
                ) {
                    AssistChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { openBtmSheet = true },
                        label = { Text("New Tag") },
                    )
                    tags.forEachIndexed{index, tag ->
                        InputChip(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            onClick = {
                                tagState = index
                                onEvent(HabitEvent.setTag(tags[tagState].name))
                                      },
                            label = { Text(tags[index].name) },
                            selected = index == tagState
                        )
                    }
                }
                if (openBtmSheet) {
                    TextField(
                        value = state.tag,
                        onValueChange = { onEvent(HabitEvent.setTag(it)) },
                        label = { Text("Habit's Tag") },
                        modifier = Modifier
                            .border(1.dp, Color(borderColor), CircleShape)
                            .clip(CircleShape),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(fillColor),
                            textColor = Color.White
                        )
                    )
                }
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(borderColor), RoundedCornerShape(10.dp))
                        .width(270.dp)
                        .height(100.dp),
                    color = Color(0xFF0A0C11),

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = "Is it daily?", color = Color.White)
                            // Spacer(modifier = Modifier.width(20.dp))
                            Checkbox(checked = dailyState, onCheckedChange = {
                                dailyState = it
                            })
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = "Is it weekly?", color = Color.White, fontSize = 15.sp)
                            //Spacer(modifier = Modifier.width(20.dp))
                            Checkbox(
                                checked = !dailyState,
                                onCheckedChange = {
                                    dailyState = !it
                                }
                            )
                        }
                    }

                }
                TextField(
                    value = freqState,
                    onValueChange = { freqState = it },
                    label = { Text("How many times per ${if (dailyState) "day" else "week"}") },
                    modifier = Modifier
                        .border(1.dp, Color(borderColor), CircleShape)
                        .clip(CircleShape)
                        ,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(fillColor),
                        textColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (!dailyState) {
                    Text(
                        text = "Which days do you want to do it?",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(start = 10.dp)
                    ) {
                        state.days.forEachIndexed { index, i ->
                            InputChip(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                onClick = {
                                    if (tagCount < freqState.toInt() &&
                                        checkedState[index] == 0) {
                                        checkedState[index] = 1
                                        tagCount += 1
                                    } else if (checkedState[index] == 1){
                                        checkedState[index] = 0
                                        tagCount -=1
                                    }
                                },
                                label = { Text(days[index]) },
                                selected = checkedState[index] == 1
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier.border(1.dp, Color(borderColor), CircleShape),
                    onClick = {
                        if (state.name == "" || state.tag == "" || freqState == "" ||
                            (tagCount < freqState.toInt() && !dailyState)) {
                           showToast.value = true
                        } else {
                            onEvent(
                                HabitEvent.setDays(
                                    date.listToDates(
                                        checkedState,
                                        dailyState,
                                        2
                                    )
                                )
                            )
                            onEvent(HabitEvent.setDaily(dailyState))
                            onEvent(HabitEvent.setFreq(freqState.toInt()))
                            onEvent(HabitEvent.SaveHabit)
                            navController.popBackStack()
                        }  
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(fillColor))
                ) {
                    Text("Add")
                }
            }
        }
    }
}