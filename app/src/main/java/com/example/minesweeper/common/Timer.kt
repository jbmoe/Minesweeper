package com.example.minesweeper.common

import android.util.Log
import kotlin.system.measureNanoTime

object Timer {
    var totalTime = 0L
    var minTime = Long.MAX_VALUE
    var tries = 0
    var avg = 0L

    fun time(tag: String = "", unit: () -> Unit) {
        val time = measureNanoTime {
            unit
        }
        totalTime += time
        if (time < minTime) minTime = time
        avg = totalTime / ++tries
        Log.d("TIME", "$tag Avg: $avg on $tries tries. Min: $minTime")
    }
}