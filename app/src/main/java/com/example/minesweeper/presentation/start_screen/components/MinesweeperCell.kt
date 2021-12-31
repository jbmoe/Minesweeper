package com.example.minesweeper.presentation.start_screen.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minesweeper.R
import com.example.minesweeper.domain.model.Cell
import com.example.minesweeper.domain.model.TileState
import com.example.minesweeper.presentation.start_screen.GameEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MinesweeperCell(cell: Cell, onEvent: (GameEvent) -> Unit) {
    val image = getImage(cell)
    val haptic = LocalHapticFeedback.current
    Image(
        modifier = Modifier
            .size(30.dp)
            .combinedClickable(
                onClick = { onEvent(GameEvent.Reveal(cell)) },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onEvent(GameEvent.ToggleFlagged(cell))
                }
            ),
        painter = painterResource(id = image),
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,
        contentDescription = null
    )

}

private fun getImage(cell: Cell): Int = when (cell.state) {
    TileState.CLOSED -> R.drawable.closed
    TileState.FLAGGED -> R.drawable.flagged
    else -> {
        if (cell.isExploded) R.drawable.bomb_expld
        else if (cell.isBomb) R.drawable.bomb1
        else when (cell.adjacentMines) {
            1 -> R.drawable.one
            2 -> R.drawable.two
            3 -> R.drawable.three
            4 -> R.drawable.four
            5 -> R.drawable.five
            6 -> R.drawable.six
            7 -> R.drawable.seven
            8 -> R.drawable.eight
            else -> R.drawable.zero
        }
    }
}

@Composable
@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
fun Preview() {
    Box(contentAlignment = Alignment.Center) {
        MinesweeperCell(
            Cell(
                row = 0,
                column = 0,
                state = TileState.OPEN,
                isBomb = true,
                isExploded = true
            )
        ) {}
    }
}