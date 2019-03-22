package com.github.ianellis

interface Game {
    fun start()
    fun pointWonBy(player: Player)
    fun score(): String
    fun state(): GameState
}