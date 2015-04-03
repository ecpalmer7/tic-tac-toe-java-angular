package org.tcd.game.tictactoe.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Position;
import org.tcd.game.tictactoe.domain.Status;

import static org.tcd.game.tictactoe.service.GameUtil.winningPositions;
import static org.tcd.game.tictactoe.service.GameUtil.openPositions;


public class GameLogic  {

	private final Game game;
	
	public GameLogic(Game game) {
		this.game = game;
	}
	
	public Move nextMove() {
		return new Move(nextPosition(), turn());
	}

	public Game addMove(Move move) {
		
		if (isOpen(move.getPosition())) {
			game.addMove(move);
		}
		// else throw exception?
		
		if (isDraw()) {
			game.setStatus(Status.DRAW);
		} else if (isWinner(Player.X)) {
			game.setStatus(Status.WIN);
			game.setWinner(Player.X);
		} else if (isWinner(Player.O)) {
			game.setStatus(Status.WIN);
			game.setWinner(Player.O);
		}
		return game;
	}

	boolean isDraw() {
		return (game.getMoves().size() == 9 && !isWinner(Player.X) && !isWinner(Player.O));
	}

	boolean isWinner(Player player) {
		return GameUtil.isWinner(game.movesAsMap(), player);
	}
	
	boolean isInPosition(Player player, int row, int col) {
		return (player == game.playerAt(row, col));
	}
	
	Player turn() {
		int count = game.getMoves().size();
		if ( (count & 1) == 0 ) {
			return Player.X;
		}
		return Player.O;
	}

	boolean isOpen(Position position) {
		return null == game.playerAt(position);
	}
	
	List<Position> getOpenPositions() {
		return openPositions(game.movesAsMap());
	}
	
	Player lastTurn() {
		return turn()==Player.X? Player.O: Player.X;
	}
	
	Position oppositeOpen(Position position1, Position position2) {
		Player player = lastTurn();
		if (isOpen(position1) && player == game.playerAt(position2)) {
			return position1;
		}
		return null;
	}
	
	Position sandwichOpen(Position position1, Position position2 ) {
		Position middle = new Position(2,2);
		if (isOpen(position1) && 
				(Player.O == game.playerAt(middle)) &&
				(Player.X == game.playerAt(position2))) {
			
			return position1;
		}
		return null;
	}
	
	boolean multipleWinningPositions(Position position) {
		Map<Position, Player> map = game.movesAsMap();
		map.put(position,  turn());
		return (winningPositions(turn(), map).size() > 1);
	}
	
	Position or(Position ...positions){
		return Stream.of(positions)
				.filter(position -> position != null)
				.findFirst()
				.orElse(null);
	}
	
	Position nextPosition() {
		switch (game.getLevel()) {
			case HARD:
				return or (
						win(),
	                    block(),
	                    fork(),
	                    blockFork(),
	                    firstMove(),
	                    center(),
	                    oppositeCorner(),
	                    sandwich(),
	                    emptyCorner(),
	                    emptySide(),
	                    firstOpen());
				
			case MEDIUM_HARD:
				return or ( 
						win(),
						block(),
						firstMove(),
						center(),
						oppositeCorner(),
						sandwich(),
						emptyCorner(),
						emptySide(),
						firstOpen());
				
			case MEDIUM:
				return or ( 
						win(),
						block(),							
						emptyCorner(),
						emptySide(),
						firstOpen());
				
			case EASY:
				return or (
						emptyCorner(),
						emptySide(),
						firstOpen());
				
			default :
				return firstOpen();
			}

	}
		
	Position openPosition(int row, int col) {
		Position position = new Position(row, col);
		if ( isOpen(position)) {
			return position;
		};
		return null;
	}
	
	Position firstOpen() {
		return getOpenPositions().iterator().next();
	}
	
	Position win() {
		return winningPositions(turn(), game.movesAsMap())
				.stream()
				.findFirst()
				.orElse(null);
	}
	
	Position block() {
		return winningPositions(lastTurn(), game.movesAsMap())
				.stream()
				.findFirst()
				.orElse(null);
	}
		
	Position fork() {
		// could use this::multipleWinningPositions
		Optional<Position> opt = getOpenPositions()
				.stream()
				.filter(p -> multipleWinningPositions(p))
				.findFirst();
		
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}
	
	Position blockFork() {
		if (game.getMoves().size() == 3) {				
			if (isInPosition(Player.X, 1, 1) && isInPosition(Player.X, 3, 3)) {
				return new Position(1, 2);
			} else if (isInPosition(Player.X, 1, 3) && isInPosition(Player.X, 3, 1)) {
				return new Position(1, 2);
			} else if (isInPosition(Player.X, 3, 2) && isInPosition(Player.X, 2, 3)) {
				return new Position(3, 3);
			} 				
		}
		return null;
	}
	
	Position firstMove() {
		if (game.getMoves().isEmpty()) {
			return new Position(1,1);
		}
		return null;
	}
	
	Position oppositeCorner() {
		
		return or (	oppositeOpen(new Position(1,1), new Position(3,3)),
					oppositeOpen(new Position(3,3), new Position(1,1)),
					oppositeOpen(new Position(3,1), new Position(1,3)),
					oppositeOpen(new Position(1,3), new Position(3,1)));
	}
	
	Position center() {
		return openPosition(2,2);
	}
	
	Position sandwich() {
		
		return or ( sandwichOpen(new Position(3,3), new Position(1,1)),
				    sandwichOpen(new Position(1,1), new Position(3,3)),
				    sandwichOpen(new Position(3,1), new Position(1,3)),
				    sandwichOpen(new Position(1,3), new Position(3,1)));
	}
	
	Position emptyCorner() {
		return or(openPosition(1,1),
				  openPosition(1,3),
				  openPosition(3,1),
				  openPosition(3,3));
	}
	
	Position emptySide() {
		return or(openPosition(1,2),
				  openPosition(2,1),
				  openPosition(2,3),
				  openPosition(3,2));
				
	}
}
