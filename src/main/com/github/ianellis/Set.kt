package com.github.ianellis

class Set(
    private val player1: Player,
    private val player2: Player
) : Game {

    companion object {
        private const val MIN_GAMES_TO_WIN = 6
        private const val MIN_WINNING_MARGIN = 2
    }

    private var currentGame: Game = StandardGame(player1, player2)

    private var player1Games: Int = 0
    private var player2Games: Int = 0

    private var state: GameState = GameState.NotStarted

    override fun start() {
        if (state == GameState.NotStarted) {
            state = GameState.Started(score())
        }
    }

    override fun state(): GameState = state

    override fun pointWonBy(player: Player) {
        if (state() !is GameState.Complete) {
            currentGame.pointWonBy(player)
            val currentGameState = currentGame.state()
            if (currentGameState is GameState.Complete) {
                currentGameWonBy(currentGameState.winner)
            }
            updateState()
        }
    }

    override fun score(): String {
        return state.let {
            when (it) {
                is GameState.Started -> it.score
                is GameState.NotStarted -> "0-0"
                is GameState.Complete -> "$player1Games-$player2Games"
            }
        }
    }

    private fun currentGameWonBy(winner: Player) {
        when (winner) {
            player1 -> player1Games ++
            player2 -> player2Games ++
        }
        currentGame = nextGame()
        currentGame.start()
        updateState()
    }

    private fun updateState() {

        state = winner()?.let {
            GameState.Complete(it)
        } ?: kotlin.run {
            GameState.Started("$player1Games-$player2Games, ${currentGame.score()}")
        }
    }

    private fun nextGame(): Game {
        return when {
            player1Games == MIN_GAMES_TO_WIN && player2Games == MIN_GAMES_TO_WIN -> TieBreak(player1, player2)
            else -> StandardGame(player1, player2)
        }
    }

    private fun winner(): Player? {
        return when {
            player1Games >= MIN_GAMES_TO_WIN && (player1Games >= player2Games + MIN_WINNING_MARGIN) -> player1
            player2Games >= MIN_GAMES_TO_WIN && (player2Games >= player1Games + MIN_WINNING_MARGIN) -> player2
            else -> null
        }
    }


}