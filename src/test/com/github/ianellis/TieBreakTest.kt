package com.github.ianellis

import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TieBreakTest {

    private lateinit var game: TieBreak
    private lateinit var player1: Player
    private lateinit var player2: Player

    @Before
    fun setup() {
        player1 = Player("player1")
        player2 = Player("player2")
        game = TieBreak(player1, player2)
    }

    @Test
    fun `score() - points are scored one at a time until 7 points`() {
        player1.`wins n points`(1)
        assertEquals("1-0", game.score())

        player1.`wins n points`(1)
        assertEquals("2-0", game.score())

        player1.`wins n points`(1)
        assertEquals("3-0", game.score())

        player1.`wins n points`(1)
        assertEquals("4-0", game.score())

        player1.`wins n points`(1)
        assertEquals("5-0", game.score())

        player1.`wins n points`(1)
        assertEquals("6-0", game.score())

    }

    @Test
    fun `state() - returns GameNotStarted if no points player, and ont started`() {
        assertEquals(GameState.NotStarted, game.state())
    }

    @Test
    fun `state() - game is won if a player wins 7 points by margin of 2`() {
        player1.`wins n points`(5)
        player2.`wins n points`(7)
        assertEquals(GameState.Complete(player2), game.state())
    }

    @Test
    fun `state() - returns Started after a call to start()`() {
        game.start()
        assertEquals(GameState.Started("0-0"), game.state())
    }

    @Test
    fun `state() - game continues when a player has 7 points if margin is less than 2`() {
        player1.`wins n points`(6)
        player2.`wins n points`(6)
        assertEquals(GameState.Started("6-6"), game.state())

        player1.`wins n points`(1)
        assertEquals(GameState.Started("7-6"), game.state())

        player2.`wins n points`(1)
        assertEquals(GameState.Started("7-7"), game.state())

        player2.`wins n points`(1)
        assertEquals(GameState.Started("7-8"), game.state())
    }

    @Test
    fun `state - when the score is above 6-6, it is won when a player wins 2 in a row`() {
        player1.`wins n points`(6)
        player2.`wins n points`(6)
        assertEquals(GameState.Started("6-6"), game.state())

        player1.`wins n points`(1)
        assertEquals(GameState.Started("7-6"), game.state())

        player1.`wins n points`(1)
        assertEquals(GameState.Complete(player1), game.state())
    }

    private fun Player.`wins n points`(n: Int) {
        (0 until n).forEach {
            game.pointWonBy(this)
        }
    }
}