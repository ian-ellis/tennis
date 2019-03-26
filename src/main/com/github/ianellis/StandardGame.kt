package com.github.ianellis

class StandardGame(
    private val player1: Player,
    private val player2: Player
) : Game, AbstractGame(player1,player2,4) {


    companion object {
        private const val DEUCE_POINT = 3
        private const val DEUCE = "deuce"
        private val PRE_DEUCE_SCORES = arrayOf("0", "15", "30", "40")
    }

    override fun describeScore(): String {
        return if (reachedDeuce()) {
            describePostDeuceScope()
        } else {
            describePreDeuceScore()
        }
    }

    private fun describePreDeuceScore(): String {
        return "${PRE_DEUCE_SCORES[player1Points]}-${PRE_DEUCE_SCORES[player2Points]}"
    }

    private fun describePostDeuceScope(): String {
        return when {
            player1Points > player2Points -> "Advantage ${player1.name}"
            player2Points > player1Points -> "Advantage ${player2.name}"
            else -> DEUCE
        }
    }

    private fun reachedDeuce(): Boolean {
        return player1Points >= DEUCE_POINT && player2Points >= DEUCE_POINT
    }


}