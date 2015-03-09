package org.tcd.game.tictactoe.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	private Long id;
	private List<Move> moves;
	private Level level;
	private Player computerPlaysAs;
	private Status status;
	private Player winner;
	
	public Game(Long id, List<Move> moves, Level level, Player computerPlaysAs) {
		this.moves = moves;
		this.level = level;
		this.computerPlaysAs = computerPlaysAs;
		this.id = id;
		this.setStatus(Status.OPEN);
	}
	
	public Game(Level level, Player computerPlaysAs) {
		this(null, new ArrayList<Move>(), level, computerPlaysAs);
	}

	public Game(Long id, Level level, Player computerPlaysAs) {
		this(id, new ArrayList<Move>(), level, computerPlaysAs);
	}
	
	public void addMove(Move move){
		moves.add(move);
	}
	
	public List<Move> getMoves() {

		return moves;
	}
	
	public Map<Position, Player> movesAsMap() {
		
		Map<Position, Player> map = new HashMap<Position, Player>();
		
		for (Move move: moves) {
			map.put(move.getPosition(), move.getPlayer());
		}
		return map;
	}

	public Level getLevel() {
		return level;
	}
	
	public Long getId() {
		return id;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Player getComputerPlaysAs() {
		return computerPlaysAs;
	}

	public void setComputerPlaysAs(Player computerPlaysAs) {
		this.computerPlaysAs = computerPlaysAs;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	
}