package com.example.resonance

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.resonance.components.BottomNav
import com.example.resonance.viewmodel.DWHabitViewModel
import com.example.resonance.viewmodel.HabitViewModel
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    companion object Set {
        var minimize = false
    }

    private val habitViewModel by viewModels<HabitViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HabitViewModel(
                        AlphaDatabase.getDatabase(applicationContext).HabitDao()
                    ) as T
                }
            }
        }
    )
    private val dayHabitViewModel by viewModels<DWHabitViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DWHabitViewModel(
                        AlphaDatabase.getDatabase(applicationContext).HabitDao()
                    ) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.onBackground
            ) {
                val state by habitViewModel.state.collectAsState()
                val stateA by dayHabitViewModel.state.collectAsState()
                val navController = rememberNavController()
                val nav = Navigation()

                nav.myNavigator(
                    navController = navController,
                    state = state,
                    stateA = stateA,
                    onEvent = habitViewModel::onEvent,
                    onEventA = dayHabitViewModel::onEvent
                )

                if (!minimize) {
                    Column(
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        BottomNav().botNav(navController = navController)
                    }
                }
            }
            Repeating().restartDaily(this, 0,10)
            Repeating().dailyNotification(this, 16,0)
        }
    }
}



// easier date type
data class MyDate (
    var Day: Int,
    var Month: Int,
    var Year: Int,
    val DoW: Int = 0,
    val selected: Int = 0,
    val thirtyOneMonths: List<Int> = listOf(0,2,4,6,7,9,11),
    val thirtyMonths: List<Int> = listOf(3,5,8,10),
    val feb: Int = 1
) {
    @Override
    override fun equals(other: Any?): Boolean {
        val date: MyDate = other as MyDate
        return  this.Day == date.Day &&
                this.Month == date.Month &&
                this.Year == date.Year &&
                this.DoW == date.DoW
        }

    fun doneDateInList(cal: Calendar, done: Int): List<Int> {
        cal.add(cal.get(2), - 1)
        this.Day = cal.get(5)
        this.Month = cal.get(2)
        this.Year = cal.get(1)

        return listOf(cal.get(5), cal.get(2), cal.get(1), done)
    }
    fun lessThan(date: MyDate): Boolean {
        return if (this.Month < date.Month) true
        else if (this.Month == date.Month)
             this.Day <= date.Day
        else false
    }
    fun dateToIntList(): List<Int> {
        return listOf(this.Day, this.Month, this.Year, this.selected)
    }

    //create duplicate future dates with a starting date
    fun listToDates(lst: List<Int>, daily: Boolean, week: Int): List<Int> {
        val cal = Calendar.getInstance()
        cal.add(cal.get(2), -cal.get(7) + 1) //start from sunday
        val tmp = ArrayList<Int>()
        for (x in 0 until 7 * week) {
            if (daily) {
                tmp.add(cal.get(5)) // date
                tmp.add(cal.get(2)) // month
                tmp.add(cal.get(1)) // year
                tmp.add(1) // selected
            } else {
                if (lst[x%7] == 1) {
                    tmp.add(cal.get(5)) // date
                    tmp.add(cal.get(2)) // month
                    tmp.add(cal.get(1)) // year
                    tmp.add(1)
                }
            }
            cal.add(cal.get(2), 1)
        }
        return tmp
    }

    fun isZero(): Boolean {
        return  this.Day == 0 &&
                this.Month == 0 &&
                this.Year == 0 &&
                this.DoW == 0
    }

    fun addDays(num: Int): MyDate { //max 30 days addition
        var day = 0;
        var mon = 0;
        var yr = 0;
        if (this.Month == feb) {
            if (this.Day + num <= 28) {
                day = this.Day + num
                return this.copy(Day = day)
            } else {
                day = this.Day + num - 28
                mon = this.Month + 1
                return this.copy(Day = day, Month = mon)
            }
        } else if (thirtyOneMonths.contains(this.Month)) {
            if (this.Day + num <= 31) {
                day = this.Day + num
                return this.copy(Day = day)
            } else {
                day = this.Day + num - 31
                if (this.Month + 1 > 12) {
                    yr = this.Year + 1
                    mon = 0
                    return this.copy(Day = day, Month = mon, Year = yr)
                } else {
                    mon = this.Month + 1
                    return this.copy(Day = day, Month = mon)
                }
            }
        } else {
            if (this.Day + num <= 30) {
                day = this.Day + num
                return this.copy(Day = day)
            } else {
                day = this.Day + num - 30
                if (this.Month + 1 > 12) {
                    yr = this.Year + 1
                    mon = 0
                    return this.copy(Day = day, Month = mon, Year = yr)
                } else {
                    mon = this.Month + 1
                    return this.copy(Day = day, Month = mon)
                }
            }
        }
    }

}
data class Pair(val Head: MyDate, val Tail: MyDate)







