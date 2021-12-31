package com.example.minesweeper.presentation.start_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.minesweeper.domain.model.Board
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    boardIn: Board
) : ViewModel() {
    private val _state = mutableStateOf(GameScreenState())
    val state: State<GameScreenState> = _state

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.Reveal -> {
                _state.value.board.reveal(event.cell)
            }
            is GameEvent.ToggleFlagged -> {
                _state.value.board.toggleFlag(event.cell)
            }
            GameEvent.Restart -> {
                _state.value.board.restart()
            }
            GameEvent.ToggleSettingsVisibility -> {
                _state.value = _state.value.copy(
                    settingsIsVisible = !_state.value.settingsIsVisible
                )
            }
            is GameEvent.DifficultyChanged -> {
                _state.value = _state.value.copy(
                    difficulty = event.value,
                    board = Board(bombPct = event.value)
                )
            }
        }
    }
}