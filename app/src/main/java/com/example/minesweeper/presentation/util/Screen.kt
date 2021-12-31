package com.example.minesweeper.presentation.util

sealed class Screen(
    val route: String
) {
    object GameScreen : Screen(route = "game")
}