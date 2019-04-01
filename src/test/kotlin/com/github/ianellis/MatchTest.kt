package com.github.ianellis

import org.junit.Test
import kotlin.test.assertEquals

class MatchTest {


    @Test
    fun `score() - returns score from set`() {
        val score = "0-0, 15-0"
        val set = gameWith(score)
        val match = Match(set)
        assertEquals(score, match.score())
    }

    @Test
    fun `pointWonBy() - invokes set pointsWonByMethod on set`() {
        var pointsWonByCaptureValue: Player? = null
        val set = object : Game {
            override fun onGameComplete(listener: (GameState.Complete) -> Unit) {}
            override fun start() {}
            override fun pointWonBy(player: Player) {
                pointsWonByCaptureValue = player
            }
            override fun score(): String = ""
            override fun state() = GameState.NotStarted

        }
        val match = Match(set)
        val player = Player("player1")
        match.pointWonBy(player)

        assertEquals(player, pointsWonByCaptureValue)
    }

    private fun gameWith(score: String): Game {
        return object : Game {
            override fun onGameComplete(listener: (GameState.Complete) -> Unit) {}
            override fun start() {}
            override fun pointWonBy(player: Player) {}
            override fun score(): String = score
            override fun state() = GameState.NotStarted
        }
    }

}