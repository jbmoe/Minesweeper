package com.example.minesweeper.presentation.start_screen

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minesweeper.R
import com.example.minesweeper.domain.model.Board
import com.example.minesweeper.domain.model.GameState
import com.example.minesweeper.presentation.components.DialogImpl
import com.example.minesweeper.presentation.start_screen.components.BoardGrid
import com.example.minesweeper.presentation.ui.theme.MinesweeperTheme
import com.example.minesweeper.presentation.components.Text
import com.example.minesweeper.presentation.start_screen.components.SettingsDialog
import com.example.minesweeper.presentation.start_screen.components.TimerText
import com.example.minesweeper.presentation.start_screen.util.secondsToMinutesSeconds
import com.example.minesweeper.presentation.ui.theme.sliderColors
import kotlin.math.absoluteValue


@ExperimentalMaterialApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onEvent: (GameEvent) -> Unit = viewModel::onEvent
    if (state.board.gameState.value == GameState.SOLVED) {
        val (min, sec) = secondsToMinutesSeconds(state.board.elapsedSeconds.value)
        DialogImpl(
            confirmButtonText = "Restart",
            dismissButtonText = "Dismiss",
            heroIconDrawable = R.drawable.cup_24,
            title = "YOU WON!",
            content = { Text("You swept all mines in $min:$sec minutes.\nBeginners luck?") },
            onConfirm = { onEvent(GameEvent.Restart) }
        )
    } else if (state.board.gameState.value == GameState.FAILED) {
        DialogImpl(
            confirmButtonText = "Restart",
            dismissButtonText = "Dismiss",
            heroIconVector = Icons.Default.Close,
            title = "YOU LOST!",
            content = { Text("Press restart to try again, loser") },
            onConfirm = { onEvent(GameEvent.Restart) }
        )
    }

    if (state.settingsIsVisible) {
        SettingsDialog(state.difficulty, onEvent = onEvent)
    }
    Content(state = state, onEvent = onEvent)
}

@ExperimentalMaterial3Api
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(state: GameScreenState, onEvent: (GameEvent) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    TimerText(
                        modifier = Modifier.padding(start = 8.dp),
                        elapsedSeconds = state.board.elapsedSeconds.value
                    )
                },
                title = {
                    IconButton(
                        modifier = Modifier.size(30.dp),
                        onClick = { onEvent(GameEvent.Restart) }) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                    }
                },
                actions = {
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "${state.board.unflaggedBombs.value}",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(onClick = { onEvent(GameEvent.ToggleSettingsVisibility) }) {
                Icon(Icons.Default.Settings, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            BoardGrid(cells = state.board.cells.value, onEvent = onEvent)
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterial3Api
@Preview(name = "Light mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun Preview() {
    val board: State<GameScreenState> = mutableStateOf(GameScreenState(board = Board(23, 12)))
    MinesweeperTheme {
        Content(board.value) {

        }
    }
}
