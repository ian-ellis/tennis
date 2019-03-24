package com.github.ianellis

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class SetTest {

    private lateinit var set: Set
    private lateinit var player1: Player
    private lateinit var player2: Player

    @Before
    fun setup() {
        player1 = Player("player1")
        player2 = Player("player2")
        set = Set(player1, player2)
    }

    @Test
    fun `state() - returns not started initially`() {
        assertEquals(GameState.NotStarted, set.state())
    }

    @Test
    fun `score() - returns 0-0 initially`() {
        assertEquals("0-0", set.score())
    }

    @Test
    fun `start() - returns 0-0 initially`() {
        assertEquals("0-0", set.score())
    }

    @Test
    fun `score() - returns games won by each player, plus current game score`() {
        //give both players win a game each
        player1.winsStandardGames(1)
        player2.winsStandardGames(1)

        //and player 1 wins 1 point of the next game
        player1.`wins n points`(1)

        assertEquals("1-1, 15-0", set.score())
    }

    @Test
    fun `state() - returns Complete when a player win 6 games, 2 more than the other`() {
        player1.winsStandardGames(4)
        player2.winsStandardGames(6)
        assertEquals(GameState.Complete(player2), set.state())
    }

    @Test
    fun `score() - returns final score when complete`() {
        player1.winsStandardGames(4)
        player2.winsStandardGames(6)
        assertEquals("4-6", set.score())
    }

    @Test
    fun `score() - 13th game returns tie break scores, when previously the scores were 6-6`() {
        player1.winsStandardGames(5)
        player2.winsStandardGames(5)
        assertEquals("5-5, 0-0", set.score())

        player1.winsStandardGames(1)
        player2.winsStandardGames(1)
        assertEquals("6-6, 0-0", set.score())

        player1.`wins n points`(1)
        assertEquals("6-6, 1-0", set.score())

        player1.`wins n points`(1)
        assertEquals("6-6, 2-0", set.score())
    }

    @Test
    fun `state() - returns Complete after tie break`() {
        player1.winsStandardGames(6)
        player2.winsStandardGames(6)
        player1.winsTieBreak()
        assertEquals(GameState.Complete(player1), set.state())
    }

    private fun Player.winsTieBreak() {
        this.`wins n points`(7)
    }


    private fun Player.winsStandardGames(games: Int) {
        (0 until games).forEach {
            this.winsStandardGame()
        }
    }

    private fun Player.winsStandardGame() {
        this.`wins n points`(4)
    }


    private fun Player.`wins n points`(n: Int) {
        (0 until n).forEach { _ ->
            set.pointWonBy(this)
        }
    }
}