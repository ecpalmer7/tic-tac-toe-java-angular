package org.tcd.game.tictactoe.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Position;


public class GameServiceTest {

	GameService service;
	
	@Before
	public void setup() {
		service = new GameService();
	}
	
	@Test
	public void testNextMove() {
		assertEquals(new Move( new Position(1,1), Player.X),  service.nextMove(emptyGame()));
	}
	
	private Game emptyGame() {
		return new Game(Level.HARD, Player.X, null);
	}

}
