package com.github.ianellis

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertSame

class StandardGameTest {
    companion object {
        private const val DEUCE = "deuce"
    }

    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var game: StandardGame

    @Before
    fun setup() {
        player1 = Player("player1")
        player2 = Player("player2")
        game = StandardGame(player1, player2)
    }

    @Test
    fun `score() - returns "" when game not started`() {
        assertEquals("", game.score())
    }

    @Test
    fun `score() - returns "0-0" when no points won`() {
        game.start()
        assertEquals("0-0", game.score())
    }

    @Test
    fun `score() - returns "15-0" when one point won by player1`() {
        player1.`wins n points`(1)
        assertEquals("15-0", game.score())
    }

    @Test
    fun `score() - returns "0-15" when one point won by player2`() {
        player2.`wins n points`(1)
        assertEquals("0-15", game.score())
    }

    @Test
    fun `score() - returns "30-0" when two points won by player1`() {
        player1.`wins n points`(2)
        assertEquals("30-0", game.score())
    }

    @Test
    fun `score() - returns "0-30" when two points won by player2`() {
        player2.`wins n points`(2)
        assertEquals("0-30", game.score())
    }

    @Test
    fun `score() - returns "40-0" when three points won by player1`() {
        player1.`wins n points`(3)
        assertEquals("40-0", game.score())
    }

    @Test
    fun `score() - returns "0-40" when three points won by player`() {
        player2.`wins n points`(3)
        assertEquals("0-40", game.score())
    }

    @Test
    fun `score() - returns "deuce" when both players have won 3 points`() {
        //given player 1 has won 3 points
        player1.`wins n points`(3)

        //and player 2 has won 3 points
        player2.`wins n points`(3)

        //then the score is deuce
        assertEquals(DEUCE, game.score())
    }

    @Test
    fun `score() - returns "Advantage player1" when player1 wins a point after deuce`() {
        `given the game is deuce`()
        player1.`wins n points`(1)
        //then the score is advantage player2
        assertEquals("Advantage player1", game.score())
    }

    @Test
    fun `score() - returns "Advantage player2" when player2 scores a point after deuce `() {
        `given the game is deuce`()
        player2.`wins n points`(1)
        //then the score is advantage player2
        assertEquals("Advantage player2", game.score())
    }

    @Test
    fun `score() - returns to "deuce" is both players score 1 point`() {
        `given the game is deuce`()
        player1.`wins n points`(1)
        player2.`wins n points`(1)
        //then the score is deuce again
        assertEquals(DEUCE, game.score())
    }

    @Test
    fun `state() - returns not started initially`() {
        assertSame(GameState.NotStarted, game.state())
    }

    @Test
    fun `state() - returns Started after a call to start()`() {
        game.start()
        assertEquals(GameState.Started("0-0"), game.state())
    }

    @Test
    fun `state() - returns Started if a point is won`() {
        game.pointWonBy(player1)
        assertEquals(GameState.Started("15-0"), game.state())
    }

    @Test
    fun `state() - returns Complete if a player scores 4 points before the other player has scored 3`() {
        player1.`wins n points`(2)
        player2.`wins n points`(4)
        assertEquals(GameState.Complete(winner = player2), game.state())
    }

    @Test
    fun `state() - returns Complete after deuce if a player wins 2 games in a row`() {
        `given the game is deuce`()
        //both player win a point
        player1.`wins n points`(1)
        player2.`wins n points`(1)
        //one player1 wins 2 in a row
        player1.`wins n points`(2)
        //then player 1 wins the game
        assertEquals(GameState.Complete(winner = player1), game.state())
    }

    private fun `given the game is deuce`() {
        player1.`wins n points`(3)
        player2.`wins n points`(3)
    }

    private fun Player.`wins n points`(n: Int) {
        (0 until n).forEach { _ ->
            game.pointWonBy(this)
        }
    }
}