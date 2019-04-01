package com.github.ianellis

class Match(
    private val set:Game
) : Game {
    override fun onGameComplete(listener: (GameState.Complete) -> Unit) {

    }

    override fun start() {
        set.start()
    }

    override fun state(): GameState {
        return set.state()
    }

    override fun pointWonBy(player: Player) {
        set.pointWonBy(player)
    }

    override fun score(): String {
        return set.score()
    }
}

