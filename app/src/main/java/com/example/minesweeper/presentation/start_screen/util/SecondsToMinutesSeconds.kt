package com.example.minesweeper.presentation.start_screen.util

fun secondsToMinutesSeconds(seconds: Int): Pair<Int, Int> {
    val min: Int = seconds / 60 % 60
    val sec: Int = seconds % 60
    return Pair(min, sec)
}