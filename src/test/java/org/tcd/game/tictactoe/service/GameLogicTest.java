package org.tcd.game.tictactoe.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Position;


public class GameLogicTest {

	GameLogic logic;
	
	@Test
	public void testNextMoveFork() throws Exception {
		
		Game game = new Game(Level.HARD, Player.X);
		
		game.addMove(new Move(2,1, Player.X));
		game.addMove(new Move(2,2, Player.O));
		game.addMove(new Move(3,3, Player.X));
		game.addMove(new Move(2,3, Player.O));
		
		logic = new GameLogic(game);
		
		assertEquals(new Move(3, 1, Player.X), logic.nextMove());
		
	}

	@Test
	public void testNextMoveBlockFork() throws Exception {
		
		Game game = new Game(Level.HARD, Player.O);
		
		game.addMove(new Move(1,1, Player.X));
		game.addMove(new Move(2,2, Player.O));
		game.addMove(new Move(3,3, Player.X));
		
		logic = new GameLogic(game);
		
		assertEquals(new Move(1, 2, Player.O), logic.nextMove());
		
	}
	
	@Test
	public void testNextMoveOppositeCorner() throws Exception {
		
		Game game = new Game(Level.HARD, Player.O);
		
		game.addMove(new Move(1,1, Player.X));
		game.addMove(new Move(2,2, Player.O));
		game.addMove(new Move(3,2, Player.X));
		
		logic = new GameLogic(game);
		
		assertEquals(new Move(3, 3, Player.O), logic.nextMove());
		
	}
	
	@Test
	public void testTurn() throws Exception {
		
		Game state = emptyGame();
		logic = new GameLogic(state);
		assertEquals(Player.X, logic.turn());
		
		List<Move> moves = new ArrayList<Move>();
		moves.add(new Move( new Position(2,2), Player.X));
		state = new Game(null, moves, Level.HARD, Player.X);
		
		logic = new GameLogic(state);
		assertEquals(Player.O, logic.turn());
		
	}

	@Test
	public void testOpenPositions() {
		logic = new GameLogic(emptyGame());
		List<Position> positions = logic.getOpenPositions();
		assertEquals(9, positions.size());
	}
	
	private Game emptyGame() {
		return new Game(Level.HARD, Player.X);
	}
}
