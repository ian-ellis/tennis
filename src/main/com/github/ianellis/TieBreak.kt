package com.github.ianellis

class TieBreak(
    private val player1: Player,
    private val player2: Player
) : Game {

    override fun start() {

    }

    override fun state(): GameState {
        return GameState.NotStarted
    }

    override fun pointWonBy(player: Player) {
    }

    override fun score(): String {
        return ""
    }
}