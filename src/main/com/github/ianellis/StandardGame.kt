package com.github.ianellis

class StandardGame(
    private val player1: Player,
    private val player2: Player
) : Game {

    companion object {
        private const val MIN_POINTS_TO_WIN = 4
        private const val MIN_WINNING_MARGIN = 2
        private const val DEUCE_POINT = 3
        private const val DEUCE = "deuce"
        private val PRE_DEUCE_SCORES = arrayOf("0", "15", "30", "40")
    }

    private var player1Points: Int = 0
    private var player2Points: Int = 0

    private var state: GameState = GameState.NotStarted

    override fun start() {
        if (state == GameState.NotStarted) {
            updateState()
            state = GameState.Started(score())
        }
    }

    override fun state(): GameState = state

    override fun pointWonBy(player: Player) {
        startGameIfNotStarted()
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
                else -> ""
            }
        }
    }

    private fun startGameIfNotStarted() {
        if (state == GameState.NotStarted) {
            start()
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

            val score = if (reachedDeuce()) {
                describePostDeuceScope()
            } else {
                describePreDeuceScore()
            }

            GameState.Started(score)

        }
    }

    private fun winner(): Player? {
        return when {
            player1Points >= MIN_POINTS_TO_WIN && (player1Points >= player2Points + MIN_WINNING_MARGIN) -> player1
            player2Points >= MIN_POINTS_TO_WIN && (player2Points >= player1Points + MIN_WINNING_MARGIN) -> player2
            else -> null
        }
    }

    private fun describePreDeuceScore(): String {
        return "${PRE_DEUCE_SCORES[player1Points]}-${PRE_DEUCE_SCORES[player2Points]}"
    }

    private fun describePostDeuceScope(): String {
        return when {
            player1Points > player2Points -> "Advantage ${player1.name}"
            player2Points > player1Points ->  "Advantage ${player2.name}"
            else -> DEUCE
        }
    }

    private fun reachedDeuce(): Boolean {
        return player1Points >= DEUCE_POINT && player2Points >= DEUCE_POINT
    }

}