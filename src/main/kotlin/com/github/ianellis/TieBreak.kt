package com.github.ianellis

class TieBreak(
    private val player1: Player,
    private val player2: Player
) : Game, AbstractGame(player1,player2,7) {


    override fun describeScore(): String {
        return "$player1Points-$player2Points"
    }

}