package com.example.resonance.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resonance.MyDate
import java.util.ArrayList
import java.util.Calendar
import kotlin.math.floor

class CustomCalendar(
    val calCount: ArrayList<Int> = ArrayList<Int>(),
    val dataMonth: Int = 0,
    val dataYear: Int = 0
) {
    private val size: Int = 35
    private val thirtyOne = MyDate(0,0,0).thirtyOneMonths
    private var dayCounter = 0

    private val month = Calendar.getInstance().get(2)
    private val monthList = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Nov", "Dec")


    fun usableDates(): ArrayList<Int> {
        val tmp = ArrayList<Int>()
        for (x in 1 until calCount.size step 4) {
            if (month == calCount[x]) {
                tmp.add(calCount[x - 1])
//                tmp.add(x)
//                tmp.add(x + 1)
                tmp.add(calCount[x + 2])
            }
        }
        return tmp
    }

    @Composable
    fun myCal(
        month: Int,
    ) {
        val days = if (thirtyOne.contains(month)) 31 else if (month == 1) 28 else 30
        val col = floor(days/6.0)
        val lastRow = days%6
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                ,
            color = Color(0xFF0B0B0B)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                Text(text = monthList[month], color = Color.White, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                for (x in 0 until col.toInt()) {
                    calRows(count = 6, x, 6)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                calRows(count = lastRow, col.toInt(), 6)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

    @Composable
    fun calRows(count : Int, startDate: Int, n:Int){
        var text by remember {
            mutableStateOf("")
        }
        var show by remember {
            mutableStateOf(false)
        }
        if (show) {
            Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
            show = false
        }
        Row(
            //modifier = Modifier.background(Color.White)
        ) {
            val lst = usableDates()
            Log.d("d" ,"$lst")
            var color = Color.Gray

            for (x in 0 until count) {
                if (lst.contains(startDate*n + x + 1)) {
                    val index = lst.indexOf(startDate*n + x + 1)
                    if (lst[index + 1] == 100) {
                        color = Color.Green
                    } else {
                        color = Color.Red
                    }
                } else {
                    color = Color.Gray
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(size.dp)
                            .background(color)
                            .clickable(onClick = {
                                text = "${startDate * n + x + 1}$month"
                                show = true
                            })
                    )
                    Text(text = "${startDate*n + x + 1}", color = Color.White)

                }
                Spacer(modifier = Modifier.width(15.dp))
            }
        }
    }
}