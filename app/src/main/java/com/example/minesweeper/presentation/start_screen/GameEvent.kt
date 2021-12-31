package com.example.minesweeper.presentation.start_screen

import com.example.minesweeper.domain.model.Cell

sealed interface GameEvent {
    object Restart : GameEvent
    data class Reveal(val cell: Cell) : GameEvent
    data class ToggleFlagged(val cell: Cell) : GameEvent
    data class DifficultyChanged(val value: Float) : GameEvent
    object ToggleSettingsVisibility : GameEvent
}