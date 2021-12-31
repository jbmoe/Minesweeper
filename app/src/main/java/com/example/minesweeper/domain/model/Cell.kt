package com.example.minesweeper.domain.model

data class Cell(
    val row: Int,
    val column: Int,
    val state: TileState = TileState.CLOSED,
    val isBomb: Boolean = false,
    val isExploded: Boolean = false,
    var adjacentMines: Int? = null
)
