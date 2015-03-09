package org.tcd.game.tictactoe.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Position;


public class GameServiceImplTest {

	GameServiceImpl service;
	
	@Before
	public void setup() {
		service = new GameServiceImpl();
	}
	
	@Test
	public void testNextMove() {
		assertEquals(new Position(1,1), service.nextMove(emptyGame()));
	}
	
	private Game emptyGame() {
		return new Game(Level.HARD, Player.X);
	}

}
