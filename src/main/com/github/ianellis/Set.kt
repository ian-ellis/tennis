package com.github.ianellis

class Set(
    private val player1: Player,
    private val player2: Player
) : Game {

    override fun start() {

    }

    override fun pointWonBy(player: Player) {

    }

    override fun score(): String {
        return ""
    }

    override fun state(): GameState {
        return GameState.NotStarted
    }


}