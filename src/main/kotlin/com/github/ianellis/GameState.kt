package com.github.ianellis

sealed class GameState {
    object NotStarted : GameState()
    data class Started(val score: String) : GameState()
    data class Complete(val winner: Player) : GameState()
}