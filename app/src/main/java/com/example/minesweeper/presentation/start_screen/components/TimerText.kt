package com.example.minesweeper.presentation.start_screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.minesweeper.presentation.components.Text
import com.example.minesweeper.presentation.start_screen.util.secondsToMinutesSeconds

@Composable
fun TimerText(modifier: Modifier = Modifier, elapsedSeconds: Int) {
    val (min, sec) = secondsToMinutesSeconds(elapsedSeconds)
    val paddedMin = min.toString().padStart(2, '0')
    val paddedSec = sec.toString().padStart(2, '0')
    Text(
        modifier = modifier,
        text = "${paddedMin}:${paddedSec}",
        style = MaterialTheme.typography.displayLarge
    )
}