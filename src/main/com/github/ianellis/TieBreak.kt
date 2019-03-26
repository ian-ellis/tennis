package com.github.ianellis

class TieBreak(
    private val player1: Player,
    private val player2: Player
) : Game, AbstractGame(player1) {

    companion object {
        private const val MIN_POINTS_TO_WIN = 7
        private const val WIN_MARGIN = 2
    }


    override fun describeScore(): String {
        return "$player1Points-$player2Points"
    }

    override fun winner(): Player? {
        return when {
            player1Points >= MIN_POINTS_TO_WIN && (player1Points >= player2Points + WIN_MARGIN) -> player1
            player2Points >= MIN_POINTS_TO_WIN && (player2Points >= player1Points + WIN_MARGIN) -> player2
            else -> null
        }
    }
}