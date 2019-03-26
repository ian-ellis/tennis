package com.github.ianellis

class TieBreak(
    private val player1: Player,
    private val player2: Player
) : Game {

    companion object {
        private const val MIN_POINTS_TO_WIN = 7
        private const val WIN_MARGIN = 2
    }

    private var player1Points: Int = 0
    private var player2Points: Int = 0

    private var state: GameState = GameState.NotStarted

    override fun start() {
        if (state == GameState.NotStarted) {
            state = GameState.Started(score())
        }
    }

    override fun state(): GameState {
        return state
    }

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

            val score = "$player1Points-$player2Points"
            GameState.Started(score)

        }
    }

    private fun winner(): Player? {
        return when {
            player1Points >= MIN_POINTS_TO_WIN && (player1Points >= player2Points + WIN_MARGIN) -> player1
            player2Points >= MIN_POINTS_TO_WIN && (player2Points >= player1Points + WIN_MARGIN) -> player2
            else -> null
        }
    }
}