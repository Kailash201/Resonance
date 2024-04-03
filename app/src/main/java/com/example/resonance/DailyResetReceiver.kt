package com.example.resonance

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.resonance.pages.DWHabitViewer
import com.example.resonance.states.TypeHabitState
import com.example.resonance.viewmodel.HabitViewModel


class DailyResetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        DWHabitViewer.restart.value = true
        Log.d("Restart2", "ran")
    }

}