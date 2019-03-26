package com.github.ianellis

abstract class AbstractGame(
    private val player1: Player
) : Game {

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

    protected abstract fun winner(): Player?
}