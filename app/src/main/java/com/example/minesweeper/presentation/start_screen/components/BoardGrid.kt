package com.example.minesweeper.presentation.start_screen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.minesweeper.domain.model.Cell
import com.example.minesweeper.presentation.start_screen.GameEvent

@Composable
fun BoardGrid(cells: List<List<Cell>>, onEvent: (GameEvent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .shadow(4.dp)
    ) {
        items(cells) { row ->
            LazyRow {
                items(row) { cell ->
                    MinesweeperCell(
                        cell = cell,
                        onEvent = { onEvent(it) }
                    )
                }
            }
        }
    }
}