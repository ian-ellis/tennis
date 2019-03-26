package com.github.ianellis

abstract class AbstractGame(
    private val player1: Player,
    private val player2: Player,
    private val minPointsToWin: Int
) : Game {

    companion object {
        private const val MIN_WINNING_MARGIN = 2
    }

    protected var player1Points: Int = 0
    protected var player2Points: Int = 0

    private var state: GameState = GameState.NotStarted

    override fun start() {
        if (state == GameState.NotStarted) {
            state = GameState.Started(describeScore())
        }
    }

    override fun state(): GameState = state

    override fun pointWonBy(player: Player) {
        if (state !is GameState.Complete) {
            updatePointsFor(player)
            updateState()
        }
    }

    override fun score(): String {
        return state.let {
            when (it) {
                is GameState.Started -> it.score
                is GameState.NotStarted -> ""
                is GameState.Complete -> ""
            }
        }
    }

    private fun updatePointsFor(player: Player) {
        if (player == player1) {
            player1Points++
        } else {
            player2Points++
        }
    }

    private fun updateState() {
        state = winner()?.let {
            GameState.Complete(it)
        } ?: kotlin.run {
            GameState.Started(describeScore())
        }
    }

    protected abstract fun describeScore(): String

    private fun winner(): Player? {
        return when {
            player1Points >= minPointsToWin && (player1Points >= player2Points + MIN_WINNING_MARGIN) -> player1
            player2Points >= minPointsToWin && (player2Points >= player1Points + MIN_WINNING_MARGIN) -> player2
            else -> null
        }
    }
}