package com.example.minesweeper.presentation.start_screen

import com.example.minesweeper.domain.model.Board
import com.example.minesweeper.domain.model.GameState

data class GameScreenState(
    val difficulty: Float = .15F,
    val board: Board = Board(bombPct = difficulty),
    val settingsIsVisible: Boolean = false
)
