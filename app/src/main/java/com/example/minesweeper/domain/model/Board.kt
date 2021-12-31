package com.example.minesweeper.domain.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.floor
import kotlin.properties.Delegates
import kotlin.random.Random

class Board constructor(
    private val rowCount: Int = 20,
    private val columnCount: Int = 12,
    bombPct: Float = .3F
) {
    private val ROW_RANGE = 0 until rowCount
    private val COLUMN_RANGE = 0 until columnCount

    private val TOTAL_CELLS = rowCount * columnCount
    private var TOTAL_BOMBS = (TOTAL_CELLS * bombPct).toInt()

    private var _cells = mutableStateOf(emptyList<List<Cell>>())
    val cells: State<List<List<Cell>>> = _cells

    private val _gameState = mutableStateOf(GameState.NOT_STARTED)
    val gameState: State<GameState> = _gameState

    private val _elapsedSeconds = mutableStateOf(0)
    val elapsedSeconds: State<Int> = _elapsedSeconds

    private val _unflaggedBombs = mutableStateOf(TOTAL_BOMBS)
    val unflaggedBombs: State<Int> = _unflaggedBombs

    private lateinit var _timer: Timer

    private var _openCells = Stack<Cell>()

    init {
        initCells()
    }

    fun restart() {
        if (_gameState.value == GameState.NOT_STARTED) return
        stopTimer()
        _unflaggedBombs.value = TOTAL_BOMBS
        _elapsedSeconds.value = 0
        _gameState.value = GameState.NOT_STARTED
        _openCells = Stack()
        initCells()
    }

    fun reveal(cell: Cell) {
        if (_gameState.value == GameState.FAILED || _gameState.value == GameState.SOLVED) return

        if (cell.state == TileState.CLOSED) {
            replaceCell(cell, cell.copy(state = TileState.OPEN, isExploded = cell.isBomb))
            _openCells.add(cell)
            if (cell.adjacentMines == 0) {
                revealBlankNeighbours(cell)
            }
        } else if (cell.state == TileState.OPEN && cell.adjacentMines != 0) {
            revealUnflaggedNeighbours(cell)
        }
        updateState()
    }

    fun toggleFlag(cell: Cell) {
        if (cell.state != TileState.OPEN) {
            val flag = if (cell.state != TileState.FLAGGED) {
                if (_unflaggedBombs.value > 0) _unflaggedBombs.value--
                TileState.FLAGGED
            } else {
                _unflaggedBombs.value++
                TileState.CLOSED
            }
            replaceCell(cell, cell.copy(state = flag))
        }
    }

    private fun revealUnflaggedNeighbours(cell: Cell) {
        val adjacentMines = cell.adjacentMines!!
        if (adjacentMines <= getFlaggedNeighboursCount(cell)) {
            traverseNeighbours(cell) { i, j ->
                val curr = _cells.value[i][j]
                if (curr.state == TileState.CLOSED) {
                    reveal(curr)
                }
            }
        }
    }

    private fun revealBlankNeighbours(cell: Cell) {
        traverseNeighbours(cell) { row, col ->
            reveal(_cells.value[row][col])
        }
    }

    private fun revealAllCells() {
        val cells: MutableList<MutableList<Cell>> = mutableListOf()
        for (i in ROW_RANGE) {
            val row = mutableListOf<Cell>()
            for (j in COLUMN_RANGE) {
                row.add(_cells.value[i][j].copy(state = TileState.OPEN))
            }
            cells.add(row)
        }
        _cells.value = cells
    }

    private fun replaceCell(oldCell: Cell, newCell: Cell) {
        _cells.value = _cells.value.map { row ->
            row.map {
                if (it == oldCell) {
                    newCell
                } else it
            }
        }
    }

    private fun updateState() {
        if (_gameState.value == GameState.NOT_STARTED) {
            startTimer()
        }
        _openCells.forEach { cell ->
            if (cell.isBomb) {
                revealAllCells()
                _gameState.value = GameState.FAILED
                stopTimer()
                return
            }
        }
        _gameState.value = if (_openCells.size + TOTAL_BOMBS == TOTAL_CELLS) {
            stopTimer()
            GameState.SOLVED
        } else {
            GameState.PLAYING
        }
    }

    private fun startTimer() {
        _timer = timer(period = 1000, initialDelay = 1000) {
            _elapsedSeconds.value++
        }
    }

    private fun stopTimer() {
        if (_gameState.value != GameState.NOT_STARTED)
            _timer.cancel()
    }

    private fun getFlaggedNeighboursCount(cell: Cell): Int {
        var count = 0
        traverseNeighbours(cell) { row, col ->
            if (_cells.value[row][col].state == TileState.FLAGGED) count++
        }
        return count
    }

    private fun initCells() {
        val cells: MutableList<MutableList<Cell>> = mutableListOf()
        for (i in ROW_RANGE) {
            val row = mutableListOf<Cell>()
            for (j in COLUMN_RANGE) {
                row.add(Cell(i, j))
            }
            cells.add(row)
        }

        val bombIndices = mutableSetOf<Pair<Int, Int>>()
        while (bombIndices.size < TOTAL_BOMBS) {
            var inserted = false
            var indices: Pair<Int, Int>? = null
            while (!inserted) {
                indices = Pair((0 until rowCount).random(), (0 until columnCount).random())
                inserted = bombIndices.add(indices)
            }

            val (i, j) = indices!!
            cells[i][j] = Cell(i, j, isBomb = true)
        }
        _cells.value = cells
        initAdjacentCountAll()
    }

    private fun initAdjacentCountAll() {
        val grid = cells.value
        for (row in ROW_RANGE) {
            for (col in COLUMN_RANGE) {
                val cell = grid[row][col]
                if (!cell.isBomb) {
                    var mines = 0
                    traverseNeighbours(cell) { i, j ->
                        if (grid[i][j].isBomb) mines++
                    }
                    cell.adjacentMines = mines
                }
            }
        }
    }

    private fun traverseNeighbours(cell: Cell, callback: (Int, Int) -> Unit) {
        val (row, col) = cell
        for (i in row - 1..row + 1) {
            for (j in col - 1..col + 1) {
                if (indicesAreValid(cell, i, j)) {
                    callback(i, j)
                }
            }
        }
    }

    private fun traverseAllCells(callback: (Cell) -> Unit) {
        _cells.value.forEach { row ->
            row.forEach { cell ->
                callback(cell)
            }
        }
    }

    private fun indicesAreValid(cell: Cell? = null, row: Int, col: Int): Boolean {
        val indicesValid = ((row in ROW_RANGE) && (col in COLUMN_RANGE))
        return if (cell != null) {
            (indicesValid && !(cell.row == row && cell.column == col))
        } else {
            indicesValid
        }
    }

    private fun logBoard() {
        val grid = _cells.value
        var string = "BOARD\n  / "
        (grid.first().indices).forEach {
            string += "$it "
        }
        string += "|\n  _____________\n"
        grid.forEachIndexed { i, row ->
            string += if (i < 10) "$i | " else "$i| "
            row.forEach { cell ->
                string += if (cell.state == TileState.CLOSED) "X " else "0 "
            }
            string += "|\n"
        }
        Log.d("PRINT", string)
    }
}
