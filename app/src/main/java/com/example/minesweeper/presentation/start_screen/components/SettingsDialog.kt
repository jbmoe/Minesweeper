package com.example.minesweeper.presentation.start_screen.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.minesweeper.presentation.components.DialogImpl
import com.example.minesweeper.presentation.components.Text
import com.example.minesweeper.presentation.start_screen.GameEvent
import com.example.minesweeper.presentation.ui.theme.sliderColors

@ExperimentalMaterialApi
@Composable
fun SettingsDialog(difficulty: Float, onEvent: (GameEvent) -> Unit) {
    var diff by remember { mutableStateOf(difficulty) }
    DialogImpl(
        confirmButtonText = "OK",
        dismissButtonText = "Cancel",
        heroIconVector = Icons.Default.Settings,
        title = "Settings",
        content = {
            Column {
                DifficultySlider(diff) {
                    diff = it
                }
            }
        },
        onConfirm = {
            onEvent(GameEvent.DifficultyChanged(diff))
            onEvent(GameEvent.ToggleSettingsVisibility)
        },
        onDismiss = { onEvent(GameEvent.ToggleSettingsVisibility) }
    )
}

@Composable
fun DifficultySlider(difficulty: Float, onValueChange: (Float) -> Unit) {
    Column {
        Text(text = "Difficulty")
        Slider(
            value = difficulty,
            valueRange = 0.1f..0.3f,
            steps = 3,
            onValueChange = {
                onValueChange(it)
            },
            colors = sliderColors()
        )
    }
}
