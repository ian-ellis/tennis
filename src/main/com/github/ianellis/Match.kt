package com.github.ianellis

class Match(
    private val set:Game
) {

    fun pointWonBy(player: Player) {
        set.pointWonBy(player)
    }

    fun score(): String {
        return set.score()
    }
}

